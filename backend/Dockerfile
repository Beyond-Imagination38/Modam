# Java 17 기반
FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# 로컬에서 빌드된 JAR 파일만 복사
COPY Modam-0.0.1-SNAPSHOT.jar app.jar

# Gradle Wrapper 실행 권한 부여
#RUN chmod +x ./gradlew

# 앱 빌드
#RUN ./gradlew build --no-daemon

# 포트 노출
EXPOSE 8080

# 실행할 JAR 복사
#CMD ["java", "-jar", "build/libs/Modam-0.0.1-SNAPSHOT.jar"]

# JAR 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

#env 설정
ENV SPRING_PROFILES_ACTIVE=2nd
