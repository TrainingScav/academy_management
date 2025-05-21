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

        String sql = "select * from students";

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

        String sql = "sselect*from students where student_name like ?";

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

    //TODO 학생 정보 (관리자 권한)

}//StudentsDAO
