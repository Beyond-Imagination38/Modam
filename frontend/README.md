frontend
├── public/
│   ├── assets/                # 이미지
│   │   ├── chatbot.png        
│   │   └── logo.png          
│   └── index.html           
├── src/
│   ├── components/          
│   │   ├── bookreport/        # 독후감 관련 컴포넌트
│   │   ├── chat/              # 채팅 기능 컴포넌트
│   │   ├── common/            # 공용 UI 컴포넌(헤더)
│   │   ├── completed/         # 완료된 독서모임 컴포넌트
│   │   ├── detail/            # 상세 페이지 컴포넌트
│   │   ├── home/              # 홈 화면 컴포넌트
│   │   ├── login/             # 로그인 페이지 컴포넌트
│   │   ├── main/              # 메인 페이지 컴포넌트
│   │   ├── mypage/            # 마이페이지 컴포넌트
│   │   ├── register/          # 독서모임 등록 폼 컴포넌트
│   │   ├── signup/            # 회원가입 페이지 컴포넌트
│   │   └── index.js           
│   ├── consts/
│   │   └── index.js           # 상수(경로, 설정값 등) 정의
│   ├── utils/
│   │   ├── fetchApi.js        # API 호출 유틸 함수
│   │   └── index.js          
│   ├── App.jsx                # 전체 앱 구조 및 라우팅 설정
│   └── index.jsx             
├── package.json               
└── README.md 