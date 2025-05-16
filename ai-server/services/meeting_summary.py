# ai-server/services/meeting_summary.py
# 독서 모임 요약
# api 수정 필요

import os
from flask import jsonify, request
from langchain_community.chat_models import ChatOpenAI

#soo: Flask는 request 객체를 인자로 넘기지 않음, request 직접 접근하도록 수정
def summarize_meeting_api():
    # 요청 JSON 데이터 가져오기
    data = request.get_json()
    topics = data.get("topics")  # 발제문 목록 (리스트 형태)
    all_responses = data.get("all_responses")  # 발제문별 참가자 응답들 (리스트의 리스트)

    # 입력 데이터 유효성 검사
    if not isinstance(topics, list) or not isinstance(all_responses, list):
        return jsonify({"error": "topics와 all_responses는 리스트여야 합니다."}), 400
    if not topics or not all_responses:
        return jsonify({"error": "topics 또는 all_responses가 비어 있습니다."}), 400
    if len(topics) != len(all_responses):
        return jsonify({"error": "topics와 all_responses의 길이가 일치하지 않습니다."}), 400

    try:
        # GPT 모델 초기화 (OpenAI GPT-4 사용)
        llm = ChatOpenAI(model_name="gpt-4", api_key=os.getenv("OPENAI_API_KEY"))

        # 각 발제문에 대한 요약 결과를 저장할 리스트
        summaries = []

        # 각 발제문과 그에 대한 응답을 순회하면서 요약 수행
        for i, (topic, responses) in enumerate(zip(topics, all_responses)):
            # GPT에 전달할 프롬프트 구성
            prompt = (
                "당신은 독서 모임의 기록자입니다. 아래 발제문과 참가자 응답을 바탕으로 "
                "주요 논의 내용과 다양한 의견이 잘 드러나도록 간결하게 요약해 주세요.\n\n"
                f"[발제문 {i+1}] {topic}\n"  # 발제문 번호와 내용
                f"[응답]\n" + "\n".join(responses)  # 해당 발제문에 대한 참가자 응답 모음
            )

            try:
                # GPT-4 모델에 프롬프트 전달하여 요약 생성
                result = llm.invoke(prompt).content.strip() #soo: content. 추가

                # 정상 응답 시 요약 결과를 저장
                summaries.append({
                    "topic": topic,
                    "summary": result
                })
            except Exception as e:
                # 개별 요약 실패 시 오류 메시지 포함하여 저장
                summaries.append({
                    "topic": topic,
                    "summary": f"[요약 실패] {str(e)}"
                })

        # 모든 발제문 요약 결과를 JSON 형태로 반환
        return jsonify({"summaries": summaries})

    except Exception as e:
        # 시스템 레벨 에러 발생 시 전체 오류 반환
        return jsonify({"error": str(e)}), 500