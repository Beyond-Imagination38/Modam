import React, { useState } from "react";
import * as S from "./Chat.style";
import { Link } from "react-router-dom";

export function Chat() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");

  const handleSend = () => {
    if (message.trim()) {
      setMessages([...messages, { sender: "나", content: message }]);
      setMessage("");
    }
  };

  return (
    <S.Container>
      <S.Header>
        <S.Title>『군주론』 - 마키아벨리</S.Title>
        <S.RightSection>
          <S.NoteText>노트</S.NoteText>
          <Link to="/Main">
            <S.ExitButton>나가기</S.ExitButton>
          </Link>
        </S.RightSection>
      </S.Header>

      <S.ChatBox>
        {messages.map((msg, index) => (
          <S.Message key={index} isMine={msg.sender === "나"}>
            <strong>{msg.sender}:</strong> {msg.content}
          </S.Message>
        ))}
      </S.ChatBox>

      <S.InputContainer>
        <S.Input
          type="text"
          placeholder="여기에 내용을 입력해 주세요."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={(e) => e.key === "Enter" && handleSend()}
        />
        <S.SendButton onClick={handleSend}>보내기</S.SendButton>
      </S.InputContainer>
    </S.Container>
  );
}
