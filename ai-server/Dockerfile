#ai-server/dockerfile 

# Python 이미지 기반
FROM python:3.10-slim

# 시스템 패키지 설치 (sentence-transformers 실행에 필요)
RUN apt-get update && apt-get install -y \
    build-essential \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 컨테이너 내 작업 디렉토리
WORKDIR /app

# 의존성 설치 파일 복사
COPY requirements.txt .

# 최신 pip 업그레이드 + 의존성 설치
RUN pip install --no-cache-dir --upgrade pip
RUN pip install --no-cache-dir -r requirements.txt

# 전체 코드 복사(프로젝트 파일 복사)
COPY . .

# Flask 서버 포트
EXPOSE 5000

# Flask 서버 실행
CMD ["python", "app.py"]
