# Java 17 기반
FROM openjdk:17-jdk-slim

# 작업 디렉토리 생성
WORKDIR /app

# Gradle 빌드를 위한 빌드 도구 설치용 이미지
COPY . /app

# Gradle Wrapper 실행 권한 부여
RUN chmod +x ./gradlew

# application_2nd.properties 명시적으로 복사
# 이미 COPY . /app 으로 포함되긴 하지만 명시적으로 한 번 더
COPY src/main/resources/application_2nd.properties /app/src/main/resources/application_2nd.properties


# 앱 빌드
RUN ./gradlew build --no-daemon

# 포트 노출
EXPOSE 8080

# 실행할 JAR 복사
CMD ["java", "-jar", "build/libs/Modam-0.0.1-SNAPSHOT.jar"]
