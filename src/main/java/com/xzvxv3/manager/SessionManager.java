package com.xzvxv3.manager;

import com.xzvxv3.core.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    public synchronized void add(Session session) {
        sessions.add(session);
    }

    public synchronized void remove(Session session) {
        sessions.remove(session);
        broadcastUserList();
    }

    public synchronized void closeAll() {
        for(Session s : sessions) {
            s.close();
        }
        sessions.clear();
    }

    public synchronized void sendAll(String msg) {
        for(Session s : sessions) {
            s.send(msg);
        }
    }

    public synchronized void broadcastUserList() {
        StringBuilder sb = new StringBuilder("[USERLIST]");

        for(Session s : sessions) {
            if(s.getUserId() != null) {
                sb.append(s.getUserId()).append(",");
            }
        }

        sendAll(sb.toString());
    }
}
