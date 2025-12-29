package com.xzvxv3.dao;

import com.xzvxv3.model.User;
import com.xzvxv3.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    public UserRepository() {
    }

    // 회원 추가
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (user_id, user_password) VALUES (?, ?)";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPassword());

            int res = pstmt.executeUpdate();

            if(res > 0) {
                System.out.printf("[회원 가입 완료!] - %s\n", user.getId());
                // 회원 가입 성공
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 회원 가입 실패
        return false;
    }

    // 아이디로 회원 찾기
    public User findUserById(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try(ResultSet res = pstmt.executeQuery()) {
                if(res.next()) {
                    return new User(
                            res.getString("user_id"),
                            res.getString("user_password")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 모든 유저 조회
    public List<User> findAllUsers() {
        String sql = "SELECT user_id, user_password FROM users";
        List<User> userList = new ArrayList<>();

        try(Connection connection = DBUtil.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery()) {

            while (res.next()) {
                User user = new User(
                    res.getString("user_id"),
                    res.getString("user_password")
                );

                userList.add(user);
            }

            System.out.printf("[전체 유저 조회 완료] - 총 %d명\n", userList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }
}
