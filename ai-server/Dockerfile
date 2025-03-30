#ai-server dockerfile 예시

# Python 이미지 기반
FROM python:3.10-slim

# 컨테이너 내 작업 디렉토리
WORKDIR /app

# 의존성 설치 파일 복사
COPY requirements_ex.txt .

# 필요한 패키지 설치
RUN pip install --no-cache-dir -r requirements_ex.txt

# 전체 코드 복사
COPY . .

# Flask 서버 포트
EXPOSE 5000

# Flask 서버 실행
CMD ["python", "app_ex.py"]
