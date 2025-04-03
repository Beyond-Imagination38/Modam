# ai-server/services/generate_topics.py
# 발제문 생성
# 라이브러리, api 수정 필요

import os
from langchain.vectorstores import Chroma
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
from langchain.chains import RetrievalQA

class RAGBookEngine:
    def __init__(self, book_id: str, api_key: str, chroma_root: str = "./chroma_store"):
        self.book_id = book_id
        self.api_key = api_key
        self.chroma_path = os.path.join(chroma_root, book_id)

        if not os.path.exists(self.chroma_path):
            raise FileNotFoundError(f"No ChromaDB found for book_id: {book_id}")

        embedding = OpenAIEmbeddings(openai_api_key=api_key)
        self.db = Chroma(persist_directory=self.chroma_path, embedding_function=embedding)
        self.retriever = self.db.as_retriever()
        self.llm = ChatOpenAI(model_name="gpt-4", api_key=api_key)

    def generate_topics(self, user_responses):
        system_message = (
            "당신은 독서 모임의 사회자입니다. 아래 감상문과 책 내용을 바탕으로 "
            "편향되지 않고 정치적이지 않은 개방형 토론 주제 3가지를 한국어로 제시하세요. "
            "각 주제는 완결된 문장 형식이어야 합니다."
        )

        user_message = "\n".join(user_responses)
        context_docs = self.retriever.get_relevant_documents("발제문 생성에 필요한 주요 내용")
        context_text = "\n".join(doc.page_content for doc in context_docs)

        prompt = f"{system_message}\n\n[책 내용]\n{context_text}\n\n[참가자 감상문]\n{user_message}"

        response = self.llm.invoke(prompt)

        # 응답 후처리
        topics = [t.strip() for t in response.split("\n") if t.strip()]
        return topics if len(topics) >= 3 else []
