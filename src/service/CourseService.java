package service;

import dao.CourseDAO;
import dto.Course;
import util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    // 수강 신청 서비스
    public void insert(int coursePk, String studentId) throws SQLException {
        // 입력 값 유효성 검사
        if (coursePk <= 0 || studentId == null) {
            throw new SQLException("강의번호와 학생번호는 필수 입력사항 입니다.");
        }
        // 유효성 검사 통과후 BookDAO 에게 입을 협력 요청 한다.
        courseDAO.insert(coursePk, studentId);
    }

    // 수강 취소
    public void delete(int coursePk, String studentId) throws SQLException {
        // 입력 값 유효성 검사
        if (coursePk <= 0 || studentId == null) {
            throw new SQLException("강의번호와 학생번호는 필수 입력사항 입니다.");
        }
        // 유효성 검사 통과후 BookDAO 에게 입을 협력 요청 한다.
        courseDAO.delete(studentId);
    }


    // 강의 전체 조회 하는 서비스
    public List<Course> getAllCourse() throws SQLException {
        return courseDAO.getAllCourse();
    }

    // 강의 이름으로 조회하는 서비스
    public List<Course> searchCourseTitle(String searchTitle) throws SQLException {
        // 입력값 유효성 검사
        if (searchTitle == null || searchTitle.trim().isEmpty()) {
            throw new SQLException("검색 제목을 입력해주세요");
        }
        return courseDAO.searchCourseTitle(searchTitle);
    }

    public static void main(String[] args) {

        CourseService courseService = new CourseService();


//        // 수강정보 전체조회
//        try {
//            courseService.getAllCourse();
//
//            for (int i = 0; i < courseService.getAllCourse().size(); i++) {
//                System.out.println(courseService.getAllCourse().get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        // 강의명으로 선택 조회
//        try {
//            // courseDAO.getAllCourse();
//            ArrayList<Course> selectCourseList = (ArrayList) courseService.searchCourseTitle("컴퓨터");
//
//
//            for (int i = 0; i < selectCourseList.size(); i++) {
//                System.out.println(selectCourseList.get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        // 수강 신청
//        try {
//            courseService.insert(1,"100002");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


         // 수강취소
//        try {
//            courseService.delete(2,"100002");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    } // end of main

}

