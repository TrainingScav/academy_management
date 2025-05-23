package dao;

import dto.Teacher;
import util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * - 강사 전체 조회
 * - 강사 이름으로 조회
 * - 강사 정보(관리자 권한)
 */
public class TeacherDAO {

    // 강사 전체 조회
    public List<Teacher> searchAllTeacher() throws SQLException {
        List<Teacher> teacherList = new ArrayList<>();
        String sql = "SELECT * from teacher ";
        try (
                Connection conn = DatabaseUtil.getConnection();
                Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int teacherPk = rs.getInt("teacher_pk");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String teacherPhone = rs.getString("teacher_phone");
                String teacherEmail = rs.getString("teacher_email");
                Teacher teacher = new Teacher(teacherPk, teacherId, teacherName, teacherPhone, teacherEmail);
                teacherList.add(teacher);
            }
        }
        return teacherList;
    }

    //강사 이름으로 조회
    public List<Teacher> searchTeacherByTitle(String searchName) throws SQLException {
        List<Teacher> teacherList = new ArrayList<>();
        String sql = "select * from teacher where teacher_name like ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchName + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int teacherPk = rs.getInt("teacher_pk");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String teacherPhone = rs.getString("teacher_phone");
                String teacherEmail = rs.getString("teacher_email");
                Teacher teacher = new Teacher(teacherPk, teacherId, teacherName, teacherPhone, teacherEmail);
                teacherList.add(teacher);

            }
        }

        return teacherList;
    }

    // 강사 id 인증 체크
    // 로그인쪽에서 넘어온 id 값을 teacher 테이블에 조회하여 있을경우 teacher 객체로 반환
    // 없으면 null로 반환
    public Teacher authenticateTeacher(String teacherId) throws SQLException {

        String sql = "select * from teacher where teacher_id like ? ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + teacherId + "%");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Teacher teacherDTO = new Teacher();
                teacherDTO.setTeacherPk(rs.getInt("teacher_pk"));
                teacherDTO.setTeacherId(rs.getString("teacher_id"));
                teacherDTO.setTeacherName(rs.getString("teacher_name"));
                return teacherDTO;

            }
        }

        return null;
    }

    // 강사가 담당하고 있는 수강과목
    public Teacher teacherCourseInfo(String teacherName) throws SQLException {

        String sql = "select t.teacher_name, c.course_title, c.start_date,c.end_date\n" +
                "from teacher as t\n" +
                "inner join course as c on t.teacher_id = c.teacher_id\n" +
                "where t.teacher_name = ? and c.start_date <= current_date() and current_date() <= c.end_date ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, teacherName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Teacher teacherDTO = new Teacher();
                teacherDTO.setCourseTitle(rs.getString("course_title"));
                teacherDTO.setStartDate(rs.getDate("start_date").toLocalDate());
                teacherDTO.setEndDate(rs.getDate("end_date").toLocalDate());
                return teacherDTO;
            }
        }
        return null;
    }

    // TODO 이 메서드는 view 에서 구현하지 않는 메서드
    //강사 수강과목 진척도 계산 (select)
    public String studentCourseProgress(String teacherId) throws SQLException {

        String sql = "select t.teacher_name, " +
                "datediff(current_date , start_date) as runDate , " +
                "datediff(end_date , current_date ) as remainingDate," +
                "round((datediff(current_date , start_date) / datediff(end_date , start_date))* 100) as percent" +
                "from teacher as t" +
                "inner join course as c on t.teacher_id = c.teacher_id" +
                "where teacher_name = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacherId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return "강사 이름: " + rs.getString("teacher_id") +
                        "진행날짜 " + rs.getInt("runDate") +
                        "남은 날짜" + rs.getInt("remainingDate") +
                        "진척도(%)" + rs.getInt("percent");

            }
        }//try
        return null;


//    // 강사 정보(관리자 권한)
//    // showTeacherInfo
    }

} // end of main