package database;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = JDBCUtil.class.getResourceAsStream("/application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
            Class.forName(properties.getProperty("driver")); // JDBC 드라이버 로드
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JDBC 초기화 실패: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("id"),
                    properties.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB 연결 실패: " + e.getMessage());
        }
    }

    // 가변 인자를 받아 모두 close 해주는 유틸
    public static void close(AutoCloseable... resources) {
        for (AutoCloseable res : resources) {
            try {
                if (res != null) res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
