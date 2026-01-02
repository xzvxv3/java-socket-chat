package com.xzvxv3.core;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private JTextArea textArea;
    private JTextArea usersArea;
    private String id;

    public Client(String id, JTextArea textArea, JTextArea usersArea) {
        this.id = id;
        this.textArea = textArea;
        this.usersArea = usersArea;
    }

    // 서버 연결
    public void connectServer(String serverName, int port) {
        try {
            socket = new Socket(serverName, port); // TCP 3Way HandShake
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            // 서버에 로그인 정보를 보냄
            output.writeUTF("[LOGIN]" + id);
            output.flush();
        } catch (IOException e) {
            System.out.println("[클라이언트의 서버 연결 실패]");
            return;
        }

        // 서버로부터 메시지를 받는 스레드
        new Thread(() -> {
            try {
                while(true) {
                    String msg = input.readUTF();

                    // 유저 정보 갱신
                    if(msg.startsWith("[USERLIST]")) {
                        String userList = msg.substring(10);
                        String[] users = userList.split(",");

                        usersArea.setText(""); // 기존 목록 지우기
                        for(String user : users) {
                            if(!user.isEmpty()) usersArea.append(user + "\n");
                        }
                    }
                    else {
                        textArea.append(msg + "\n"); // 메시지 출력
                        textArea.setCaretPosition(textArea.getDocument().getLength()); // 스크롤 맨 아래로
                    }
                }
            } catch(IOException e) {
                // System.out.println("[수신 스레드 종료]");
                textArea.append("서버와 연결이 끊겼습니다\n");
            }
        }).start();
    }

    // 메시지 보내기
    public void sendMessage(String msg) {
        try {
            output.writeUTF("[" + id + "]: " + msg);
            output.flush();
        } catch (IOException e) {
            System.out.println("[메시지 전송 실패]");
        }
    }

    // 자원 정리
    public void close() {
        try {
            output.writeUTF("[LOGOUT]");
            output.close();
            input.close();
            socket.close();
            System.out.println("[클라이언트 자원 정리 완료]");
        } catch (IOException e) {
            System.err.println("[클라이언트 자원 정리 실패]");
        }
    }
}
