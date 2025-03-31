# ai-server/core/load_chroma.py
# chromaDB 로드

import os
from dotenv import load_dotenv
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings

# .env 로드
load_dotenv()
openai_api_key = os.getenv("OPENAI_API_KEY")


def load_chroma(book_id, chroma_root="core/chroma_store"):
    """
    저장된 ChromaDB를 불러오는 함수

    Args:
        book_id (str): 저장된 책 ID (= Chroma 폴더명)
        chroma_root (str): Chroma 저장 루트 폴더

    Returns:
        Chroma: 불러온 Chroma 객체
    """
    persist_path = os.path.join(chroma_root, book_id)

    if not os.path.exists(persist_path):
        raise FileNotFoundError(f"ChromaDB not found at {persist_path}")

    embedding = OpenAIEmbeddings(openai_api_key=openai_api_key)

    db = Chroma(
        persist_directory=persist_path,
        embedding_function=embedding
    )
    return db