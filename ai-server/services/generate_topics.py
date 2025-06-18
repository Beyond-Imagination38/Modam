# ai-server/services/generate_topics.py
# 발제문 생성

import os
import re
import numpy as np
from dotenv import load_dotenv
from flask import request, jsonify

from langchain_chroma import Chroma  # soo:0618
from langchain_openai import ChatOpenAI  # soo:0618

#from langchain.vectorstores import Chroma # soo:0618
#from langchain.embeddings.openai import OpenAIEmbeddings # soo:0618
#from langchain.chains import RetrievalQA
from langchain.embeddings.base import Embeddings
from sentence_transformers import SentenceTransformer
from core.load_chroma import load_chroma

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
    def __init__(self, book_id: int, api_key: str):
        self.book_id = str(book_id)
        self.api_key = api_key
        
        # 벡터 DB, LLM 초기화
        embedding = HuggingFaceEmbeddings()
        self.db = load_chroma(self.book_id)
        self.retriever = self.db.as_retriever()
        self.llm = ChatOpenAI(model="gpt-4", api_key=self.api_key)  # soo:0618 model_name → model로 변경

    def generate_topics(self, user_responses):
        # 프롬프트 구성
        system_message = (
        "당신은 독서 모임의 사회자입니다. 아래 책 내용과 참가자들의 감상문을 참고하여 "
        "참가자들이 쉽게 이해하고 활발하게 토론할 수 있는 **개방형 토론 주제** 3가지를 작성해주세요.\n\n"
        "- 각 주제는 **책의 인물, 사건, 주제, 배경 등 구체적인 요소**를 포함해야 합니다.\n"
        "- **일상적인 언어로 작성**하고, **질문의 의도가 명확하게 드러나야** 합니다.\n"
        "- 주제는 편향적이거나 정치적인 내용이 아니어야 하며, 너무 추상적이거나 모호하지 않아야 합니다.\n"
        "- 사회적 약자(어린이, 노인, 여성, 장애인, 성소수자 등)를 혐오하는 표현이 들어가서는 안됩니다.\n"
        "- 각 주제는 하나의 완결된 문장으로 써 주세요.\n"
        "- 단순한 정보 확인 질문보다는 **느낌, 생각, 경험, 비교** 등을 유도하는 사고형 질문이 좋습니다.\n\n"
        "예시:\n"
        "✔ '주인공이 겪은 갈등 중 어떤 장면이 가장 인상 깊었고, 그 이유는 무엇일까?'\n"
        "✔ '이 책에서 등장인물 간의 관계는 현실에서도 공감할 수 있는가?'\n"
        "✔ '이야기의 배경이 주제를 어떻게 강화하거나 반영하고 있는가?'\n"
        "✘ '주인공 이름은 무엇인가요?'\n"
        "✘ '이 책은 무슨 내용인가요?'\n"
        )

        user_message = "\n".join(user_responses)
        user_query = " ".join(user_responses)  # 쿼리로 감상문 전체 사용
        
        # 감상문 기반으로 책 내용 검색
        context_docs = self.retriever.invoke(user_query) # soo:0618
        context_text = "\n".join(doc.page_content for doc in context_docs)

        prompt = (
        f"{system_message}\n"
        f"[책 내용]\n{context_text}\n\n"
        f"[참가자 감상문]\n{user_message}"
        )
        
        # GPT 호출
        response = self.llm.invoke(prompt).content  # content만 추출

        print("GPT 응답 원문:")
        print(response)
        
        # 응답 후처리
        topics = []
        for line in response.split("\n"):
            if line.strip():
                # "1. ", "2) ", "3." 등 리스트 번호만 제거
                clean = re.sub(r"^\s*\d+[\.\)]\s*", "", line).strip()
                topics.append(clean)

        # 결과 개수 확인
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
        rag_engine = RAGBookEngine(book_id=book_id, api_key=API_KEY)
        topics = rag_engine.generate_topics(user_responses)
        return jsonify({"topics": topics})
    except FileNotFoundError as e:
        return jsonify({"error": str(e)}), 404
    except Exception as e:
        return jsonify({"error": str(e)}), 500