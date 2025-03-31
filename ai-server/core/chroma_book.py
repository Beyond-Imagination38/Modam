# ai-server/core/chroma_book.py
# 책 pdf를 벡터화하여 chromaDB에 저장
# pip install langchain chromadb openai pypdf
# pip install python-dotenv
# pip install -U langchain-community langchain-openai

import os
from dotenv import load_dotenv
from langchain_community.document_loaders import PyPDFLoader
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings


# .env 파일 로드(OpenAI key)
load_dotenv()
openai_api_key = os.getenv("OPENAI_API_KEY")


def save_pdf_to_chroma(book_id, pdf_path, chroma_root="chroma_store"):
    """
    PDF 문서를 벡터화하여 ChromaDB로 저장

    Args:
        book_id (str): 책 ID (폴더 이름으로 사용됨)
        pdf_path (str): PDF 파일 경로
        chroma_root (str): Chroma 저장 루트 폴더
    """
    
    # PDF 로드
    loader = PyPDFLoader(pdf_path)
    docs = loader.load()

    # 임베딩 모델 초기화 (OpenAI)
    embeddings = OpenAIEmbeddings(openai_api_key=openai_api_key)

    # 저장 경로 설정
    persist_path = os.path.join(chroma_root, book_id)
    os.makedirs(persist_path, exist_ok=True)

    # ChromaDB 저장
    db = Chroma.from_documents(documents=docs, embedding=embeddings, persist_directory=persist_path)
    db.persist()

    print(f"'{book_id}'의 PDF를 ChromaDB로 저장 완료 → {persist_path}")
