package dao;
import dto.Students;
import util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

                int studentsPk = rs.getInt("students_pk");
                String accessLevel = rs.getString("access_level");
                String studentsId = rs.getString("students_id");
                String studentsName = rs.getString("students_name");
                LocalDate studentsBirth = rs.getDate("students_birth").toLocalDate();
                String studentsPhone = rs.getString("students_phone");
                String studentsEmail = rs.getString("students-email");

                Students students = new Students(studentsPk, accessLevel, studentsId, studentsName, studentsBirth, studentsPhone, studentsEmail);
                studentsList.add(students);
            }//while
        }//try-catch
        return studentsList;
    }//getAllStudents

    //학생 이름 조회 (select)
    public List<Students> searchByName(String studentName) throws SQLException {

        List<Students> studentsList = new ArrayList<>();

        String sql = "sselect*from students where student_name like ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + studentName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int studentsPk = rs.getInt("students_pk");
                String accessLevel = rs.getString("access_level");
                String studentsId = rs.getString("students_id");
                String studentsName = rs.getString("students_name");
                LocalDate studentsBirth = rs.getDate("students_birth").toLocalDate();
                String studentsPhone = rs.getString("students_phone");
                String studentsEmail = rs.getString("students-email");

                Students students = new Students(studentsPk, accessLevel, studentsId, studentsName, studentsBirth, studentsPhone, studentsEmail);
                studentsList.add(students);
            }//while
        }//try-catch
        return studentsList;
    }//searchByName

    //학생 학번 조회 및 로그인 (select)
    public Students authenticateStudents(String StudentsId) throws SQLException {

        String sql = "select*from students where student_id = ? ";

        try(Connection conn = DatabaseUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, StudentsId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Students studentsDTO = new Students();

                studentsDTO.setStudentsPk(rs.getInt("student_pk"));
                studentsDTO.setAccessLevel(rs.getString("access_level"));
                studentsDTO.setStudentsId(rs.getString("student_id"));
                studentsDTO.setStudentsName(rs.getString("student_name"));
                studentsDTO.setStudentsBirth(rs.getDate("student_birth").toLocalDate());
                studentsDTO.setStudentsPhone(rs.getString("student_phone"));
                studentsDTO.setStudentsEmail(rs.getString("student_email"));

                //정확한 id입력시 student 객체 생성 리턴
                return studentsDTO;
            }//if
        }//try-catch
        //잘못된 id 입력시 null값 반환
        return null;
    }//authenticateStudents


}//StudentsDAO
