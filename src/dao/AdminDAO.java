package dao;

import dto.Admin;
import dto.Course;
import dto.Students;
import dto.Teacher;
import util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // 관리자 회원정보 등록(INSERT) 트랜잭션
    public void addAdmin(String adminId, String adminName) throws SQLException {

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO admin(admin_id, admin_name) " +
                    "VALUES (?, ?) ";

            System.out.println("adminId : " + adminId);
            System.out.println("adminName : " + adminName);

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, adminId);
                pstmt.setString(2, adminName);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("관리자 정보 등록에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 관리자 회원정보 조회(SELECT)
    public List<Admin> getAllAdmin() throws SQLException {
        List<Admin> adminList = new ArrayList<>();
        String selectSql = "SELECT * FROM admin ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Admin adminDto = new Admin();
                adminDto.setAdminPk(rs.getInt("admin_pk"));
                adminDto.setAccessLevel(rs.getString("access_level"));
                adminDto.setAdminId(rs.getString("admin_id"));
                adminDto.setAdminName(rs.getString("admin_name"));
                adminList.add(adminDto);
            }
        }
        return adminList;
    }

    // 관리자 회원정보 수정(UPDATE) 트랜잭션
    public void updateAdmin(int adminPk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newAdminId = null;
            String newAdminName = null;
            String checkSql = "SELECT * FROM admin WHERE admin_pk = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, adminPk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 관리자 정보가 존재하지 않습니다.");
                }
                newAdminId = rs.getString("admin_id");
                newAdminName = rs.getString("admin_name");

            }

            String updateNameSql = "UPDATE admin SET admin_id = ?, admin_name = ? WHERE admin_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newAdminId);
                pstmt.setString(2, newAdminName);
                pstmt.setInt(3, adminPk);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("관리자 정보 수정에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 관리자 회원정보 삭제(DELETE) 트랜잭션
    public void deleteAdmin(String adminId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT * FROM admin WHERE admin_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, adminId);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 관리자 정보가 존재하지 않습니다.");
                }
            }
            String deleteIdSql = "DELETE FROM admin WHERE admin_id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)) {
                pstmt.setString(1, adminId);
                int deleted = pstmt.executeUpdate();
                System.out.println("관리자 정보가 삭제되었습니다.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("관리자 정보 삭제에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 관리자 admin_id 로 관리자 인증(로그인 용) 기능
    public Admin authenticateAdmin(String adminId) throws SQLException {
        String sql = "SELECT * FROM admin WHERE admin_id = ? ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            // 단일행 출력
            if (rs.next()) {
                Admin adminDTO = new Admin();
                adminDTO.setAdminPk(rs.getInt("admin_pk"));
                adminDTO.setAccessLevel(rs.getString("access_level"));
                adminDTO.setAdminId(rs.getString("admin_id"));
                adminDTO.setAdminName(rs.getString("admin_name"));
                return adminDTO;
            }
        }
        return null;
    }


    // 강의 개설(INSERT)
    public void addCourse(Course course) throws SQLException {

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO course (course_title, course_capacity, start_date, end_date, teacher_id) " +
                    "VALUES (?, ?, ?, ?, ?) ";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, course.getCourseTitle());
                pstmt.setInt(2, course.getCourseCapacity());
                pstmt.setDate(3, Date.valueOf(course.getStartDate()));
                pstmt.setDate(4, Date.valueOf(course.getEndDate()));
                pstmt.setInt(5, course.getTeacherId());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 개설에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 강의 수정(UPDATE)
    public void updateCourse(int coursePk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newCourseTitle = null;
            Integer newTeacherId = null;
            LocalDate newStartDate = null;
            LocalDate newEndDate = null;


            String checkSql = "SELECT * FROM course WHERE course_pk = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, coursePk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강의는 존재하지 않습니다.");
                }
                newCourseTitle = rs.getString("course_title");
                newTeacherId = rs.getInt("teacher_id");
                newStartDate = rs.getDate("start_date").toLocalDate();
                newEndDate = rs.getDate("end_date").toLocalDate();
            }

            String updateNameSql = "UPDATE course " +
                    "SET teacher_id = ?, course_title = ?, start_date = ?, end_date = ?" +
                    "WHERE course_pk = ? ";

            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setInt(1, newTeacherId);
                pstmt.setString(2, newCourseTitle);
                pstmt.setDate(3, Date.valueOf(newStartDate));
                pstmt.setDate(4, Date.valueOf(newEndDate));
                pstmt.setInt(5, coursePk);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 정보 수정에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 강의 정보 삭제(DELETE)
    public void deleteCourse(String coursePk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT * FROM course WHERE course_pk = ? " +
                    "AND start_date <= current_date() AND current_date() <= end_date";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, coursePk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강의가 존재하지 않습니다.");
                }
            }
            String deleteIdSql = "DELETE FROM course WHERE course_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)) {
                pstmt.setString(1, coursePk);
                int deleted = pstmt.executeUpdate();
                System.out.println("강의가 삭제되었습니다.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 삭제에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    // 강사 회원정보 등록(INSERT)
    public void addTeacher(Teacher teacher) throws SQLException {

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO teacher(teacher_id, teacher_name, teacher_phone, teacher_email) "
                    + "VALUES (?, ?, ?, ?) ";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, teacher.getTeacherId());
                pstmt.setString(2, teacher.getTeacherName());
                pstmt.setString(3, teacher.getTeacherPhone());
                pstmt.setString(4, teacher.getTeacherEmail());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강사 정보 등록에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 강사 회원정보 수정(UPDATE)
    public void updateTeacher(int teacherPk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newTeacherId = null;
            String newTeacherName = null;
            String newTeacherPhone = null;
            String newTeacherEmail = null;

            String checkSql = "SELECT * FROM teacher WHERE teacher_pk = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, teacherPk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강사 정보는 존재하지 않습니다.");
                }
                newTeacherId = rs.getString("teacher_id");
                newTeacherName = rs.getString("teacher_name");
                newTeacherPhone = rs.getString("teacher_phone");
                newTeacherEmail = rs.getString("teacher_email");
            }
            String updateNameSql = "UPDATE teacher " +
                    "SET teacher_id = ?, teacher_name = ?, teacher_phone = ?, teacher_email = ? " +
                    "WHERE teacher_pk = ? ";

            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newTeacherId);
                pstmt.setString(2, newTeacherName);
                pstmt.setString(3, newTeacherPhone);
                pstmt.setString(4, newTeacherEmail);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강사 정보 수정에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 강사 회원정보 삭제(DELETE)
    public void deleteTeacher(String teacherId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT * FROM teacher WHERE teacher_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, teacherId);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강사 정보가 존재하지 않습니다.");
                }
            }
            String deleteIdSql = "DELETE FROM teacher WHERE teacher_id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)) {
                pstmt.setString(1, teacherId);
                int deleted = pstmt.executeUpdate();
                System.out.println("강사 정보가 삭제되었습니다.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강사 정보 삭제에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    // 학생 회원정보 등록(INSERT)
    public void addStudent(Students students) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String insertSql = "INSERT INTO " +
                    "students(student_id, student_name, student__birth, student_phone, student_email) " +
                    "VALUES(?, ?, ?, ?, ?) ";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, students.getStudentId());
                pstmt.setString(2, students.getStudentName());
                pstmt.setDate(3, Date.valueOf(students.getStudentBirth()));
                pstmt.setString(4, students.getStudentPhone());
                pstmt.setString(5, students.getStudentEmail());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new RuntimeException("학생 정보 등록에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 학생 회원정보 수정(UPDATE)
    public void updateStudents(int studentPk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newStudentId = null;
            String newStudentName = null;
            LocalDate newStudentBirth = null;
            String newStudentPhone = null;
            String newStudentEmail = null;

            String checkSql = "SELECT * FROM students WHERE student_pk = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, studentPk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 학생은 존재하지 않습니다.");
                }
                newStudentId = rs.getString("student_id");
                newStudentName = rs.getString("student_name");
                newStudentBirth = rs.getDate("student_birth").toLocalDate();
                newStudentPhone = rs.getString("student_phone");
                newStudentEmail = rs.getString("student_email");
            }

            String updateNameSql = "UPDATE students " +
                    "SET student_id = ?, student_name = ?, student__birth = ?, student_phone = ?, student_email = ? " +
                    "WHERE student_pk = ? ";

            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newStudentId);
                pstmt.setString(2, newStudentName);
                pstmt.setDate(3, Date.valueOf(newStudentBirth));
                pstmt.setString(4, newStudentPhone);
                pstmt.setString(5, newStudentEmail);
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("학생 정보 수정에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 학생 회원정보 삭제(DELETE)
    public void deleteStudent(String studentId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT * FROM students WHERE student_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, studentId);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 학생 정보가 존재하지 않습니다.");
                }
            }
            String deleteIdSql = "DELETE FROM students WHERE student_id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)) {
                pstmt.setString(1, studentId);
                int deleted = pstmt.executeUpdate();
                System.out.println("학생 정보가 삭제되었습니다.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("학생 정보 삭제에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }


    // 테스트 코드
    public static void main(String[] args) {
        AdminDAO adminDAO = new AdminDAO();
        // 관리자 회원정보 등록(INSERT)

        try {
            adminDAO.addAdmin(admin1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 관리자 회원정보 조회(SELECT)
        try {
            adminDAO.getAllAdmin();
            for (int i = 0; i < adminDAO.getAllAdmin().size(); i++) {
                System.out.println(adminDAO.getAllAdmin().get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 관리자 회원정보 수정(UPDATE)

        // 관리자 회원정보 삭제(DELETE)

        // 관리자 admin_id 로 관리자 인증(로그인 용) 기능

    }


}
