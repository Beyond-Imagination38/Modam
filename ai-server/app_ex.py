# ai-server/app_ex.py

from flask import Flask

app = Flask(__name__)

@app.route("/")
def home():
    return "AI 서버가 잘 작동 중입니다!"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
