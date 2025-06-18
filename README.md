Data 볼륨에 대한 폴더 경로의 목록입니다.
볼륨 일련 번호가 00000018 8AD3:DFC8입니다.
E:\GRAD_PROJECT\FINAL_GRAD_PROJECT\MODAM\BACKEND
│  .dockerignore
│  .gitattributes
│  .gitignore
│  be.md
│  build.gradle
│  Dockerfile
│  gradlew
│  gradlew.bat
│  HELP.md
│  settings.gradle
│  tree.txt
│  
├─.gradle
│  │  file-system.probe
│  │  
│  ├─8.11.1
│  │  │  gc.properties
│  │  │  
│  │  ├─checksums
│  │  │      checksums.lock
│  │  │      md5-checksums.bin
│  │  │      sha1-checksums.bin
│  │  │      
│  │  ├─executionHistory
│  │  │      executionHistory.bin
│  │  │      executionHistory.lock
│  │  │      
│  │  ├─expanded
│  │  ├─fileChanges
│  │  │      last-build.bin
│  │  │      
│  │  ├─fileHashes
│  │  │      fileHashes.bin
│  │  │      fileHashes.lock
│  │  │      resourceHashesCache.bin
│  │  │      
│  │  └─vcsMetadata
│  ├─8.13
│  │  │  gc.properties
│  │  │  
│  │  ├─checksums
│  │  │      checksums.lock
│  │  │      md5-checksums.bin
│  │  │      sha1-checksums.bin
│  │  │      
│  │  ├─executionHistory
│  │  │      executionHistory.bin
│  │  │      executionHistory.lock
│  │  │      
│  │  ├─expanded
│  │  ├─fileChanges
│  │  │      last-build.bin
│  │  │      
│  │  ├─fileHashes
│  │  │      fileHashes.bin
│  │  │      fileHashes.lock
│  │  │      resourceHashesCache.bin
│  │  │      
│  │  └─vcsMetadata
│  ├─buildOutputCleanup
│  │      buildOutputCleanup.lock
│  │      cache.properties
│  │      outputFiles.bin
│  │      
│  └─vcs-1
│          gc.properties
│          
├─build
│  │  resolvedMainClassName
│  │  
│  ├─classes
│  │  └─java
│  │      ├─main
│  │      │  └─com
│  │      │      └─modam
│  │      │          └─backend
│  │      │              │  ModamApplication.class
│  │      │              │  
│  │      │              ├─config
│  │      │              │      RedisConfig.class
│  │      │              │      WebSocketConfig.class
│  │      │              │      
│  │      │              ├─controller
│  │      │              │      BookClubController.class
│  │      │              │      ChatController.class
│  │      │              │      HomeController.class
│  │      │              │      MemoController.class
│  │      │              │      ReadingNoteController.class
│  │      │              │      UserController.class
│  │      │              │      
│  │      │              ├─dto
│  │      │              │      AITopicRequestDto.class
│  │      │              │      BookClubCommonDto.class
│  │      │              │      BookClubCompletedDetailDto.class
│  │      │              │      BookClubCreateDto.class
│  │      │              │      BookClubDetailDto.class
│  │      │              │      BookClubDto.class
│  │      │              │      BookClubSearchCondition.class
│  │      │              │      BookClubStatusDto.class
│  │      │              │      BookDto.class
│  │      │              │      ChatMessageDto.class
│  │      │              │      CompletedBookClubDto.class
│  │      │              │      MemoDto.class
│  │      │              │      PasswordUpdateDto.class
│  │      │              │      ReadingNoteDto.class
│  │      │              │      SummaryCreateDto.class
│  │      │              │      UserDto.class
│  │      │              │      UserNameUpdateDto.class
│  │      │              │      
│  │      │              ├─handler
│  │      │              │      FreeDiscussionManager.class
│  │      │              │      SubtopicDiscussionManager.class
│  │      │              │      
│  │      │              ├─model
│  │      │              │      Book.class
│  │      │              │      BookClub.class
│  │      │              │      ChatMessage$ChatMessageBuilder.class
│  │      │              │      ChatMessage.class
│  │      │              │      DiscussionTopic$DiscussionTopicBuilder.class
│  │      │              │      DiscussionTopic.class
│  │      │              │      Memo.class
│  │      │              │      MessageType.class
│  │      │              │      Participant.class
│  │      │              │      ReadingNote.class
│  │      │              │      Summary.class
│  │      │              │      User.class
│  │      │              │      VoteStatus.class
│  │      │              │      
│  │      │              ├─repository
│  │      │              │      BookClubRepository.class
│  │      │              │      BookRepository.class
│  │      │              │      ChatMessageRepository.class
│  │      │              │      DiscussionTopicRepository.class
│  │      │              │      MemoRepository.class
│  │      │              │      ParticipantRepository.class
│  │      │              │      ReadingNoteRepository.class
│  │      │              │      SummaryRepository.class
│  │      │              │      UserRepository.class
│  │      │              │      
│  │      │              ├─security
│  │      │              │      JwtAuthenticationFilter.class
│  │      │              │      JwtHandshakeInterceptor.class
│  │      │              │      SecurityConfig.class
│  │      │              │      SwaggerConfig.class
│  │      │              │      
│  │      │              ├─service
│  │      │              │      BookClubService.class
│  │      │              │      BookService.class
│  │      │              │      ChatService.class
│  │      │              │      MemoService.class
│  │      │              │      ReadingNoteService.class
│  │      │              │      UserService.class
│  │      │              │      
│  │      │              └─util
│  │      │                      JwtUtil.class
│  │      │                      
│  │      └─test
│  │          └─com
│  │              └─modam
│  │                  └─backend
│  │                          ModamApplicationTests.class
│  │                          
│  ├─generated
│  │  └─sources
│  │      ├─annotationProcessor
│  │      │  └─java
│  │      │      ├─main
│  │      │      └─test
│  │      └─headers
│  │          └─java
│  │              ├─main
│  │              └─test
│  ├─libs
│  │      Modam-0.0.1-SNAPSHOT-plain.jar
│  │      Modam-0.0.1-SNAPSHOT.jar
│  │      
│  ├─reports
│  │  └─problems
│  │          problems-report.html
│  │          
│  ├─resources
│  │  └─main
│  │      │  application.properties
│  │      │  application_2nd.properties
│  │      │  
│  │      ├─static
│  │      └─templates
│  └─tmp
│      ├─bootJar
│      │      MANIFEST.MF
│      │      
│      ├─compileJava
│      │      previous-compilation-data.bin
│      │      
│      ├─compileTestJava
│      │      previous-compilation-data.bin
│      │      
│      └─jar
│              MANIFEST.MF
│              
├─gradle
│  └─wrapper
│          gradle-wrapper.jar
│          gradle-wrapper.properties
│          
└─src
├─main
│  ├─java
│  │  └─com
│  │      └─modam
│  │          └─backend
│  │              │  ModamApplication.java
│  │              │  
│  │              ├─config
│  │              │      RedisConfig.java
│  │              │      WebSocketConfig.java
│  │              │      
│  │              ├─controller
│  │              │      BookClubController.java
│  │              │      ChatController.java
│  │              │      HomeController.java
│  │              │      MemoController.java
│  │              │      ReadingNoteController.java
│  │              │      UserController.java
│  │              │      
│  │              ├─dto
│  │              │      AITopicRequestDto.java
│  │              │      BookClubCommonDto.java
│  │              │      BookClubCompletedDetailDto.java
│  │              │      BookClubCreateDto.java
│  │              │      BookClubDetailDto.java
│  │              │      BookClubDto.java
│  │              │      BookClubSearchCondition.java
│  │              │      BookClubStatusDto.java
│  │              │      BookDto.java
│  │              │      ChatMessageDto.java
│  │              │      CompletedBookClubDto.java
│  │              │      MemoDto.java
│  │              │      PasswordUpdateDto.java
│  │              │      ReadingNoteDto.java
│  │              │      SummaryCreateDto.java
│  │              │      UserDto.java
│  │              │      UserNameUpdateDto.java
│  │              │      
│  │              ├─handler
│  │              │      FreeDiscussionManager.java
│  │              │      SubtopicDiscussionManager.java
│  │              │      
│  │              ├─model
│  │              │      Book.java
│  │              │      BookClub.java
│  │              │      ChatMessage.java
│  │              │      DiscussionTopic.java
│  │              │      Memo.java
│  │              │      MessageType.java
│  │              │      Participant.java
│  │              │      ReadingNote.java
│  │              │      Summary.java
│  │              │      User.java
│  │              │      VoteStatus.java
│  │              │      
│  │              ├─repository
│  │              │      BookClubRepository.java
│  │              │      BookRepository.java
│  │              │      ChatMessageRepository.java
│  │              │      DiscussionTopicRepository.java
│  │              │      MemoRepository.java
│  │              │      ParticipantRepository.java
│  │              │      ReadingNoteRepository.java
│  │              │      SummaryRepository.java
│  │              │      UserRepository.java
│  │              │      
│  │              ├─security
│  │              │      JwtAuthenticationFilter.java
│  │              │      JwtHandshakeInterceptor.java
│  │              │      SecurityConfig.java
│  │              │      SwaggerConfig.java
│  │              │      
│  │              ├─service
│  │              │      BookClubService.java
│  │              │      BookService.java
│  │              │      ChatService.java
│  │              │      MemoService.java
│  │              │      ReadingNoteService.java
│  │              │      UserService.java
│  │              │      
│  │              └─util
│  │                      JwtUtil.java
│  │                      
│  └─resources
│      │  application.properties
│      │  
│      ├─static
│      └─templates
└─test
└─java
└─com
└─modam
└─backend
ModamApplicationTests.java
                            
