package com.xzvxv3.manager;
import com.xzvxv3.dao.UserRepository;
import com.xzvxv3.model.User;
import com.xzvxv3.util.DBUtil;
import java.sql.*;
import java.util.Arrays;

public class AuthManager {

    private final UserRepository userRepository;

    public AuthManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 아이디 중복 확인
    public boolean isDuplicateId(String id) {
        return userRepository.findUserById(id) != null;
    }

    // 로그인 확인
    public boolean loginCheck(String id, char[] password) {
        User user = userRepository.findUserById(id);

        if(user != null) {
            // 로그인 가능
            char[] userPassword = user.getPassword().toCharArray();

            boolean isMatch = Arrays.equals(userPassword, password);

            return isMatch;
        }

        // 로그인 불가능
        return false;
    }

}
