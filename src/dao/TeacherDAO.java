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
                int teacherPk = rs.getInt("tracher_pk");
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

    // 강사 정보(관리자 권한)
    // showTeacherInfo


} // end of main