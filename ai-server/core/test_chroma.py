# ai-server/core/test_chroma.py
# 책 pdf -> chromaDB 테스트
# pip install -U langchain-community
# pip install tiktoken
from chroma_book import save_pdf_to_chroma
import os

# 테스트용 book_id와 PDF 경로
book_id = "book1"
pdf_path = os.path.join("book_pdf", "1984.pdf")   # 상대경로

# 실행
save_pdf_to_chroma(book_id, pdf_path)