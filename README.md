# Modam: 대면 독서 모임이 어려운 사람들을 위한 AI 진행자 기반 채팅형 독서 모임 서비스

---
## 1. 프로젝트명: Modam
---

이 프로젝트는 AI 기반 비대면 독서모임 플랫폼으로, 사용자가 시간과 장소의 제약 없이 자유롭게 토론 중심의 독서모임에 참여할 수 있도록 돕습니다.
GPT를 활용하여 자동으로 발제문을 생성하고, 실시간 채팅과 요약 기능을 통해 모임을 효율적으로 운영할 수 있습니다.


</br>

---
## 2. 프로젝트 기획 의도
---
본 프로젝트는 다음과 같은 현실적 제약과 사용자 요구를 해결하고자 기획되었습니다.

  **1) 시간적·물리적 제약**
  - 학업, 시험, 과제 등으로 정기적인 오프라인 모임 참석이 어려움
  - 지방 거주자의 경우, 모임 장소까지의 이동 시간과 비용이 부담
  
  **2) 심리적 부담감**
  - 대면 토론에서 의견을 표현하는 것에 불안감이나 긴장감을 느끼는 사용자 존재
  
  **3) 모임 조직의 부담**
  - 발제문 준비, 참여자 관리, 회차 운영 등 모임 주최·운영에 대한 부담
  
  **4) 모임 성사 어려움**
  - 마이너한 장르나 특정 주제를 좋아하는 경우, 취향이 맞는 참여자 확보의 어려움
  
  **5) 토론 내용 정리의 어려움**
  - 토론 후 내용을 기록하거나 구조화하는 데 어려움을 느끼며, 회의처럼 정리된 결과물을 선호하는 사용자 존재
  
  **6) 온라인 채팅에 대한 불안감**
  - 자유로운 표현은 원하지만, 비속어·혐오 표현 등 부적절한 발언에 대한 우려

</br> 이러한 문제들을 해결하기 위해 본 플랫폼은 다음 기능들을 제공합니다.</br>

  **1) GPT 기반 발제문 자동 생성으로 준비 부담 경감**
  
  **2) 실시간 채팅 + AI 필터링을 통한 안전하고 자유로운 토론 환경 제공**
  
  **3) 요약 기능으로 모임 내용을 자동 정리**
  
  **4) 비대면·익명 기반의 심리적으로 안전한 토론 공간 마련**

</br>

---
## 3. 기술 스택
---
### 🖥️ 프론트엔드

![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![STOMP](https://img.shields.io/badge/STOMP-000000?style=for-the-badge&logoColor=white)
![SockJS](https://img.shields.io/badge/SockJS-010101?style=for-the-badge&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-333333?style=for-the-badge&logo=websocket&logoColor=white)

### ⚙️ 백엔드
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Flask](https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)

### 🤖 AI
![PyTorch](https://img.shields.io/badge/PyTorch-EE4C2C?style=for-the-badge&logo=pytorch&logoColor=white)
![GPT-4o (OpenAI)](https://img.shields.io/badge/GPT--4o-412991?style=for-the-badge&logo=openai&logoColor=white)
![LangChain](https://img.shields.io/badge/LangChain-000000?style=for-the-badge&logo=langchain&logoColor=white)
![ChromaDB](https://img.shields.io/badge/ChromaDB-FF6F61?style=for-the-badge)

### 🛢️ 데이터베이스
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### ☁️ 인프라, 배포
![AWS EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

</br>

---
## 4. 아키텍처
---

![시스템 구조도](https://github.com/Beyond-Imagination38/Modam/blob/main/resource/2-2.%EC%8B%9C%EC%8A%A4%ED%85%9C%EA%B5%AC%EC%A1%B0%EB%8F%84.png)

### 🗂️ 폴더 구조 요약
```bash

modam-project/
├── backend/         # Spring Boot 서버
├── ai-server/       # Flask AI 서버
├── frontend/        # React 프론트엔드
├── docker-compose.yml
└── .env             # 환경변수 파일
```

[⚙️ 백엔드 디렉토리 구조](https://github.com/Beyond-Imagination38/Modam/blob/main/backend/be.md)

[🖥️ 프론트엔드 디렉토리 구조](https://github.com/Beyond-Imagination38/Modam/blob/main/frontend/README.md)

[🤖 AI서버 디렉토리 구조](https://github.com/Beyond-Imagination38/Modam/blob/main/ai-server/ai.md)

</br>

---
## 5. 소스코드 설명(주요기능)
---
![모듈](https://github.com/Beyond-Imagination38/Modam/blob/main/resource/2-4.%ED%91%9C%EC%9D%B4%EB%AF%B8%EC%A7%80.png)

### 🖥️ 프론트엔드
#### 주요 모듈 및 역할
1) **UI/UX 사용자 화면 구성 모듈**</br>   :React 컴포넌트 기반으로 전체 기능의 시각적 인터페이스를 구성
2) **사용자 인증 모듈**</br>   : 회원가입 및 로그인 기능 제공, JWT 토큰 기반 인증 유지
3) **채팅 화면 렌더링 모듈**</br>  : STOMP와 SockJS 기반 WebSocket 연결로 실시간 채팅 메시지를 수신하고 화면에 표시. 부적절 메시지는 필터링 반영
4) **독서 모임 등록 및 참여 모듈**</br>  : 새 모임 생성 및 참여, 서버로 데이터 전송, 응답으로 모임 목록 렌더링
5) **독후감 작성 및 메모 저장 모듈**</br>  : 사용자 감상문 및 메모 입력, 서버에 저장 → 발제문 생성 및 요약 시 활용
#### 주요 기능의 데이터 흐름
```graph TD
A[사용자 입력] --> B[로그인 요청 → JWT 발급]
B --> C[모임 생성 / 참여 → 모임 목록 표시]
C --> D[채팅 메시지 입력 → 실시간 전송 및 렌더링]
D --> E[메모 작성 및 저장 → AI 요청 시 활용]
E --> F[모임 종료 후 요약 요청 → 요약 결과 수신 및 표시]
```

### ⚙️ 백엔드
#### 주요 모듈 및 역할
1) **사용자 인증 모듈**</br>  : JWT 기반 사용자 인증 처리
2) **클라이언트/서버 통신 모듈**</br>  : 클라이언트 API 요청 및 응답 처리
3) **채팅방 관리 모듈**</br>  : 실시간 채팅방 생성 및 연결 관리
4) **메시지 저장 및 관리 모듈**</br>  : 채팅 메시지 저장 및 요약용 데이터 관리
5) **AI 메시지 처리 모듈**</br>  : AI 서버에 메시지 전달 → 요약/필터링 결과 수신
6) **발제문 생성 요청 모듈**</br>  : 독후감 기반 발제문 생성 요청 처리
7) **요약 요청 모듈**</br>  : 전체 채팅 로그 전송 및 요약 결과 제공
8) **필터링 요청 모듈**</br>  : 부적절 발언 탐지 요청 및 결과 처리
#### 주요 기능의 데이터 흐름
```graph TD
A[클라이언트 요청] --> B[JWT 인증 처리]
B --> C[모임 생성 / 참여 → DB 저장]
C --> D[WebSocket 연결 → Redis 중계 → 채팅 저장]
D --> E[모임 종료 시 → AI 서버로 로그 전달 → 요약 결과 제공]
```
### 🤖 AI
#### 주요 모듈 및 역할
1) **입력 데이터 처리 모듈**</br>  : PyPDF2 + LangChain으로 책 PDF 분할 및 전처리
2) **벡터 임베딩 및 저장 모듈**</br>  : HuggingFace 모델로 임베딩 후 ChromaDB에 저장
3) **문맥 기반 검색 모듈 (Retriever)**</br>  : 독후감 기반 관련 문단 검색
4) **발제문 생성 모듈**</br>  : 검색 문단 + 독후감을 GPT-4o에 전달해 발제문 생성
5) **요약 생성 모듈**</br>  : 채팅 로그 → PromptTemplate → GPT-4o 요약 요청
6) **채팅 필터링 모듈**</br>  : KSS로 문장 분리 + 키워드 필터링 → KcELECTRA 모델 기반 문맥 판단
7) **API 통신 처리 모듈**</br>  : (Flask)REST API 요청 수신 → 발제문/요약/필터링 결과 응답
#### 주요 기능의 데이터 흐름
```graph TD
A[독후감 + book_id] --> B[ChromaDB 검색 → 유사 문단 추출]
B --> C[프롬프트 구성 → GPT-4o 발제문 생성]
D[모임 종료 → 전체 채팅 로그 수신] --> E[GPT-4o 요약 요청 → 결과 저장 및 반환]
F[채팅 메시지 수신] --> G[KSS → 키워드 필터 → KcELECTRA → 결과 반환]
```

</br>

---
## 6. 개발환경 설정
---
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
1) 가상환경 설정

```bash
python -m venv venv
.\venv\Scripts\activate       # (Windows)
source venv/bin/activate      # (macOS / Linux)
```

2) 패키지 설치

```bash
pip install -r requirements.txt
```

3) 실행

```bash
python app.py
```

### 백엔드 (Spring Boot) 설정

1) 환경 변수 설정
modam-project 루트에 .env 파일을 생성한 후, 아래 형식에 맞춰 작성합니다. <br>
.env 민감 정보는 개인 메일로 교수님께 전달해드렸습니다.

```ini
#########################
#프로젝트 디렉토리로 이동
cd ../Modam
# .env 추가
nano .env
#########################

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

#########################
# .env를 현재 쉘에 적용
#########################

set -a && source .env && set +a

```

### 🐳 전체 서비스 실행 (Docker 기반)
1) 프로젝트 루트로 이동
```bash
cd modam
```
2) Docker 실행
```bash
docker compose up --build
```

실행 후 다음 경로에서 확인 가능:<br>
백엔드 API: http://localhost:8080 <br>
Flask AI 서버: http://localhost:5000 <br>
DB: localhost:3307 (내부 포트는 3306)
