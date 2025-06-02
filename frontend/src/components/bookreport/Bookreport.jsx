import React, { useState } from "react";
import { Link } from "react-router-dom";
import * as S from "./Bookreport.style";
import Header from "../common/Header";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Bookreport() {
  const [input, setInput] = useState("");
  const { postId } = 1;

  const handleSubmit = async () => {
    alert("소감문이 성공적으로 제출되었습니다.");
    /*try {
      const response = await fetchApi(API_URLS.bookreport, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          content: input,
          postId: postId,
        }),
      });

      if (!response.ok) {
        throw new Error("서버 응답 실패");
      }

      const result = await response.json();
      alert("소감문이 성공적으로 제출되었습니다.");
      console.log("서버 응답:", result);
    } catch (error) {
      console.error("제출 중 오류 발생:", error);
      alert("제출에 실패했습니다. 다시 시도해주세요.");
    }*/
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
          <S.Button onClick={handleSubmit} $bg="#674ea7" color="white">
            제출
          </S.Button>
        </Link>
      </S.Actions>
    </S.Container>
  );
}