package service;

import dao.TeacherDAO;
import dto.Teacher;

import java.sql.SQLException;
import java.util.List;

public class TeacherService {

    private static final TeacherDAO teacherDAO = new TeacherDAO();

    //강사

//1. 강사 전체 조회

    public List<Teacher> searchAllTeacher() {
        try {
            return teacherDAO.searchAllTeacher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//2.강사 이름으로 조회

    public List<Teacher> searchTeacherByTitle(String searchName) {
        // 입력값 유효성 검사
        if (searchName == null || searchName.trim().isEmpty() ) {
            try {
                throw new SQLException("성함을 다시 입력해주세요");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return teacherDAO.searchTeacherByTitle(searchName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//3.강사 로그인

    public Teacher authenticateTeacher(String teacherId) {
        // 유효성 검사
        if (teacherId == null || teacherId.trim().isEmpty()) {
            try {
                throw new SQLException("유효한 ID 값을 입력하세요");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return teacherDAO.authenticateTeacher(teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//4.강사가 담당하고 있는 수강과목

    public Teacher teacherCourseInfo(String teacherName) {
        if (teacherName == null || teacherName.trim().isEmpty()) {
            try {
                throw new SQLException("성함을 다시 입력해주세요");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return teacherDAO.teacherCourseInfo(teacherName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TeacherService teacherService = new TeacherService();
//        teacherService.searchAllTeacher();
//        teacherService.searchTeacherByTitle();
//        teacherService.authenticateTeacher();
//        teacherService.connectedCourse();
        System.out.println(teacherService.searchTeacherByTitle("김만주"));


    }
}
