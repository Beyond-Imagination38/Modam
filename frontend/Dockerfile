#fe Dockerfile예시


# Node.js 기반 이미지 사용
FROM node:20-alpine

# 컨테이너 안의 작업 디렉토리 지정
WORKDIR /app

# package.json과 package-lock.json 복사
COPY package*.json ./

# 의존성 설치
RUN npm install

# 전체 소스 복사
COPY . .

# React 개발 서버가 사용할 포트
EXPOSE 3000

# 개발 서버 실행 (npm start)
CMD ["npm", "start"]
