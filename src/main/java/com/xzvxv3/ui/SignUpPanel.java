package com.xzvxv3.ui;

import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends JPanel {
    private ImageIcon titleImage = new ImageIcon("image/signup/title_lbl.png");
    private ImageIcon basicImage = new ImageIcon("image/signup/basic_lbl.png");

    private JTextField idTextField = new JTextField();
    private JPasswordField passwordTextField = new JPasswordField(20); // Password 입력칸
    private JPasswordField checkPasswordTextField = new JPasswordField(20); // Check Password 입력칸
    private JButton backBtn = new JButton();
    private JButton createAccountBtn = new JButton();
    private JButton checkAvailabilityBtn = new JButton();

    private JFrame frame = null;

    public SignUpPanel(JFrame frame) {
        this.frame = frame;
        setLayout(null);
        setBackground(new Color(179, 179, 179));
        setOpaque(true);

        // 컴포넌트 초기화
        initComponent();
    }

    // 컴포넌트 초기화
    private void initComponent() {
        idTextField.setBounds(190, 240, 220, 40);
        passwordTextField.setBounds(190, 350, 220, 40);
        checkPasswordTextField.setBounds(190, 470, 220, 40);

        setUpBackBtn();
        setUpCreateAccountBtn();
        setUpCheckAvailabilityBtn();

        add(createAccountBtn);
        add(backBtn);
        add(checkAvailabilityBtn);

        add(idTextField);
        add(passwordTextField);
        add(checkPasswordTextField);
    }

    // 아이디 중복 체크 버튼
    private void setUpCheckAvailabilityBtn() {
        checkAvailabilityBtn.setIcon(new ImageIcon("image/signup/check_btn.png"));
        checkAvailabilityBtn.setRolloverIcon(new ImageIcon("image/signup/check_btn_rollover.png"));
        checkAvailabilityBtn.setBounds(415, 240, 60, 40);
        checkAvailabilityBtn.setBorderPainted(false);
        checkAvailabilityBtn.setFocusPainted(false);
    }

    // 계정 생성 버튼 초기화
    private void setUpCreateAccountBtn() {
        createAccountBtn.setIcon(new ImageIcon("image/signup/create_account_btn.png"));
        createAccountBtn.setRolloverIcon(new ImageIcon("image/signup/create_account_btn_rollover.png"));
        createAccountBtn.setBounds(250, 700, 400, 60);
        createAccountBtn.setBorderPainted(false);
        createAccountBtn.setFocusPainted(false);
    }

    // 뒤로가기 버튼 초기화
    private void setUpBackBtn() {
        backBtn.setIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setRolloverIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setBounds(20, 700, 150, 60);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);

        backBtn.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel(frame));
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
