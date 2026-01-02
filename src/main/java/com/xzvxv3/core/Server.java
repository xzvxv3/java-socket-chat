package com.xzvxv3.core;

import com.xzvxv3.manager.SessionManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        SessionManager sessionManager = new SessionManager();
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.printf("[서버 시작 - 포트: %d]\n", PORT);

        // 관리자 스레드 - 종료 감시
        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            System.out.print("[서버 종료 'exit' 입력]: ");

            while (true) {
                String cmd = sc.nextLine();
                if (cmd.equals("exit")) {
                    System.out.println("[자원 정리 시작]");
                    try {
                        sessionManager.closeAll(); //  모든 클라이언트 연결 끊기
                        serverSocket.close(); // 대기 중인 accept()를 강제로 깨움
                        System.out.println("[자원 정리 완료]");
                    } catch (IOException e) {
                        System.out.println("자원 정리 중 에러: " + e.getMessage());
                    } finally {
                        sc.close();
                    }
                    break;
                }
            }
        }).start();

        // 소켓 연결
        while (true) {
            try {
                // 대기 중 소켓이 닫히면 SocketException 발생
                Socket socket = serverSocket.accept();

                Session session = new Session(socket, sessionManager);
                sessionManager.add(session);

                Thread sessionThread = new Thread(session);
                sessionThread.start();

            } catch (SocketException e) {
                System.out.println("[서버 소켓 닫힘]");
                break;
            }
        }

        System.out.println("[서버가 종료되었습니다.]");
    }
}