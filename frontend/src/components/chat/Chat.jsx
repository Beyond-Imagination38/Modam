import React, { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import * as S from "./Chat.style";
import { Link, useParams } from "react-router-dom"; //soo: useParams추가

export function Chat() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState(() => Number(localStorage.getItem("userId") || 0)); //soo: localStorage에서 userId 가져오기
  const [username, setUserName] = useState(() => localStorage.getItem("userName") || "사용자"); //soo: localStorage에서 userName 가져오기
  const [stompClient, setStompClient] = useState(null);
  const [memoContent, setMemoContent] = useState("");
  const [isMemoVisible, setIsMemoVisible] = useState(false);

  const { clubId } = useParams(); //soo: clubId 가져오기

  const accessToken = localStorage.getItem("accessToken") || "";

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/chat"); //soo: 주소 수정
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, // 자동 재연결 (5초)
      onConnect: () => {
        console.log("WebSocket 연결 성공"); //soo:Debug
        window.stompClient = client; //soo: console에서 사용 가능
        client.subscribe(`/topic/chat/${clubId}`, (message) => {
          const receivedMessage = JSON.parse(message.body);
          setMessages((prevMessages) => [...prevMessages, receivedMessage]);
        });
      },
      onStompError: (error) => {
        console.error("STOMP 오류:", error);
      },
    });

    client.activate(); // 연결 시작
    setStompClient(client);

    return () => {
      client.deactivate(); // 컴포넌트 언마운트 시 연결 해제
    };
  }, []);

  const sendMessage = () => {
    if (!stompClient || !stompClient.connected) {
      console.error("STOMP 클라이언트가 연결되지 않았습니다.");
      return;
    }

    if (message.trim()) {
      const chatMessage = {
        messageType: "DISCUSSION", //soo: 기본 메시지 타입 설정
        clubId: parseInt(clubId), //soo: 문자열 clubId를 숫자로 변환
        userId, //soo: localStorage에서 가져온 userId 사용
        userName: username, //soo: localStorage에서 가져온 userName 사용
        content: message,
      };

      stompClient.publish({
        destination: `/app/chat/${clubId}`, //soo: 동적으로 clubId 포함
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(chatMessage),
      });

      setMessage("");
    }
  };

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
                <S.Message key={index} $isMine={msg.userName === username}> {/* soo: isMine → $isMine으로 변경하여 warning 해결 */}
                  <strong>{msg.userName}:</strong> {msg.content}
                </S.Message>
            ))}
          </S.ChatBox>

          <S.InputContainer>
            <S.Input
                type="text"
                placeholder="여기에 내용을 입력해 주세요."
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyPress={(e) => e.key === "Enter" && sendMessage()}
            />
            <S.SendButton onClick={sendMessage}>보내기</S.SendButton>
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
