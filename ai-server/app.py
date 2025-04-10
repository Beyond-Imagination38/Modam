# ai-server/app.py

from flask import Flask, request
from services.chat_filter import filter_chat_api
from services.generate_topics import generate_topics_api
from services.meeting_summary import summarize_meeting_api
import sys

app = Flask(__name__)


@app.route('/ai/filter-chat', methods=['POST'])    # 채팅 필터링 api
def filter_chat():
    return filter_chat_api()

@app.route('/ai/generate-topics', methods=['POST'])   # 발제문 생성 api
def generate_topics():
    return generate_topics_api()

@app.route('/ai/summarize', methods=['POST'])  # 모임 내용 요약 api
def summarize_meeting():
    return summarize_meeting_api()


if __name__ == '__main__' and 'gunicorn' not in sys.argv[0]:
    print("🔧 Flask 앱 실행 중...")
    app.run(host="0.0.0.0", port=5000)