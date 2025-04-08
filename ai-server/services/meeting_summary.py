# ai-server/services/meeting_summary.py
# 독서 모임 요약
# api 수정 필요

import os
from flask import jsonify, request
from langchain_community.chat_models import ChatOpenAI

def summarize_meeting_api(request):
    data = request.get_json()
    topics = data.get("topics")
    all_responses = data.get("all_responses")

    # 유효성 검사 강화
    if not isinstance(topics, list) or not isinstance(all_responses, list):
        return jsonify({"error": "topics와 all_responses는 리스트여야 합니다."}), 400
    if not topics or not all_responses:
        return jsonify({"error": "topics 또는 all_responses가 비어 있습니다."}), 400
    if len(topics) != len(all_responses):
        return jsonify({"error": "topics와 all_responses의 길이가 일치하지 않습니다."}), 400

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
