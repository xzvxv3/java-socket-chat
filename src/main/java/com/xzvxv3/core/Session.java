package com.xzvxv3.core;

import com.xzvxv3.manager.SessionManager;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session implements Runnable {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private boolean closed = false;

    // 유저 아이디
    private String userId;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public void run() {
        try {
            while(true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();

                // 로그인 처리
                if(received.startsWith("[LOGIN]")) {
                    this.userId = received.substring(7);
                    // String msg = userId + "님이 로그인 하셨습니다.";
                    sessionManager.sendAll("[" + userId + "] - connected.");
                    sessionManager.broadcastUserList();
                    continue;
                }

                // 로그아웃 처리
                if(received.startsWith("[LOGOUT]")) break;

                // 채팅 메시지 처리
                sessionManager.sendAll(received);
            }
        } catch(IOException e) {
            System.err.println("[세션 강제 종료]");
        } finally {
            sessionManager.remove(this); // 정상 로그아웃 or 강제 종료 -> 세션 관리자에서 제거
            if(userId != null) {
                sessionManager.sendAll("[" + userId + "] - disconnected.");
            }
            close(); // 자원 정리
        }
    }

    // 클라이언트에게 메시지 보내기
    public void send(String msg) {
        try {
            output.writeUTF(msg);
            output.flush();
        } catch (IOException e) {
            System.out.println("[메시지 전송 에러]");
        }
    }

    // 자원 정리
    public void close() {
        if(closed) return;

        try {
            output.close();
            input.close();
            socket.close();
            closed = true;
            System.out.println("[세션 자원 정리 완료]");
        } catch (IOException e) {
            System.err.println("[세션 자원 정리 실패]");
        }
    }
}
