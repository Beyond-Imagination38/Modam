import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import * as S from "./Bookreport.style";
import Header from "../common/Header";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Bookreport() {
  const { clubId } = useParams();
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user?.id;
  const [input, setInput] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async () => {
    const payload = {
      userId: parseInt(userId),
      clubId: parseInt(clubId),
      content: input,
    };

    console.log("서버로 전송할 데이터:", payload);

    try {
      const response = await fetch("http://localhost:8080/reading-notes", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          userId,
          clubId,
          content: input,
        }),
      });
      
      if (!response.ok) {
        throw new Error("서버 응답 실패");
      }

      const resultText = await response.text();
      alert(resultText);
      console.log("서버 응답:", resultText);

      navigate(`/detail/${clubId}`);
    } catch (error) {
      console.error("제출 중 오류 발생:", error);
      alert("제출에 실패했습니다. 다시 시도해주세요.");
    }
  };

  const handleReset = () => {
    setInput("");
  };

  return (
    <S.Container>
      <Header />
      <S.TextareaContainer>
        <S.Textarea
          placeholder="소감문을 입력해주세요."
          value={input}
          onChange={(e) => setInput(e.target.value)}
        />
      </S.TextareaContainer>
      <S.Actions>
        <S.Button onClick={handleReset}>초기화</S.Button>
          <S.Button onClick={handleSubmit} $bg="#674ea7" color="white">
            제출
          </S.Button>
      </S.Actions>
    </S.Container>
  );
}