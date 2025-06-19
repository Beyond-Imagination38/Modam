```
📦 ai-server
├── 📁 book_pdf # 책 pdf 폴더
│
├── 📁 core # 책 pdf를 ChromaDB에 저장, 로드하는 기능
│   ├── 📁 chroma_store # 벡터화된 책 DB
│   ├── 📄 chroma_book.py   # pdf를 chromaDB에 벡터화->저장
│   ├── 📄 load_chroma.py   # chromaDB에 있는 책을 로드
│   ├── 📄 test_chroma.py   # chroma_book.py 테스트
│   └── 📄 test_load_chroma.py     # load_chroma.py 테스트
│
├── 📁 services # 주요 기능 폴더
│   ├── 📄 __init__.py 
│   ├── 📄 chat_filter.py   # 채팅 필터링
│   ├── 📄 generate_topics.py   # 발제문 생성
│   └── 📄 meeting_summary.csv     # 모임 요약
│
├── 📄 .dockerignore
├── 📄 ai.md
├── 📄 app.py
├── 📄 Dockerfile
└── 📄 requirements.txt
```
