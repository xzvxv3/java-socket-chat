package com.xzvxv3.ui;

import com.xzvxv3.core.Client;

import javax.swing.*;
import java.awt.*;

public class ChatRoomPanel extends JPanel  {

    private ImageIcon titleImage = new ImageIcon("image/chatroom/title_lbl.png");
    private ImageIcon usersImage = new ImageIcon("image/chatroom/users_lbl.png");
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

    private void sendAction() {
        String msg = textField.getText().trim();
        if (!msg.isEmpty()) {
            client.sendMessage(msg); // 서버로 전송
            textField.setText("");
            textField.requestFocus();
        }
    }

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

    // 채팅창
    private void setUpChatArea() {
        textArea = new JTextArea();
        textArea.setEditable(false); // 편집 불가
        textArea.setLineWrap(true);  // 자동 줄바꿈
        textArea.setFont(new Font("Font.DIALOG", Font.PLAIN, 16));

        // 스크롤 패널로 감싸기
        textScrollPane = new JScrollPane(textArea);
        textScrollPane.setBounds(35, 140, 370, 450);
    }

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

        backBtn.addActionListener(e -> {
            frame.replacePanel(new LoginPanel(frame));
            client.close();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImage.getImage(), 75, -140, 450, 450, this);
        g.drawImage(usersImage.getImage(), 280, -60, 450, 450, this);
    }
}
