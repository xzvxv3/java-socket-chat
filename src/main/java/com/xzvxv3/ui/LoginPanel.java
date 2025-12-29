package com.xzvxv3.ui;

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

    private JFrame frame = null;

    public LoginPanel(JFrame frame) {
        this.frame = frame;

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
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new SignUpPanel(frame));
            frame.revalidate();
            frame.repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImage.getImage(), 75, -140, 450, 450, this);
        g.drawImage(basicImage.getImage(), 35, 100, 550, 550, this);
    }
}
