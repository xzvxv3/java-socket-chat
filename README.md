# **실시간 채팅 프로그램 - Java Talk**

자바 스윙과 TCP 소켓을 활용하여 1:N 실시간 채팅 프로그램을 구현하였습니다.  

서버–클라이언트 모델을 기반으로 하며, 데이터베이스 연동을 통해 로그인 및 회원가입 기능을 제공합니다.

&nbsp;

## Tech Stack

- Language: Java 21  
- Library: Java Swing(UI), JDBC  
- DataBase: MySQL  
- Tools: IntelliJ IDEA, Git

&nbsp;

## System Architecture

- Server Relay Model: 클라이언트 간 직접 통신이 아닌 서버가 모든 메시지를 수신하여 조건에 맞는 클라이언트에게 전달하는 구조 사용
  
- Multi-Threading: 클라이언트별 독립적인 Session 스레드 할당 -> 다중 접속 환경에서의 동시성 및 통신 독립성 보장
  
- DAO Pattern: UserRepository을 통해 DB 접근 로직 분리 -> 유지보수성 고려

&nbsp;

## Key Features

1. 사용자 인증 및 관리
   - 회원가입/로그인: UserRepository와 DB를 연동하여 실제 가입된 사용자만 채팅 참여 가능하게 함
   - 중복 체크: 가입 시, DB을 통한 아이디 중복 확인

2. 실시간 채팅 기능
   - 1:N 전체 채팅: 모든 접속자에게 실시간 메시지 전송
   - 실시간 접속자 명단: 유저 로그인/로그아웃 시 모든 클라이언트의 유저 리스트 UI가 즉시 동기화

3. 채팅 명령어 기능
   - 귓속말 기능: 특정 ID에게 1:1 메시지를 보낼 수 있음. 다른 클라이언트들은 볼 수 없음
   - 차단/해제 기능: 특정 ID를 차단 목록에 등록하여 해당 유저의 메시지가 본인에게 전달되지 않도록 함

&nbsp;

## Communication Protocol

서버와 클라이언트 간의 통신 규약

|분류|명령 태그|데이터 형식|설명|
|:---:|:---:|:---|:---:|
|인증|[LOGIN]|[LOGIN] ID|접속 시 유저 정보를 세션에 등록 및 입장 알림|
|종료|[LOGOUT]|[LOGOUT]|접속 종료 알림 & 서버/클라이언트 자원 정리 요청|
|동기화|[USERLIST]|[USERLIST] ID1, ID2, ...|서버가 모든 클라이언트에게 현재 접속자 명단 전송|
|귓속말|/w|/w ID Message|특정 ID를 가진 유저에게 1:1 메시지 전달|
|차단|/b|/b ID|특정 ID를 차단 목록에 추가|
|차단 해제|/ub|/ub ID|특정 ID를 차단 목록에서 제거|
|채팅|NONE|Message|태그가 없는 경우 전체 유저에게 메시지 보냄|

&nbsp;

## Project Structure

<pre>
src
└── com.xzvxv3
    ├── <b>core</b>
    │   ├── Server.java           <font color="#808080"># ServerSocket 대기 및 세션 생성 관리</font>
    │   ├── Session.java          <font color="#808080"># 클라이언트별 개별 통신 스레드 (Dispatcher)</font>
    │   └── Client.java           <font color="#808080"># 서버 접속 및 실시간 메시지 송수신 처리</font>
    ├── <b>manager</b>
    │   ├── SessionManager.java   <font color="#808080"># 실시간 세션 관리 및 메시지 중계(Relay)</font>
    │   └── AuthManager.java      <font color="#808080"># 사용자 인증 및 회원가입 비즈니스 로직</font>
    ├── <b>dao</b>
    │   └── UserRepository.java    <font color="#808080"># Database(MySQL) 접근 및 관리</font>
    ├── <b>model</b>
    │   └── User.java             <font color="#808080"># 사용자 정보 데이터 모델 (DTO)</font>
    ├── <b>ui</b>
    │   ├── ChatAppFrame.java     <font color="#808080"># 메인 GUI 프레임 (화면 전환 제어)</font>
    │   ├── LoginPanel.java       <font color="#808080"># 사용자 로그인 인터페이스</font>
    │   ├── SignUpPanel.java      <font color="#808080"># 사용자 회원가입 인터페이스</font>
    │   └── ChatRoomPanel.java    <font color="#808080"># 실시간 채팅방 인터페이스</font>
    └── <b>util</b>
        └── DBUtil.java           <font color="#808080"># JDBC 드라이버 로드 및 DB 연결 관리</font>
</pre>

&nbsp;

## How to Run

<img width="1920" height="1080" alt="1" src="https://github.com/user-attachments/assets/4bda18a1-6434-4965-90a8-121723bb04bf" />

---

<img width="1920" height="1080" alt="2" src="https://github.com/user-attachments/assets/8eaa62f0-a27b-46a8-982a-49c08a3375cb" />

---

<img width="1920" height="1080" alt="3" src="https://github.com/user-attachments/assets/7c5abe41-dd75-4832-a465-1be71d64fac3" />

---

<img width="1920" height="1080" alt="4" src="https://github.com/user-attachments/assets/74264b1a-e7da-4f31-85f1-4ab6bdba434a" />

---

<img width="1920" height="1080" alt="5" src="https://github.com/user-attachments/assets/34dec176-a9da-4eed-9c30-4cd86029629d" />

---

<img width="1920" height="1080" alt="7" src="https://github.com/user-attachments/assets/c58e9e7a-20c7-49d8-990b-8167ca30cfaa" />

---

<img width="1920" height="1080" alt="8" src="https://github.com/user-attachments/assets/f8c925de-02a6-4e8f-9afd-c438ce5870fc" />

