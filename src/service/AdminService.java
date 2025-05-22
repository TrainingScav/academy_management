package service;

import dao.AdminDAO;
import dto.Admin;
import dto.Course;
import dto.Teacher;
import dto.Students;

import java.sql.SQLException;
import java.util.List;

public class AdminService {
    private final AdminDAO adminDAO = new AdminDAO();


    //관리자

    //1. 관리자 회원정보 등록(INSERT) 트랜잭션
    public void addAdmin(Admin admin) throws SQLException {
        if (admin.getAdminId() == null || admin.getAdminName() == null ||
                admin.getAdminId().trim().isEmpty() || admin.getAdminName().trim().isEmpty()) {
            throw new SQLException("관리자 아이디와 이름은 필수 입력 항목 입니다.");
        }
        adminDAO.addAdmin(admin);
    }

    //2.관리자 회원정보 조회(SELECT)
    public List<Admin> getAllAdmin() throws SQLException {
        return adminDAO.getAllAdmin();
    }

    //3.관리자 회원정보 수정(UPDATE) 트랜잭션
    public void updateAdmin(Admin admin) throws SQLException {
        if (admin.getAdminId() == null || admin.getAdminName() == null ||
                admin.getAdminId().trim().isEmpty() || admin.getAdminName().trim().isEmpty()) {
            throw new SQLException("유효한 관리자 아이디와 이름을 입력해주세요.");
        }
        adminDAO.updateAdmin(admin);
    }

    //4.관리자 회원정보 삭제(DELETE) 트랜잭션
    public void deleteAdmin(String adminId) throws SQLException {
        if (adminId == null || adminId.trim().isEmpty()) {
            throw new SQLException("유효한 관리자 아이디를 입력해주세요.");
        }
        adminDAO.deleteAdmin(adminId);
    }

    //5.관리자 admin_id 로 관리자 인증(로그인 용) 기능
    public Admin authenticateAdmin(String adminId) throws SQLException {
        if (adminId == null || adminId.trim().isEmpty()) {
            throw new SQLException("관리자 아이디를 입력해주세요");
        }
        return adminDAO.authenticateAdmin(adminId);
    }


    //6.강의 개설(INSERT)
    public void addCourse(Course course) throws SQLException {
        if (course.getCourseTitle() == null || course.getCourseCapacity() == 0 ||
                course.getStartDate() == null || course.getEndDate() == null || course.getTeacherId() == null) {
            throw new SQLException("강의 정보는 필수 입력 항목 입니다.");
        }
        if (course.getCourseTitle().trim().isEmpty() || course.getTeacherId().trim().isEmpty()) {
            throw new SQLException("강의 정보는 필수 입력 항목 입니다.");
        }
        adminDAO.addCourse(course);
    }

    //7.강의 수정(UPDATE)
    public void updateCourse(Course course) throws SQLException {
        if (course.getCourseTitle() == null || course.getStartDate() == null || course.getEndDate() == null ||
                course.getTeacherId() == null) {
            throw new SQLException("유효한 강의 정보를 입력해주세요.");
        }
        if (course.getCourseTitle().trim().isEmpty() || course.getTeacherId().trim().isEmpty()) {
            throw new SQLException("유효한 강의 정보를 입력해주세요.");
        }
        adminDAO.updateCourse(course);
    }

    //8.강의 정보 삭제(DELETE)
    public void deleteCourse(String coursePk) throws SQLException {
        if (coursePk == null | coursePk.trim().isEmpty()) {
            throw new SQLException("유효한 강의 번호를 입력해주세요.");
        }
        adminDAO.deleteCourse(coursePk);
    }


    //9.강사 회원정보 등록(INSERT)
    public void addTeacher(Teacher teacher) throws SQLException {
        if (teacher.getTeacherId() == null || teacher.getTeacherName() == null || teacher.getTeacherPhone() == null ||
                teacher.getTeacherEmail() == null) {
            throw new SQLException("강사 정보는 필수 입력 항목 입니다.");
        }
        if (teacher.getTeacherId().trim().isEmpty() || teacher.getTeacherName().trim().isEmpty() ||
                teacher.getTeacherPhone().trim().isEmpty() || teacher.getTeacherEmail().trim().isEmpty()) {
            throw new SQLException("강사 정보는 필수 입력 항목 입니다.");
        }
        adminDAO.addTeacher(teacher);
    }

    //10.강사 회원정보 수정(UPDATE)
    public void updateTeacher(Teacher teacher) throws SQLException {
        if (teacher.getTeacherName() == null || teacher.getTeacherPhone() == null || teacher.getTeacherEmail() == null) {
            throw new SQLException("유효한 강사 정보를 입력해주세요.");
        }
        if (teacher.getTeacherName().trim().isEmpty() || teacher.getTeacherPhone().trim().isEmpty() ||
                teacher.getTeacherEmail().trim().isEmpty()) {
            throw new SQLException("유효한 강사 정보를 입력해주세요.");
        }
        adminDAO.updateTeacher(teacher);
    }

    //11.강사 회원정보 삭제(DELETE)
    public void deleteTeacher(String teacherId) throws SQLException {
        if (teacherId == null | teacherId.trim().isEmpty()) {
            throw new SQLException("유효한 강사 아이디를 입력해주세요.");
        }
        adminDAO.deleteTeacher(teacherId);
    }


    //12.학생 회원정보 등록(INSERT)
    public void addStudent(Students students) throws SQLException {
        if (students.getStudentId() == null || students.getStudentName() == null || students.getStudentBirth() == null
                || students.getStudentPhone() == null || students.getStudentEmail() == null) {
            throw new SQLException("학생 정보는 필수 입력 항목 입니다.");
        }
        if (students.getStudentId().trim().isEmpty() || students.getStudentName().trim().isEmpty()
                || students.getStudentPhone().trim().isEmpty() || students.getStudentEmail().trim().isEmpty()) {
            throw new SQLException("학생 정보는 필수 입력 항목 입니다.");
        }
        adminDAO.addStudent(students);
    }

    //13.학생 회원정보 수정(UPDATE)
    public void updateStudents(Students students) throws SQLException {
        if (students.getStudentName() == null || students.getStudentBirth() == null ||
                students.getStudentPhone() == null || students.getStudentEmail() == null) {
            throw new SQLException("유효한 학생 정보를 입력해주세요.");
        }
        if (students.getStudentName().trim().isEmpty() || students.getStudentPhone().trim().isEmpty() ||
                students.getStudentEmail().trim().isEmpty()) {
            throw new SQLException("유효한 학생 정보를 입력해주세요.");
        }
        adminDAO.updateStudents(students);
    }

    //14.학생 회원정보 삭제(DELETE)
    public void deleteStudent(String studentId) throws SQLException {
        if (studentId == null | studentId.trim().isEmpty()) {
            throw new SQLException("유효한 학생 아이디를 입력해주세요.");
        }
        adminDAO.deleteStudent(studentId);
    }

    // TODO 추후 삭제 예정
    // 테스트 코드
    // 관리자 정보 등록



}
