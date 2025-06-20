from langchain.embeddings import HuggingFaceEmbeddings as BaseHuggingFaceEmbeddings

class HuggingFaceEmbeddings(BaseHuggingFaceEmbeddings):
    def __init__(self):
        super().__init__(model_name="sentence-transformers/all-MiniLM-L6-v2")
