## ⚙️ 개발환경 설정

### 사전 준비 사항

- Python 3.10 이상  
- pip 23.x 이상  
- Docker, Docker Compose 설치  
---

### 🖥️ 프로젝트 클론

```bash
git clone https://github.com/your-org/modam-project.git
cd modam-project
```

### AI 서버 (Flask) 설정
1. 가상환경 설정

```bash
python -m venv venv
.\venv\Scripts\activate       # (Windows)
source venv/bin/activate      # (macOS / Linux)
```

2. 패키지 설치

```bash
pip install -r requirements.txt
```

3. 실행

```bash
python app.py
```

### 백엔드 (Spring Boot) 설정

1. 환경 변수 설정
modam-project 루트에 .env 파일을 생성한 후, 아래 형식에 맞춰 작성합니다. <br>
.env 민감 정보는 개인 메일로 교수님께 전달해드렸습니다.

```ini
#########################
# 서버 정보
#########################

# AI 서버 → Spring 서버 호출용 주소
SPRING_SERVER_URL=http://be-modam:8080
# Spring 서버 → AI 서버 호출용 주소
AI_SERVER_URL=http://ai-modam:5000
# React 서버
WEBSOCKET_ALLOWED_ORIGIN=""

#########################
# MySQL 설정
#########################

MYSQL_ROOT_PASSWORD=""
SPRING_DATASOURCE_URL=jdbc:mysql://db-modam:3306/modam_db?serverTimezone=Asia/Seoul
SPRING_DATASOURCE_USERNAME=""
SPRING_DATASOURCE_PASSWORD=""

#########################
# JWT 설정 (Spring Security)
#########################

jwt.secret=""
jwt.expiration=86400000

#########################
# OpenAI API Key (Flask 사용)
#########################

OPENAI_API_KEY=""

```

### 🐳 전체 서비스 실행 (Docker 기반)
1. 프로젝트 루트로 이동
```bash
cd modam
```
2. Docker 실행
```bash
docker compose up --build
```

### 프론트엔드 (React) 설정

1. 의존성 설치

```bash
cd frontend
npm install
npm install @stomp/stompjs sockjs-client
```

2. 실행
```bash
npm start
```


실행 후 다음 경로에서 확인 가능:<br>
프론트엔드 서버: http://localhost:3000 <br>
백엔드 API: http://localhost:8080 <br>
Flask AI 서버: http://localhost:5000 <br>
DB: localhost:3307 (내부 포트는 3306)

### 🗂️ 폴더 구조 요약
```bash

modam-project/
├── backend/         # Spring Boot 서버
├── ai-server/       # Flask AI 서버
├── frontend/        # React 프론트엔드
├── docker-compose.yml
└── .env             # 환경변수 파일
```
