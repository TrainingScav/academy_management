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
    public void addAdmin(Admin admin) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);
            String insertSql = "INSERT INTO admin(admin_id, admin_name) " + "VALUES (?, ?) ";

            System.out.println("adminId : " + admin.getAdminId());
            System.out.println("adminName : " + admin.getAdminName());

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, admin.getAdminId());
                pstmt.setString(2, admin.getAdminName());
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
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Admin adminDto = new Admin();
                adminDto.setAdminPk(rs.getInt("admin_pk"));
                adminDto.setAdminId(rs.getString("admin_id"));
                adminDto.setAdminName(rs.getString("admin_name"));
                adminList.add(adminDto);
            }
        }
        return adminList;
    }

    // 관리자 회원정보 수정(UPDATE) 트랜잭션
    public void updateAdmin(Admin admin) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newAdminName = null;
            String checkSql = "SELECT * FROM admin WHERE admin_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, admin.getAdminId());
                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("해당 관리자 정보가 존재하지 않습니다.");
                }
                newAdminName = admin.getAdminName();
            }

            String updateNameSql = "UPDATE admin SET admin_name = ? WHERE admin_id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newAdminName);
                pstmt.setString(2, admin.getAdminId());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() +"관리자 정보 수정에 실패했습니다.");
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
            throw new SQLException(e.getMessage() +"관리자 정보 삭제에 실패했습니다.");
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
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            // 단일행 출력
            if (rs.next()) {
                Admin adminDTO = new Admin();
                adminDTO.setAdminPk(rs.getInt("admin_pk"));
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
            String insertSql = "INSERT INTO course (course_title, course_capacity, start_date, end_date, teacher_id) "
                    + "VALUES (?, ?, ?, ?, ?) ";

            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, course.getCourseTitle());
                pstmt.setInt(2, course.getCourseCapacity());
                pstmt.setDate(3, Date.valueOf(course.getStartDate()));
                pstmt.setDate(4, Date.valueOf(course.getEndDate()));
                pstmt.setString(5, course.getTeacherId());
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
    public void updateCourse(Course course) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newCourseTitle = null;
            String newTeacherId = null;
            LocalDate newStartDate = null;
            LocalDate newEndDate = null;

            String checkSql = "SELECT * FROM course WHERE course_pk = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, course.getCoursePk());
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강의는 존재하지 않습니다.");
                }
                newCourseTitle = course.getCourseTitle();
                newTeacherId = course.getTeacherId();
                newStartDate = course.getStartDate();
                newEndDate = course.getEndDate();
            }
            String updateNameSql = "UPDATE course " + "SET teacher_id = ?, course_title = ?, start_date = ?, end_date = ?"
                    + "WHERE course_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newTeacherId);
                pstmt.setString(2, newCourseTitle);
                pstmt.setDate(3, Date.valueOf(newStartDate));
                pstmt.setDate(4, Date.valueOf(newEndDate));
                pstmt.setInt(5, course.getCoursePk());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() +"강의 정보 수정에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    // 강의 정보 삭제(DELETE)
    public void deleteCourse(int coursePk) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "select * from course where course_pk = ? and start_date > current_date()";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setInt(1, coursePk);
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강의가 존재하지 않습니다.");
                }
            }
            String deleteIdSql = "DELETE FROM course WHERE course_pk = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteIdSql)) {
                pstmt.setInt(1, coursePk);
                int deleted = pstmt.executeUpdate();
                System.out.println("강의가 삭제되었습니다.");
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "강의 삭제에 실패했습니다.");
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
    public void updateTeacher(Teacher teacher) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newTeacherName = null;
            String newTeacherPhone = null;
            String newTeacherEmail = null;

            String checkSql = "SELECT * FROM teacher WHERE teacher_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, teacher.getTeacherId());
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 강사 정보는 존재하지 않습니다.");
                }
                newTeacherName = teacher.getTeacherName();
                newTeacherPhone = teacher.getTeacherPhone();
                newTeacherEmail = teacher.getTeacherEmail();
            }
            String updateNameSql = "UPDATE teacher SET teacher_name = ?, teacher_phone = ?, teacher_email = ? "
                    + "WHERE teacher_id = ? ";

            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newTeacherName);
                pstmt.setString(2, newTeacherPhone);
                pstmt.setString(3, newTeacherEmail);
                pstmt.setString(4, teacher.getTeacherId());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "강사 정보 수정에 실패했습니다.");
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
            throw new SQLException(e.getMessage() + "강사 정보 삭제에 실패했습니다.");
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

            String insertSql = "INSERT INTO "
                    + "students(student_id, student_name, student_birth, student_phone, student_email) "
                    + "VALUES(?, ?, ?, ?, ?) ";
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
    public void updateStudents(Students students) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            String newStudentName = null;
            LocalDate newStudentBirth = null;
            String newStudentPhone = null;
            String newStudentEmail = null;

            String checkSql = "SELECT * FROM students WHERE student_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, students.getStudentId());
                ResultSet rs = checkPstmt.executeQuery();
                if (!rs.next()) {
                    throw new SQLException("해당 학생은 존재하지 않습니다.");
                }
                newStudentName = students.getStudentName();
                newStudentBirth = students.getStudentBirth();
                newStudentPhone = students.getStudentPhone();
                newStudentEmail = students.getStudentEmail();
            }

            String updateNameSql =
                    "UPDATE students SET student_name = ?, student_birth = ?, student_phone = ?, student_email = ? "
                            + "WHERE student_id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(updateNameSql)) {
                pstmt.setString(1, newStudentName);
                pstmt.setDate(2, Date.valueOf(newStudentBirth));
                pstmt.setString(3, newStudentPhone);
                pstmt.setString(4, newStudentEmail);
                pstmt.setString(5, students.getStudentId());
                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "학생 정보 수정에 실패했습니다.");
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
            throw new SQLException(e.getMessage() + "학생 정보 삭제에 실패했습니다.");
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
}
