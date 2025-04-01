# ai-server/app.py

from flask import Flask, request
from services.chat_filter import filter_chat_api

app = Flask(__name__)

@app.route('/filter-chat', methods=['POST'])
def filter_chat():
    return filter_chat_api(request)

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)