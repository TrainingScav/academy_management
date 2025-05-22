package dao;

import dto.Students;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAO {

    //학생 전체 조회 (select)
    public List<Students> getAllStudents() throws SQLException {

        List<Students> studentsList = new ArrayList<>();

        String sql = "select * from students ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Students studentsDTO = new Students();

                studentsDTO.setStudentPk(rs.getInt("student_pk"));
                studentsDTO.setStudentId(rs.getString("student_id"));
                studentsDTO.setStudentName(rs.getString("student_name"));
                studentsDTO.setStudentBirth(rs.getDate("student_birth").toLocalDate());
                studentsDTO.setStudentPhone(rs.getString("student_phone"));
                studentsDTO.setStudentEmail(rs.getString("student_email"));

                studentsList.add(studentsDTO);
            }//while
        }//try-catch
        return studentsList;
    }//getAllStudents

    //학생 이름 조회 (select)
    public List<Students> searchStudentByName(String studentName) throws SQLException {

        List<Students> studentsList = new ArrayList<>();

        String sql = "select*from students where student_name like ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + studentName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Students studentsDTO = new Students();

                studentsDTO.setStudentPk(rs.getInt("student_pk"));
                studentsDTO.setStudentId(rs.getString("student_id"));
                studentsDTO.setStudentName(rs.getString("student_name"));
                studentsDTO.setStudentBirth(rs.getDate("student_birth").toLocalDate());
                studentsDTO.setStudentPhone(rs.getString("student_phone"));
                studentsDTO.setStudentEmail(rs.getString("student_email"));

                studentsList.add(studentsDTO);
            }//while
        }//try-catch
        return studentsList;
    }//searchByName

    //학생 학번 조회 및 로그인 (select)
    public Students authenticateStudent(String StudentId) throws SQLException {

        String sql = "select*from students where student_id = ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, StudentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Students studentsDTO = new Students();
                studentsDTO.setStudentPk(rs.getInt("student_pk"));
                studentsDTO.setStudentId(rs.getString("student_id"));
                studentsDTO.setStudentName(rs.getString("student_name"));

                //정확한 id입력시 student 객체 생성 리턴
                return studentsDTO;
            }//if
        }//try-catch
        //잘못된 id 입력시 null값 반환
        return null;
    }//authenticateStudents

    //학생 수강과목 조회 (select)
    public Students studentCourseInfo(String studentId) throws SQLException {

        String sql = "select c.course_title, c.start_date, c.end_date " +
                "from students as s " +
                "join course_history as ch on s.student_id = ch.student_id " +
                "join course as c on ch.course_pk = c.course_pk " +
                "where s.student_id = ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Students studentsDTO = new Students();

                studentsDTO.setCourseTitle(rs.getString("course_title"));
                studentsDTO.setCourseStartDate(rs.getDate("start_date").toLocalDate());
                studentsDTO.setCourseEndDate(rs.getDate("end_date").toLocalDate());

                //정확한 id입력시 student 객체 생성 리턴
                return studentsDTO;
            }//if
        }//try
        //잘못된 id 입력시 null값 반환
        return null;
    }//studentCourse

    //학생 수강과목 진척도 계산 (select)
    public Students studentCourseProgress(String studentId) throws SQLException {

        String sql = "select c.course_title, c.start_date, c.end_date, " +
                "datediff(curdate(),start_date) as progress, " +
                "datediff(end_date,curdate()) as remain, " +
                "concat(round(datediff(curdate(),start_date)/datediff(end_date,start_date)*100),'%') as percent " +
                "from students as s " +
                "join course_history as ch on s.student_id = ch.student_id " +
                "join course as c on ch.course_pk = c.course_pk " +
                "where curdate() > c.start_date " +
                "and c.end_date > curdate() " +
                "and s.student_id = ? ";

        try (Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Students stuDto = new Students();

                stuDto.setCourseTitle(rs.getString("course_title"));
                stuDto.setCourseStartDate(rs.getDate("start_date").toLocalDate());
                stuDto.setCourseEndDate(rs.getDate("end_date").toLocalDate());
                stuDto.setCourseProgress(rs.getInt("progress"));
                stuDto.setCourseRemain(rs.getInt("remain"));
                stuDto.setCoursePercent(rs.getInt("percent"));

                return stuDto;
            }
        }//try
        return null;
    }//studentCourseProgress







    //테스트코드
    public static void main(String[] args) {

//        StudentsDAO sdao = new StudentsDAO();
//
//        try {
//            sdao.studentCourseProgress("s101");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        //전체조회
//        try {
//            sdao.getAllStudents();
//            for (int i = 0; i < sdao.getAllStudents().size(); i++) {
//                System.out.println(sdao.getAllStudents().get(i));
//
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        //이름조회
//        try {
//            System.out.println(sdao.searchByName("김"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        //학번조회
//        try {
//            System.out.println(sdao.studentCourse("100001"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }//main
}//StudentsDAO
