## ⚙️ 개발환경 설정

### ✅ 사전 준비 사항

- Python 3.10 이상  
- pip 23.x 이상  
- Docker, Docker Compose 설치  
- VSCode 설치 (권장)

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

2. 환경변수 설정

modam-project/ai-server 경로에 .env 파일 생성

아래 형식으로 작성

ini

OPENAI_API_KEY=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
SPRING_SERVER_URL=http://localhost:8080

3. 패키지 설치

```bash
pip install -r requirements.txt
```

4. 실행

```bash
python app.py
```

### 백엔드 (Spring Boot) 설정

1. 환경 변수 설정

modam-project/ 루트 디렉토리에 .env 파일 생성

아래 형식에 맞춰 작성

ini

MYSQL_ROOT_PASSWORD=0000
SPRING_DATASOURCE_URL=jdbc:mysql://db-modam:3306/modam_db?serverTimezone=Asia/Seoul
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=0000

JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

AI_SERVER_URL=http://localhost:5000
WEBSOCKET_ALLOWED_ORIGIN=http://localhost:3000
2. 실행 (Docker 기반)

```bash
docker compose up --build
```

실행 후 다음 경로에서 확인 가능:

백엔드 API: http://localhost:8080

Flask AI 서버: http://localhost:5000

DB: localhost:3307 (내부 포트는 3306)

### 🗂️ 폴더 구조 요약
```bash

modam-project/
├── backend/         # Spring Boot 서버
├── ai-server/       # Flask AI 서버
├── frontend/        # React 프론트엔드 (선택)
├── docker-compose.yml
└── .env             # 환경변수 파일
```
