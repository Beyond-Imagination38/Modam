# ai-server/core/test_chroma.py
# 책 pdf -> chromaDB 테스트

from chroma_book import save_pdf_to_chroma
import os

# 테스트용 book_id와 PDF 경로
book_id = "2"
pdf_path = os.path.join("ai-server", "book_pdf", "LesMiserables.pdf")

# 실행
save_pdf_to_chroma(book_id, pdf_path)