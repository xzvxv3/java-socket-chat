package com.xzvxv3.manager;

import com.xzvxv3.core.Session;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    // 리스트에 세션 추가
    public synchronized void add(Session session) {
        sessions.add(session);
    }

    // 리스트에서 세션 삭제 & 유저 목록 갱신
    public synchronized void remove(Session session) {
        sessions.remove(session);
        broadcastUserList();
    }

    // 세션 자원 정리
    public synchronized void closeAll() {
        for(Session s : sessions) {
            s.close();
        }
        sessions.clear();
    }

    // 모든 유저에게 전송
    public synchronized void sendAll(String from, String msg) {
        for(Session s : sessions) {
            if(!s.isBlocked(from)) {
                s.send(msg);
            }
        }
    }

    // 귓속말
    public synchronized void sendTo(String msg, String from, String to) {
        for(Session s : sessions) {
            if((!s.isBlocked(from) && s.getUserId().equals(to)) || s.getUserId().equals(from)) {
                s.send("[" + from + "] >> " + msg);
            }
        }
    }

    // 유저 목록 갱신
    public synchronized void broadcastUserList() {
        StringBuilder sb = new StringBuilder("[USERLIST]");

        for(Session s : sessions) {
            if(s.getUserId() != null) {
                sb.append(s.getUserId()).append(",");
            }
        }

        for(Session s : sessions) {
            s.send(sb.toString());
        }
    }
}
