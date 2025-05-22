package dao;

import dto.Admin;
import dto.Course;
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
//    public void insert(int coursePk,String studentId) throws SQLException {
//        String sql = "insert into course_history(course_pk,student_id) " +
//                "values(?,? ) ";
//        try (Connection conn = DatabaseUtil.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, coursePk);
//            pstmt.setString(2, studentId);
//            pstmt.executeUpdate();
//
//        }
//    }


    public void insertCourse(int coursePk, String studentId) throws SQLException {

        //finally 자원해제를 위해 try문 외부에 변수 선언했다.
        Connection conn = null;

        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); //자동저장을 막겠다는 뜻
            String sql = "insert into course_history(course_pk,student_id) values(?,? ) ";

            try (PreparedStatement InsertPstmt = conn.prepareStatement(sql)) {

                InsertPstmt.setInt(1, coursePk);
                InsertPstmt.setString(2, studentId);
                InsertPstmt.executeUpdate();

                //트랜잭션 완료, 커밋
                conn.commit();
            }

        } catch (SQLException e) {

            //오류 발생시 롤백 처리
            if (conn != null) {
                conn.rollback();
            }
            System.err.println("오류발생, rollback 처리 됐습니다");

        } finally {
            if (conn != null) {

                //오토커밋 복구
                conn.setAutoCommit(true);

                //자원 해제 (메모리 누수 방지)
                conn.close();
            }
        }//try-catch-finally
    }//returnBook




    // 수강 취소
    public void delete(int coursePk,String studentId) throws SQLException {
        String sql = "DELETE\n" +
                "FROM course_history\n" +
                "where course_pk = ? and student_id = ? ";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, coursePk);
            pstmt.setString(2, studentId);
            pstmt.executeUpdate();


        }
    }

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


                Course course = new Course(id,teacherId,title,capacity,startDate,endDate);
                courseList.add(course);


            }
        }
        return courseList;
    }

    // 강의명으로 수강정보 조회
    public List<Course> searchCourseTitle(String searchTitle) throws SQLException {

        List<Course> courseList = new ArrayList<>();

        String sql = "select * from course where course_title like ? ";

        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,"%" + searchTitle + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("course_pk");
                String teacherId = rs.getString("teacher_id");
                String title = rs.getString("course_title");
                int capacity = rs.getInt("course_capacity");
                LocalDate startDate = rs.getDate("start_date").toLocalDate();
                LocalDate endDate = rs.getDate("end_date").toLocalDate();


                Course course = new Course(id,teacherId,title,capacity,startDate,endDate);
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
//            courseDAO.insert(1,"100002");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
        // 수강 취소
//        try {
//        courseDAO.delete(1,"100002");
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//    }

        try {
            courseDAO.insertCourse(1,"S101");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


} // end of class
