import React, { useState } from "react";
import { Link } from "react-router-dom";
import * as S from "./Bookreport.style";
import Header from "../common/Header";

export function Bookreport() {
  const [input, setInput] = useState("");
  const { postId } = 1;

  const handleSubmit = () => {
    alert("소감문이 성공적으로 제출되었습니다.");
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
        <Link
          to={`/post/${postId}`}
          key={postId}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          <S.Button onClick={handleSubmit} bg="#674ea7" color="white">
            제출
          </S.Button>
        </Link>
      </S.Actions>
    </S.Container>
  );
}