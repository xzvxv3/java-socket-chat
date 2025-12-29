package com.xzvxv3.ui;

import com.xzvxv3.dao.UserRepository;
import com.xzvxv3.manager.AuthManager;
import com.xzvxv3.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SignUpPanel extends JPanel {
    private ImageIcon titleImage = new ImageIcon("image/signup/title_lbl.png");
    private ImageIcon basicImage = new ImageIcon("image/signup/basic_lbl.png");

    private final JTextField idTextField = new JTextField();
    private final JPasswordField passwordTextField = new JPasswordField(20); // Password 입력칸
    private final JPasswordField checkPasswordTextField = new JPasswordField(20); // Check Password 입력칸
    private final JButton backBtn = new JButton();
    private final JButton createAccountBtn = new JButton();
    private final JButton checkAvailabilityBtn = new JButton();

    private AuthManager authManager = null;
    private UserRepository userRepository = null;
    private ChatAppFrame frame = null;

    // 아이디 중복 체크
    private boolean isIdChecked = false;

    public SignUpPanel(ChatAppFrame frame) {
        this.frame = frame;
        authManager = frame.getAuthManager();
        userRepository = frame.getUserRepository();

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

        checkAvailabilityBtn.addActionListener(e -> {
            String id = idTextField.getText();

            // 비어있는 아이디
            if(id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 중복 체크
            if (authManager.isDuplicateId(id)) {
                JOptionPane.showMessageDialog(this, "이미 사용 중인 아이디입니다.", "중복 확인", JOptionPane.ERROR_MESSAGE);
                idTextField.setText(""); // 입력란 비우기 (선택사항)
                idTextField.requestFocus(); // 포커스 주기
            } else {
                // 사용 가능한 경우
                JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.", "중복 확인", JOptionPane.INFORMATION_MESSAGE);
                isIdChecked = true;
            }
        });
    }

    // 계정 생성 버튼 초기화
    private void setUpCreateAccountBtn() {
        createAccountBtn.setIcon(new ImageIcon("image/signup/create_account_btn.png"));
        createAccountBtn.setRolloverIcon(new ImageIcon("image/signup/create_account_btn_rollover.png"));
        createAccountBtn.setBounds(250, 700, 400, 60);
        createAccountBtn.setBorderPainted(false);
        createAccountBtn.setFocusPainted(false);

        // 계정 생성
        createAccountBtn.addActionListener(e -> {
            if(!isIdChecked) {
                JOptionPane.showMessageDialog(this, "아이디 중복 체크를 해주세요.", "회원 가입 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            char[] password = passwordTextField.getPassword();
            char[] checkPassword = checkPasswordTextField.getPassword();

            if(!Arrays.equals(password, checkPassword)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "회원 가입 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String id = idTextField.getText();
            if(userRepository.addUser(new User(id, String.valueOf(password)))) {
                JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);

                // 로그인 화면으로 이동
                frame.replacePanel(new LoginPanel(frame));
            } else {
                // 회원 가입 실패
                JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다. 다시 시도해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // 뒤로가기 버튼 초기화
    private void setUpBackBtn() {
        backBtn.setIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setRolloverIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setBounds(20, 700, 150, 60);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);

        backBtn.addActionListener(e -> {
            frame.replacePanel(new LoginPanel(frame));
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImage.getImage(), 75, -140, 450, 450, this);
        g.drawImage(basicImage.getImage(), 35, 100, 550, 550, this);
    }
}
