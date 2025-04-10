# ai-server/core/test_load_chroma.py
# load_chroma.py 테스트용 파일

from load_chroma import load_chroma

# 불러올 책 ID
book_id = "book1"

# ChromaDB 로드
db = load_chroma(book_id)

# 간단한 테스트 쿼리 실행
retriever = db.as_retriever()
query = "1984 소설의 주인공은 누구인가요"
docs = retriever.get_relevant_documents(query)

# 결과 출력
print(f"🔍 Query: {query}")
print("📄 관련 문서:")
for i, doc in enumerate(docs):
    print(f"\n--- Document {i + 1} ---")
    print(doc.page_content)
