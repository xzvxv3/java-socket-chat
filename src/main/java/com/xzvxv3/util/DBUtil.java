package com.xzvxv3.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

// 데이터베이스 관리 클래스
public class DBUtil {

    // DB 접속 정보를 담을 설정 객체
    private static Properties props = new Properties();

    static {
        try (InputStream is = new FileInputStream("src/main/resources/db.properties")) {
            if (is == null) {
                System.err.println("db.properties Not Found.");
                System.exit(0);
            } else {
                props.load(is);
                System.out.println("db.properties Load Success.");
            }
        } catch (IOException e) {
            System.err.println("DB 설정 파일을 읽는 중 오류 발생");
            e.printStackTrace();
        }
    }

    // DB 연결
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (SQLException e) {
            System.out.println("[데이터베이스 연결 실패] - 종료");
            System.exit(0);
        }
        return null;
    }
}