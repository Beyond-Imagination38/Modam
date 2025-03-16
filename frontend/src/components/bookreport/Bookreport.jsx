import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "./Bookreport.style";

export function Bookreport() {
  const [input, setInput] = useState("");
  const [editedFeedback, setEditedFeedback] = useState("");
  const [remainingEdits, setRemainingEdits] = useState(5);
  const navigate = useNavigate();

  const handleSubmit = () => {
    navigate("/chat");
  };

  const handleEdit = async () => {
    if (!input.trim()) {
      alert("소감문을 입력해주세요.");
      return;
    }
    if (remainingEdits <= 0) {
      alert("첨삭 가능 횟수를 모두 사용하셨습니다.");
      return;
    }
    try {
      //const result = await submitFeedbackAPI(input);
      //setEditedFeedback(result.data.editedContent);
      setRemainingEdits((prev) => prev - 1);
    } catch (error) {
      alert("첨삭 요청에 실패했습니다. 다시 시도해주세요.");
    }
  };

  /*
  const handleSubmit = async () => {
    const userConfirmation = window.confirm("제출하시겠습니까?");
    if (!userConfirmation) return;

    if (editedFeedback.trim()) {
      try {
        await submitFeedbackAPI(editedFeedback);
        alert("소감문이 성공적으로 제출되었습니다.");
      } catch (error) {
        console.warn("소감문 제출에 실패했습니다:", error);
      } finally {
        navigate("/chat");
      }
    } else {
      navigate("/chat");
    }
  };
*/
  const handleReset = () => {
    setInput("");
    setEditedFeedback("");
    setRemainingEdits(5);
  };

  return (
    <S.Container>
      <S.Header>소감문 작성</S.Header>
      <S.TextareaContainer>
        <S.Textarea
          placeholder="소감문을 입력해주세요. 첨삭은 최대 5번 가능합니다!"
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
        <S.Textarea
          placeholder="이렇게 써보는 건 어떨까요?"
          value={editedFeedback}
          readOnly
        />
      </S.TextareaContainer>
      <S.RemainingCount>남은 첨삭 수: {remainingEdits}/5</S.RemainingCount>
      <S.Actions>
        <S.Button onClick={handleReset}>초기화</S.Button>
        <S.Button onClick={handleEdit} bg="#674ea7" color="white">
          동의 후 첨삭
        </S.Button>
        <S.Button onClick={handleSubmit} bg="#674ea7" color="white">
          제출
        </S.Button>
      </S.Actions>
    </S.Container>
  );
}
