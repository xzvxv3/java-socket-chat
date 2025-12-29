package com.xzvxv3.ui;

import com.xzvxv3.dao.UserRepository;
import com.xzvxv3.manager.AuthManager;
import com.xzvxv3.manager.SessionManager;

import javax.swing.*;

public class ChatAppFrame extends JFrame {

    // 유저 관리자
    private UserRepository userRepository = new UserRepository();
    // 권한 관리자
    private AuthManager authManager = new AuthManager(userRepository);
    // 세션 관리자
    private SessionManager sessionManager = new SessionManager();

    public ChatAppFrame() {
        super("Java Talk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600, 800);
        setLocation(700, 100);
        getContentPane().add(new LoginPanel(this));
        setVisible(true);
    }

    // 화면 교체
    public void replacePanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    // 매니저 Getter
    public AuthManager getAuthManager() { return authManager; }
    public UserRepository getUserRepository() { return userRepository; }
    public SessionManager getSessionManager() { return sessionManager; }
}
