package com.xzvxv3.ui;

import com.xzvxv3.core.Client;

import javax.swing.*;
import java.awt.*;

public class ChatRoomPanel extends JPanel  {

    private ImageIcon titleImage = new ImageIcon("image/chatroom/title_lbl.png");
    private ImageIcon usersImage = new ImageIcon("image/chatroom/users_lbl.png");
    private ImageIcon cmdImage = new ImageIcon("image/chatroom/cmd_lbl.png");
    private ChatAppFrame frame = null;
    private JTextArea textArea;    // 채팅 내용이 보이는 곳
    private JScrollPane textScrollPane;
    private JTextField textField;  // 메시지를 입력하는 곳
    private JButton sendButton;    // 전송 버튼
    private JTextArea usersArea;
    private JScrollPane usersScrollPane;

    private final JButton backBtn = new JButton();

    private Client client;
    private String id;

    public ChatRoomPanel(ChatAppFrame frame, String id) {
        this.frame = frame;
        this.id = id;
        setLayout(null);
        setBackground(new Color(179, 179, 179));
        setOpaque(true);
        setVisible(true);

        initComponent();

        client = new Client(id, textArea, usersArea);
        client.connectServer("localhost", 12345);

        sendButton.addActionListener(e -> sendAction());
        textField.addActionListener(e -> sendAction());
    }

    // 채팅 입력 -> 서버에 전송
    private void sendAction() {
        String msg = textField.getText().trim();
        if (!msg.isEmpty()) {
            client.sendMessage(msg); // 서버로 전송
            textField.setText("");
            textField.requestFocus();
        }
    }

    // 컴포넌트 초기화
    private void initComponent() {
        setUpChatArea();
        setUpInputArea();
        setUpSendBtn();
        setUpStatusArea();
        setUpBackBtn();

        add(textScrollPane);
        add(textField);
        add(sendButton);
        add(backBtn);
        add(usersScrollPane);
    }

    // 채팅 기록창
    private void setUpChatArea() {
        textArea = new JTextArea();
        textArea.setEditable(false); // 편집 불가
        textArea.setLineWrap(true);  // 자동 줄바꿈
        textArea.setFont(new Font("Font.DIALOG", Font.PLAIN, 16));

        // 스크롤 패널로 감싸기
        textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBounds(35, 140, 370, 450);
    }

    // 유저 목록
    private void setUpStatusArea() {
        usersArea = new JTextArea();
        usersArea.setEditable(false); // 편집 불가
        usersArea.setLineWrap(true);  // 자동 줄바꿈
        usersArea.setFont(new Font("Font.DIALOG", Font.PLAIN, 16));

        // 스크롤 패널로 감싸기
        usersScrollPane = new JScrollPane(usersArea);
        usersScrollPane.setBounds(440, 190, 130, 400);
    }

    // 메시지 입력창
    private void setUpInputArea() {
        textField = new JTextField();
        textField.setBounds(32, 600, 280, 40);
    }

    // 전송 버튼
    private void setUpSendBtn() {
        sendButton = new JButton("Send");
        sendButton.setBounds(328, 600, 80, 40);
    }

    // 뒤로가기 버튼 초기화
    private void setUpBackBtn() {
        backBtn.setIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setRolloverIcon(new ImageIcon("image/common/back_btn.png"));
        backBtn.setBounds(20, 700, 150, 60);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);

        // 뒤로가기 버튼 => 로그아웃 버튼
        backBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "로그아웃 확인",
                    "로그아웃 알림창",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                frame.replacePanel(new LoginPanel(frame));
                client.close();
                System.out.println("[Log out successful]");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImage.getImage(), 75, -140, 450, 450, this);
        g.drawImage(usersImage.getImage(), 280, -60, 450, 450, this);
        g.drawImage(cmdImage.getImage(), 210, 460, cmdImage.getIconWidth(), cmdImage.getIconHeight(), this);
    }
}
