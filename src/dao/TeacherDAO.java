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
    public List<Teacher> searchAllTeacher() throws SQLException{
        List<Teacher> teacherList = new ArrayList<>();
        String sql ="SELECT * from teacher ";
        try(
                Connection conn = DatabaseUtil.getConnection();
                Statement stmt =conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int teacherPk = rs.getInt("teacher_pk");
                String accessLevel = rs.getString("access_level");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String teacherPhone = rs.getString("teacher_phone");
                String teacherEmail = rs.getString("teacher_email");
                Teacher teacher = new Teacher(teacherPk, accessLevel, teacherId, teacherName, teacherPhone, teacherEmail);
                teacherList.add(teacher);
            }
        }
        return teacherList;
    }

    //강사 이름으로 조회
    public List<Teacher> searchTeacherByTitle(String searchName) throws SQLException{
        List<Teacher> teacherList = new ArrayList<>();
        String sql = "select * from teacher where teacher_name like ? ";

        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, "%" + searchName + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int teacherPk = rs.getInt("teacher_pk");
                String accessLevel = rs.getString("access_level");
                String teacherId = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String teacherPhone = rs.getString("teacher_phone");
                String teacherEmail = rs.getString("teacher_email");
                Teacher teacher = new Teacher(teacherPk, accessLevel, teacherId, teacherName, teacherPhone, teacherEmail);
                teacherList.add(teacher);
            }
        }
        return teacherList;
    }

    // 강사 id 인증 체크
    // 로그인쪽에서 넘어온 id 값을 teacher 테이블에 조회하여 있을경우 teacher 객체로 반환
    // 없으면 null로 반환
    public Teacher authenticateTeacher(String teacherId) throws  SQLException{

        String sql = "select * from teacher where teacher_id like ? ";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,teacherId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Teacher teacherDTO = new Teacher();
                teacherDTO.setTeacherPk(rs.getInt("teacher_pk"));
                teacherDTO.setAccessLevel(rs.getString("access_level"));
                teacherDTO.setTeacherId(rs.getString("teacher_id"));
                teacherDTO.setTeacherName(rs.getString("teacher_name"));
                teacherDTO.setTeacherPhone(rs.getString("teacher_phone"));
                teacherDTO.setTeacherEmail(rs.getString("teacher_email"));
                return teacherDTO;

            }
        }
        return null;
    }


//    // 강사 정보(관리자 권한)
//    // showTeacherInfo

//    public static void main(String[] args) {
//        // 전체 조회 테스트
//        BookDAO bookDAO = new BookDAO();
//        try {
//            // bookDAO.getAllBooks();
//            ArrayList<Book> selectedBookList =
//                    (ArrayList) bookDAO.searchBooksTitle("입문");
//            for (int i = 0; i < selectedBookList.size(); i++) {
//                System.out.println(selectedBookList.get(i));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }



} // end of main