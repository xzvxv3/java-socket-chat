package com.xzvxv3.ui;

import com.xzvxv3.manager.AuthManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    // Java Talk, 기본 틀 이미지
    private ImageIcon titleImage = new ImageIcon("image/login/title_lbl.png");
    private ImageIcon basicImage = new ImageIcon("image/login/basic_lbl.png");

    // 컴포넌트 요소
    private JTextField idTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField(20); // Password 입력칸
    private JButton loginBtn = new JButton();
    private JButton signUpBtn = new JButton();

    private AuthManager authManager = null;
    private ChatAppFrame frame = null;

    public LoginPanel(ChatAppFrame frame) {
        this.frame = frame;
        authManager = frame.getAuthManager();

        setLayout(null);
        setBackground(new Color(179, 179, 179));
        setOpaque(true);

        // 컴포넌트 초기화
        initComponent();
    }

    // 컴포넌트 초기화
    private void initComponent() {
        idTextField.setBounds(190, 280, 220, 40);
        passwordTextField.setBounds(190, 430, 220, 40);

        setupLoginBtn();
        setupSignUpBtn();

        add(idTextField);
        add(passwordTextField);
        add(loginBtn);
        add(signUpBtn);
    }

    // 로그인 버튼 초기 설정
    private void setupLoginBtn() {
        loginBtn.setBounds(395, 550, 100, 50);
        loginBtn.setIcon(new ImageIcon("image/login/login_btn.png"));
        loginBtn.setRolloverIcon(new ImageIcon("image/login/login_btn_rollover.png"));
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> {
            String id = idTextField.getText();
            char[] password = passwordTextField.getPassword();

            if(!authManager.loginCheck(id, password)) {
                JOptionPane.showMessageDialog(this, "로그인에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                System.out.println("[로그인 실패]");
                return;
            }

            frame.replacePanel(new ChatRoomPanel(frame, id));
            System.out.println("[로그인 성공]: " + id);
        });
    }

    // 회원가입 버튼 초기 설정
    private void setupSignUpBtn() {
        signUpBtn.setBounds(290, 550, 100, 50);
        signUpBtn.setIcon(new ImageIcon("image/login/signup_btn.png"));
        signUpBtn.setRolloverIcon(new ImageIcon("image/login/signup_btn_rollover.png"));
        signUpBtn.setBorderPainted(false);
        signUpBtn.setFocusPainted(false);

        // 회원 가입 화면으로 이동
        signUpBtn.addActionListener(e -> {
            frame.replacePanel(new SignUpPanel(frame));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImage.getImage(), 75, -140, 450, 450, this);
        g.drawImage(basicImage.getImage(), 35, 100, 550, 550, this);
    }
}
