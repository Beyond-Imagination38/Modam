# ai-server/services/meeting_summary.py
# 독서 모임 요약
# api 수정 필요

from flask import jsonify
from langchain.chat_models import ChatOpenAI
import os

def summarize_meeting_api(request):
    data = request.get_json()
    topics = data.get("topics")
    all_responses = data.get("all_responses")

    if not topics or not all_responses or len(topics) != len(all_responses):
        return jsonify({"error": "Invalid input"}), 400

    try:
        llm = ChatOpenAI(model_name="gpt-4", api_key=os.getenv("OPENAI_API_KEY"))

        prompt = (
            "당신은 독서 모임의 기록자입니다. 참가자들이 각 주제에 대해 논의한 응답을 바탕으로, "
            "주제별로 간결하고 핵심적인 요약을 작성해주세요.\n\n"
        )
        for i, (topic, responses) in enumerate(zip(topics, all_responses)):
            prompt += f"[주제 {i + 1}] {topic}\n"
            prompt += "응답:\n" + "\n".join(responses) + "\n\n"

        summary = llm.invoke(prompt).strip()
        return jsonify({"summary": summary})

    except Exception as e:
        return jsonify({"error": str(e)}), 500
