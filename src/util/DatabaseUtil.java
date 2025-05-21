package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  데이터 베이스 연결을 관리하는 유틸리티 클래스
 */
public class DatabaseUtil {
    private static  final String DB_URL = "jdbc:mysql://192.168.0.184:3306/mydatabase?serverTimezone=Asia/Seoul";
    private static final String DB_USER = "academy_admin";
    private static final String DB_PASSWORD = "ghkdlxld";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }


}
