package com.xzvxv3.manager;
import com.xzvxv3.util.DBUtil;
import java.sql.*;

public class AuthManager {

    public AuthManager() {
    }

    // 아이디 중복 방지
    public boolean isDuplicateId(String Id) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        // DB 연결 & SQL 실행 준비
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // 바인딩 작업 (? 자리에 숫자 채우기)
            pstmt.setString(1, Id);

            // 쿼리 실행 -> 결과 집합 가져옴
            try(ResultSet res = pstmt.executeQuery()) {
                if(res.next()) {
                    return res.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 아이디 생성 가능
        return false;
    }

    // 아이디 & 비밀번호 일치 -> 로그인 확인
    public boolean loginCheck(String Id, String password) {
        String sql = "SELECT user_password FROM users WHERE user_id = ?";

        // DB 연결 & SQL 실행 준비
        try(Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // 바인딩 작업 (? 자리에 숫자 채우기)
            pstmt.setString(1, Id);

            // 쿼리 실행 -> 결과 집합 가져옴
            try(ResultSet res = pstmt.executeQuery()) {
                if(res.next()) {
                    // DB에 저장된 비밀번호 가져옴
                    String dbPaasword = res.getString("user_password");
                    // 비밀번호 일치 여부 확인
                    return dbPaasword.equals(password);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 아이디 & 비밀번호 불일치
        return false;
    }
}
