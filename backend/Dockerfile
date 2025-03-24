# 1. Java 17 기반 이미지 사용 (버전에 맞게 조정 가능)
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. Gradle 빌드를 위한 빌드 도구 설치용 이미지
COPY . /app

# 4. Gradle Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# 5. 앱 빌드
RUN ./gradlew build --no-daemon

# 6. 실행할 JAR 복사
CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
