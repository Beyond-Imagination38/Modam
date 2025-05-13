# ai-server/core/test_load_chroma.py
# load_chroma.py 테스트용 파일

from load_chroma import load_chroma

# 불러올 책 ID
book_id = "1"

# ChromaDB 로드
db = load_chroma(book_id)

# 간단한 테스트 쿼리 실행
retriever = db.as_retriever()
query = "조지 오웰의 1984는 전체주의 사회의 극단적인 미래를 그린 디스토피아 소설로, 읽는 내내 섬뜩한 현실감과 무력감을 안겨주었다.\
    빅브라더가 모든 것을 감시하고 통제하는 사회에서는 사적인 공간조차 존재하지 않으며, 인간의 사고마저 국가가 지배한다.\
        주인공 윈스턴은 체제에 저항하려 하지만 결국 자신의 사랑과 생각까지도 무너지고 만다.이 소설에서 가장 충격적인 부분은 '이중사고'와 신어'라는 개념이다.\
            국가가 언어와 사고를 통제함으로써 진실 자체를 재정의하는 모습은 현재 우리가 살아가는 시대에도 경고를 주는 것처럼 느껴진다.\
                가짜뉴스, 여론 조작, 감시 기술 등 현대 사회의 다양한 문제들이 소설 속 현실과 맞닿아 있기에 1949년에 쓰인 이 책이 여전히 유효하다는 점이 놀랍다."
docs = retriever.get_relevant_documents(query)

# 결과 출력
print(f"🔍 Query: {query}")
print("관련 문서:")
for i, doc in enumerate(docs):
    print(f"\n--- Document {i + 1} ---")
    print(doc.page_content)
