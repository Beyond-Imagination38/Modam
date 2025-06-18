# Python 3.10 기반 슬림 이미지
FROM python:3.10-slim

# 시스템 패키지 설치 (예: sentence-transformers, chromadb 등 일부 native dependencies 필요)
RUN apt-get update && apt-get install -y \
    build-essential \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 작업 디렉토리 설정
WORKDIR /app

# requirements.txt 복사
COPY requirements.txt .

# pip 최신화 및 의존성 설치
RUN pip install --no-cache-dir --upgrade pip \
    && pip install --no-cache-dir -r requirements.txt

# 소스 코드 전체 복사
COPY . .

# 기본 포트 오픈
EXPOSE 5000

# 환경변수 설정 (Flask 앱 위치 및 실행 환경)
ENV FLASK_APP=app.py
ENV FLASK_ENV=production

# Flask 앱 실행 명령
CMD ["flask", "run", "--host=0.0.0.0", "--port=5000"]
