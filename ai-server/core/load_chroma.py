# ai-server/core/load_chroma.py
# chromaDB 로드

import os
from langchain_chroma import Chroma  # soo:0618 최신 방식
from langchain_huggingface import HuggingFaceEmbeddings

def load_chroma(book_id, chroma_root="chroma_store"):
    """
    저장된 ChromaDB를 불러오는 함수

    Args:
        book_id (str): 저장된 책 ID (= Chroma 폴더명)
        chroma_root (str): Chroma 저장 루트 폴더

    Returns:
        Chroma: 불러온 Chroma 객체
    """
    
    # 현재 파일 기준으로 절대경로 생성
    base_dir = os.path.dirname(os.path.abspath(__file__))
    persist_path = os.path.join(base_dir, chroma_root, str(book_id))
    print("Chroma persist path:", os.path.abspath(persist_path))

    if not os.path.exists(persist_path):
        raise FileNotFoundError(f"ChromaDB not found at {persist_path}")

    embedding = HuggingFaceEmbeddings(model_name="sentence-transformers/all-MiniLM-L6-v2") 

    db = Chroma(
        persist_directory=persist_path,
        embedding_function=embedding
    )
    return db