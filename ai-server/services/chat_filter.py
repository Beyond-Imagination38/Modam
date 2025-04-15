# services/chat.filter.py
# 채팅 필터링

from flask import request, jsonify
from transformers import AutoTokenizer, AutoModelForSequenceClassification
import torch

# 키워드 기반 욕설 리스트
BADWORDS = [
    "씨발", "ㅅㅂ", "ㅂㅅ", "개새끼", "좆", "병신", "ㅗ", "ㅉ", "닥쳐", "꺼져",
    "fuck", "shit", "fxxk", "fuckin", "fuckyou", "asshole", "damn", "ㅆㅂ"
]

def contains_badword(text):
    return any(word in text for word in BADWORDS)

# 모델 로드 (최초 1회, HuggingFace에서 로드)
model_path = "mjBANG/Modam_hate_filter"
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

tokenizer = AutoTokenizer.from_pretrained(model_path)
model = AutoModelForSequenceClassification.from_pretrained(model_path).to(device)
model.eval()

# 모델 예측
def predict_model(text, threshold=0.5):
    inputs = tokenizer(text, return_tensors="pt", padding=True, truncation=True, max_length=128)
    inputs = {k: v.to(device) for k, v in inputs.items()}

    with torch.no_grad():
        outputs = model(**inputs)
        probs = torch.nn.functional.softmax(outputs.logits, dim=-1)

    score = probs[0][1].item()
    return score > threshold, round(score, 3)

# 최종 필터링
def final_filter(text):
    keyword_flag = contains_badword(text)
    model_flag, score = predict_model(text)
    return keyword_flag or model_flag, {
        "keyword_detected": keyword_flag,
        "model_detected": model_flag,
        "model_score": score
    }

# Flask API 핸들러
def filter_chat_api():
    data = request.get_json()
    text = data.get("text")

    if not text:
        return jsonify({"error": "텍스트가 비어 있습니다."}), 400

    is_blocked, detail = final_filter(text)

    return jsonify({
        "is_blocked": is_blocked,
        "detail": detail
    })

if __name__ == "__main__":
    from flask import Flask

    app = Flask(__name__)
    app.add_url_rule("/chat-filter", view_func=filter_chat_api, methods=["POST"])

    app.run(host="0.0.0.0", port=5000, debug=True)
