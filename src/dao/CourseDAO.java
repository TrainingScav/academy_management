package dao;

import dto.Admin;
import dto.Course;
import dto.Students;
import service.CourseService;
import util.DatabaseUtil;

import javax.swing.*;
import java.awt.print.Book;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {


    // 수강 신청

    public void insert(int coursePk, String studentId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false);

            int newCoursePk;
            String newStudentId;


            String checkSql = "select * from course_history where student_id = ? ";
            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {
                checkPstmt.setString(1, studentId);
                ResultSet rs = checkPstmt.executeQuery();
                if (rs.next()) {
                    throw new SQLException("해당 학생은 이미 강의를 수강중입니다.");
                }
                newCoursePk = coursePk;
                newStudentId = studentId;

            }
            String updateNameSql = "insert into course_history(course_pk,student_id) values(?,? ) ";
            try (PreparedStatement InsertPstmt = conn.prepareStatement(updateNameSql)) {
                InsertPstmt.setInt(1, newCoursePk);
                InsertPstmt.setString(2, newStudentId);
                InsertPstmt.executeUpdate();

            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException(e.getMessage() + "수강정보 등록에 실패했습니다.", e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public Course authenticateCourse(int coursePk) throws SQLException {

        String sql = "select*from course where course_pk = ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, coursePk);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Course courseDTO = new Course();

                courseDTO.setCoursePk(rs.getInt("course_pk"));
                courseDTO.setCourseTitle(rs.getString("course_title"));

                //정확한 id입력시 student 객체 생성 리턴
                return courseDTO;
            }//if
        }//try-catch
        //잘못된 id 입력시 null값 반환
        return null;
    }//authenticateCourse


    //수강 기록 삭제
    public void delete(String studentId) throws SQLException {

        //finally 자원해제를 위해 try문 외부에 변수 선언했다.
        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();

            //트랜잭션
            conn.setAutoCommit(false); //자동저장을 막겠다는 뜻

            String checkSql = "select * from course_history " +
                    "where student_id = ? ";

            try (PreparedStatement checkPstmt = conn.prepareStatement(checkSql)) {

                checkPstmt.setString(1, studentId);
                checkPstmt.executeQuery();

                ResultSet rs = checkPstmt.executeQuery();

                if (!rs.next()) {
                    throw new SQLException("문의하신 수강기록이 존재하지 않거나 " + "이미 삭제 처리 됐습니다.");
                }
            }

            String deleteSql = "delete from course_history " +
                    "where student_id = ? ";

            try (PreparedStatement deletePsmt = conn.prepareStatement(deleteSql)) {

                deletePsmt.setString(1, studentId);
                deletePsmt.executeUpdate();

            }
            //트랜잭션 완료, 커밋
            conn.commit();

        } catch (SQLException e) {

            //오류 발생시 롤백 처리
            if (conn != null) {
                conn.rollback();
            }
            throw new SQLException("강의 정보 수정에 실패했습니다." + e.getMessage(), e);

        } finally {
            if (conn != null) {

                //오토커밋 복구
                conn.setAutoCommit(true);

                //자원 해제 (메모리 누수 방지)
                conn.close();
            }
        }

    }//deleteCourse


    // 수강정보 전체 조회
    public List<Course> getAllCourse() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = "select * from course ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("course_pk");
                String teacherId = rs.getString("teacher_id");
                String title = rs.getString("course_title");
                int capacity = rs.getInt("course_capacity");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();


                Course course = new Course(id, teacherId, title, capacity, startDate, endDate);
                courseList.add(course);


            }
        }
        return courseList;
    }

    // 강의명으로 수강정보 조회
    public List<Course> searchCourseTitle(String searchTitle) throws SQLException {

        List<Course> courseList = new ArrayList<>();

        String sql = "select * from course where course_title like ? ";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + searchTitle + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("course_pk");
                String teacherId = rs.getString("teacher_id");
                String title = rs.getString("course_title");
                int capacity = rs.getInt("course_capacity");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();


                Course course = new Course(id, teacherId, title, capacity, startDate, endDate);
                courseList.add(course);
            }
        }
        return courseList;
    }


    //테스트 코드 작성
    public static void main(String[] args) {

        CourseDAO courseDAO = new CourseDAO();

        // 수강정보 전체조회
//            try {
//                courseDAO.getAllCourse();
//
//                for (int i = 0; i < courseDAO.getAllCourse().size(); i++) {
//                    System.out.println(courseDAO.getAllCourse().get(i));
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }

        // 강의명으로 선택 조회
//        try {
//            // courseDAO.getAllCourse();
//            ArrayList<Course> selectCourseList = (ArrayList) courseDAO.searchCourseTitle("컴퓨터");
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
//            courseDAO.insert(19,"S140");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        // pk로 title 검색
//        try {
//            System.out.println(courseDAO.authenticateCourse(15));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


        // 수강 취소
        try {
            courseDAO.delete("S110");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    } // end of main

} // end of class
