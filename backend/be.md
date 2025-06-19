## 📁 Backend 디렉토리 구조

```bash
backend/
├── .dockerignore                 
├── .gitattributes                
├── .gitignore                    
├── be.md                         
├── build.gradle                  
├── Dockerfile                    
├── gradlew                       
├── gradlew.bat                   
├── HELP.md                       
├── settings.gradle               

├── .gradle/                      # Gradle 빌드 캐시 (Git에 포함 X)
│   └── ...

├── build/                        # 빌드 결과물 디렉토리 (Git에 포함 X)
│   └── ...

├── gradle/
│   └── wrapper/                  # Gradle Wrapper 관련 설정

└── src/
    ├── main/
    │   ├── java/com/modam/backend/        # 📦 메인 백엔드 애플리케이션 코드
    │   │   ├── ModamApplication.java
    │   │
    │   │   ├── config/                    # ⚙️ 환경 설정 (Redis, WebSocket 등)
    │   │   │   ├── RedisConfig.java
    │   │   │   └── WebSocketConfig.java
    │   │
    │   │   ├── controller/                # 🌐 REST API 엔드포인트 컨트롤러
    │   │   │   ├── BookClubController.java
    │   │   │   ├── ChatController.java
    │   │   │   ├── HomeController.java
    │   │   │   ├── MemoController.java
    │   │   │   ├── ReadingNoteController.java
    │   │   │   └── UserController.java
    │   │
    │   │   ├── dto/                       # 📦 요청/응답 DTO 클래스
    │   │   │   ├── AITopicRequestDto.java
    │   │   │   ├── BookClubCommonDto.java
    │   │   │   ├── BookClubCompletedDetailDto.java
    │   │   │   ├── BookClubCreateDto.java
    │   │   │   ├── BookClubDetailDto.java
    │   │   │   ├── BookClubDto.java
    │   │   │   ├── BookClubSearchCondition.java
    │   │   │   ├── BookClubStatusDto.java
    │   │   │   ├── BookDto.java
    │   │   │   ├── ChatMessageDto.java
    │   │   │   ├── CompletedBookClubDto.java
    │   │   │   ├── MemoDto.java
    │   │   │   ├── PasswordUpdateDto.java
    │   │   │   ├── ReadingNoteDto.java
    │   │   │   ├── SummaryCreateDto.java
    │   │   │   ├── UserDto.java
    │   │   │   └── UserNameUpdateDto.java
    │   │
    │   │   ├── handler/                   # 💬 토론 관리 로직
    │   │   │   ├── FreeDiscussionManager.java
    │   │   │   └── SubtopicDiscussionManager.java
    │   │
    │   │   ├── model/                     # 🗃️ JPA 엔티티 클래스 (DB 테이블 매핑)
    │   │   │   ├── Book.java
    │   │   │   ├── BookClub.java
    │   │   │   ├── ChatMessage.java
    │   │   │   ├── DiscussionTopic.java
    │   │   │   ├── Memo.java
    │   │   │   ├── MessageType.java
    │   │   │   ├── Participant.java
    │   │   │   ├── ReadingNote.java
    │   │   │   ├── Summary.java
    │   │   │   ├── User.java
    │   │   │   └── VoteStatus.java
    │   │
    │   │   ├── repository/                # 🗄️ 데이터베이스 접근 (JPA Repository)
    │   │   │   ├── BookClubRepository.java
    │   │   │   ├── BookRepository.java
    │   │   │   ├── ChatMessageRepository.java
    │   │   │   ├── DiscussionTopicRepository.java
    │   │   │   ├── MemoRepository.java
    │   │   │   ├── ParticipantRepository.java
    │   │   │   ├── ReadingNoteRepository.java
    │   │   │   ├── SummaryRepository.java
    │   │   │   └── UserRepository.java
    │   │
    │   │   ├── security/                  # 🔐 인증/인가 및 보안 관련 클래스
    │   │   │   ├── JwtAuthenticationFilter.java
    │   │   │   ├── JwtHandshakeInterceptor.java
    │   │   │   ├── SecurityConfig.java
    │   │   │   └── SwaggerConfig.java
    │   │
    │   │   ├── service/                   # 🧠 비즈니스 로직 처리 계층
    │   │   │   ├── BookClubService.java
    │   │   │   ├── BookService.java
    │   │   │   ├── ChatService.java
    │   │   │   ├── MemoService.java
    │   │   │   ├── ReadingNoteService.java
    │   │   │   └── UserService.java
    │   │
    │   │   └── util/                      # 🧰 공통 유틸리티
    │   │       └── JwtUtil.java
    │   │
    │   └── resources/                     # ⚙️ 설정 파일 및 정적 리소스
    │       ├── application.properties
    │       ├── application_2nd.properties
    │       ├── static/
    │       └── templates/
    │
    └── test/java/com/modam/backend/
        └── ModamApplicationTests.java     # ✅ 통합 테스트 클래스



📘 설명 요약 (전체 클래스 포함)
📌 config/ – 설정
RedisConfig.java

WebSocketConfig.java

📌 controller/ – REST API 엔드포인트
BookClubController.java

ChatController.java

HomeController.java

MemoController.java

ReadingNoteController.java

UserController.java

📌 dto/ – 요청/응답 데이터 객체
AITopicRequestDto.java

BookClubCommonDto.java

BookClubCompletedDetailDto.java

BookClubCreateDto.java

BookClubDetailDto.java

BookClubDto.java

BookClubSearchCondition.java

BookClubStatusDto.java

BookDto.java

ChatMessageDto.java

CompletedBookClubDto.java

MemoDto.java

PasswordUpdateDto.java

ReadingNoteDto.java

SummaryCreateDto.java

UserDto.java

UserNameUpdateDto.java

📌 handler/ – 토론 기능
FreeDiscussionManager.java

SubtopicDiscussionManager.java

📌 model/ – JPA Entity
Book.java

BookClub.java

ChatMessage.java

DiscussionTopic.java

Memo.java

MessageType.java

Participant.java

ReadingNote.java

Summary.java

User.java

VoteStatus.java

📌 repository/ – DB 접근 계층
BookClubRepository.java

BookRepository.java

ChatMessageRepository.java

DiscussionTopicRepository.java

MemoRepository.java

ParticipantRepository.java

ReadingNoteRepository.java

SummaryRepository.java

UserRepository.java

📌 security/ – 보안/인증
JwtAuthenticationFilter.java

JwtHandshakeInterceptor.java

SecurityConfig.java

SwaggerConfig.java

📌 service/ – 비즈니스 로직
BookClubService.java

BookService.java

ChatService.java

MemoService.java

ReadingNoteService.java

UserService.java

📌 util/ – 공통 유틸리티
JwtUtil.java

📌 test/ – 테스트
ModamApplicationTests.java
