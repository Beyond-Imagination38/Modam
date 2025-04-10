# ai-server/core/chroma_book.py
# 책 pdf를 벡터화하여 chromaDB에 저장
# pip install langchain chromadb openai pypdf
# pip install python-dotenv
# pip install langchain chromadb pypdf sentence-transformers
# pip install torch
# pip install -U langchain-huggingface

import os
from langchain_community.document_loaders import PyPDFLoader
from langchain_community.vectorstores import Chroma
from langchain_huggingface import HuggingFaceEmbeddings

# 현재 파일 기준으로 chroma_store 위치 고정
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
CHROMA_ROOT = os.path.join(BASE_DIR, "chroma_store")

def save_pdf_to_chroma(book_id, pdf_path, chroma_root=CHROMA_ROOT):
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

    # 임베딩 모델 초기화 (HuggingFace), 버전 충돌로 인해 수정 필요
    embeddings = HuggingFaceEmbeddings(model_name="sentence-transformers/all-MiniLM-L6-v2")

    # 저장 경로 설정
    persist_path = os.path.join(chroma_root, book_id)
    os.makedirs(persist_path, exist_ok=True)

    # ChromaDB 저장
    db = Chroma.from_documents(documents=docs, embedding=embeddings, persist_directory=persist_path)
    db.persist()

    print(f"'{book_id}'의 PDF를 ChromaDB로 저장 완료 → {persist_path}")
