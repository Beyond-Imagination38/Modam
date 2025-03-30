import React, { useState, useEffect } from "react";
//import Stomp from "stompjs";
//import SocketJS from "socketjs-client";
import * as S from "./Chat.style";
import { Link } from "react-router-dom";

export function Chat() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");
  const [username, setUserName] = useState("");
  const [stompClient, setStompClient] = useState(null);
  const [memoContent, setMemoContent] = useState("");
  const [isMemoVisible, setIsMemoVisible] = useState(false);
  /*
  useEffect(() => {
    const socket = new SocketJS("http://localhost:8080/ws");
    const client = Stomp.over(socket);

    client.connet({}, () => {
      client.subscribe("/topic/message", (message) => {
        const receiveMessage = JSON.parse(message.body);
        setMessages((prevMessages) => [...prevMessages, receiveMessage]);
      });
    });

    setStompClient(client);
    return () => {
      client.disconnect();
    };
  }, []);
*/
  const handleSend = () => {
    if (message.trim()) {
      setMessages([...messages, { sender: "나", content: message }]);
      setMessage("");
    }
  };

  /*
  const sendMessage = () => {
    if(message.trim()) {
      const chatMessage ={
        userName,
        content: message
      };
      stompClient.send('/topic/chat',         {
        Authorization: 'Bearer ' + accessToken,
        'Content-Type': 'application/json',
      },
      JSON.stringify(chatMessage));
      sendMessage('');
      }
    }
  }
*/

  const handleMemoChange = (e) => {
    setMemoContent(e.target.value);
  };

  const toggleMemo = () => {
    setIsMemoVisible(!isMemoVisible);
  };

  return (
    <S.Container>
      <S.ChatSection>
        <S.Header>
          <S.Title>『군주론』 - 마키아벨리</S.Title>
          <S.RightSection>
            <S.NoteText onClick={toggleMemo}>
              {isMemoVisible ? "메모 닫기" : "메모 열기"}
            </S.NoteText>
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
      </S.ChatSection>

      {isMemoVisible && (
        <S.MemoSection>
          <S.MemoTitle>메모</S.MemoTitle>
          <S.MemoInput
            value={memoContent}
            onChange={handleMemoChange}
            placeholder="여기에 메모를 입력하세요."
          />
        </S.MemoSection>
      )}
    </S.Container>
  );
}
