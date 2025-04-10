# ai-server/services/generate_topics.py
# 발제문 생성
# 라이브러리, api 수정 필요

import os
import numpy as np
from dotenv import load_dotenv
from flask import request, jsonify
from langchain.vectorstores import Chroma
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
#from langchain.chains import RetrievalQA
from langchain.embeddings.base import Embeddings
from sentence_transformers import SentenceTransformer

# .env 파일 로드
load_dotenv()
API_KEY = os.getenv("OPENAI_API_KEY")

# 직접 구현한 Hugging Face 임베딩 wrapper
class HuggingFaceEmbeddings(Embeddings):
    def __init__(self, model_name: str = "sentence-transformers/all-MiniLM-L6-v2"):
        self.model = SentenceTransformer(model_name)

    def embed_documents(self, texts):
        return [self.model.encode(text).tolist() for text in texts]

    def embed_query(self, text):
        return self.model.encode(text).tolist()
    
class RAGBookEngine:
    def __init__(self, book_id: str, api_key: str, chroma_root: str = "./chroma_store"):
        self.book_id = book_id
        self.api_key = API_KEY
        self.chroma_path = os.path.join(chroma_root, book_id)

        if not os.path.exists(self.chroma_path):
            raise FileNotFoundError(f"No ChromaDB found for book_id: {book_id}")
        
        # 벡터 DB, LLM 초기화
        embedding = HuggingFaceEmbeddings()
        self.db = Chroma(persist_directory=self.chroma_path, embedding_function=embedding)
        self.retriever = self.db.as_retriever()
        self.llm = ChatOpenAI(model_name="gpt-4", api_key=self.api_key)

    def generate_topics(self, user_responses):
        # 프롬프트 구성
        system_message = (
            "당신은 독서 모임의 사회자입니다. 아래 감상문과 책 내용을 바탕으로 "
            "편향되지 않고 정치적이지 않은 개방형 토론 주제 3가지를 한국어로 제시하세요. "
            "각 주제는 완결된 문장 형식이어야 합니다."
        )

        user_message = "\n".join(user_responses)
        
        # 관련 문서 검색
        context_docs = self.retriever.get_relevant_documents("발제문 생성에 필요한 주요 내용")
        context_text = "\n".join(doc.page_content for doc in context_docs)

        prompt = f"{system_message}\n\n[책 내용]\n{context_text}\n\n[참가자 감상문]\n{user_message}"

        # GPT 호출
        response = self.llm.invoke(prompt).content  # content만 추출

        print("GPT 응답 원문:")
        print(response)
        
        # 응답 후처리
        topics = [t.strip("0123456789. /\"").strip() for t in response.split("\n") if t.strip()]
        if len(topics) < 3:
            print("GPT 응답이 예상보다 짧습니다. 응답 내용:")
            print(response)
            return []

        return topics
    
# API 함수 정의
def generate_topics_api():
    data = request.json
    book_id = data.get("book_id")
    user_responses = data.get("user_responses")

    if not all([book_id, user_responses]):
        return jsonify({"error": "Missing required fields"}), 400

    try:
        rag_engine = RAGBookEngine(book_id=book_id, api_key=API_KEY, chroma_root="core/chroma_store")
        topics = rag_engine.generate_topics(user_responses)
        return jsonify({"topics": topics})
    except FileNotFoundError as e:
        return jsonify({"error": str(e)}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500