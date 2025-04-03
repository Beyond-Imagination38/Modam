# ai-server/app.py
# 수정필요
from flask import Flask, request
from services.chat_filter import filter_chat_api
from services.generate_topics import generate_topics_api
from services.meeting_summary import summarize_meeting_api

app = Flask(__name__)


@app.route('/filter-chat', methods=['POST'])    # 채팅 필터링 api
def filter_chat():
    return filter_chat_api(request)

@app.route('/generate-topics', methods=['POST'])   # 발제문 생성 api
def generate_topics():
    return generate_topics_api(request)

@app.route('/summarize', methods=['POST'])  # 모임 내용 요약 api
def summarize():
    return summarize_meeting_api(request)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)