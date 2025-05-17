import React, { useState, useEffect } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import * as S from "./Chat.style";
import { Link, useParams } from "react-router-dom"; 
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

const formatSummary = (text) => {
  if (!text) return [];

  // '주제' 키워드가 포함된 위치를 기준으로 우선 문단 나누기
  const topicBlocks = text.split(/(?=주제\s*\d*:)/g); // '주제 1:', '주제2:' 등을 기준으로 나눔
  const formattedParagraphs = [];

  topicBlocks.forEach((block) => {
    const sentences = block.trim().split(/(?<=[.!?])\s+/);
    const paragraphSize = 2;

    for (let i = 0; i < sentences.length; i += paragraphSize) {
      const para = sentences.slice(i, i + paragraphSize).join(" ");
      formattedParagraphs.push(para);
    }
  });

  return formattedParagraphs;
};


export function Chat() {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState(""); 
  const [userId, setUserId] = useState(() => Number(localStorage.getItem("userId") || 0)); 
  const [username, setUserName] = useState(() => localStorage.getItem("userName") || "사용자"); 
  const [stompClient, setStompClient] = useState(null);
  const [memoContent, setMemoContent] = useState("");
  const [isMemoVisible, setIsMemoVisible] = useState(false);

  const { clubId } = useParams();

  const accessToken = localStorage.getItem("accessToken") || "";

  useEffect(() => {
    // 임시 요약 메시지 테스트
    const testMessage = {
      messageType: "SUMMARY",
      clubId: 123,
      userId: 0,
      userName: "AI 진행자",
      content: "오늘 1984 독서 모임은 어떠셨나요? 오늘 토의 내용을 요약해드릴게요. 주제 1: 감시 사회와 개인의 자유. 참가자들은 소설 속 '빅 브라더' 개념이 현대 사회의 CCTV, 스마트폰, 개인정보 추적 기술과 유사하다고 언급했습니다. 특히 프라이버시와 효율성 사이의 균형 문제에 대해 활발한 토론이 있었습니다. 주제 2: 진실 조작과 언론의 역할. 뉴스피크와 진리부에 대한 해석이 다양하게 나왔으며, 현실 사회의 언론 신뢰도 문제와 연결하는 의견이 많았습니다. 마지막으로, 이러한 요소들이 현재 우리 사회에 던지는 경고를 어떻게 받아들일지에 대한 성찰로 토론이 마무리되었습니다."
    };

    setMessages((prev) => [...prev, testMessage]);
  }, []);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/chat"); 
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000, 
      onConnect: () => {
        console.log("WebSocket 연결 성공"); 
        window.stompClient = client; 
        client.subscribe(`/topic/chat/${clubId}`, (message) => {
          const receivedMessage = JSON.parse(message.body);

          console.log("📥 [DEBUG] 받은 메시지:", receivedMessage);//debug soo:demo02


          if (receivedMessage.messageType === "SUMMARY") {
            const introMessage = {
              userId: 0,
              userName: "AI 진행자",
              content: "오늘 독서 모임 어떠셨나요?\n토의 내용을 요약해드릴게요",
            };

            setMessages((prevMessages) => [
              ...prevMessages,
              introMessage,
              receivedMessage,
            ]);
          } else {
            setMessages((prevMessages) => [...prevMessages, receivedMessage]);
          }
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

      //soo:demo02
      const parsedClubId = parseInt(clubId);
      console.log("📤 보낼 clubId:", parsedClubId);

      const chatMessage = {
        messageType: "DISCUSSION", 
        clubId: parseInt(clubId), 
        userId, 
        userName: username, 
        content: message,
      };

      stompClient.publish({
        destination: `/app/chat/${clubId}`, 
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

const saveMemo = async () => {
  try {
    const response = await fetchApi(API_URLS.saveMemo(clubId), {
      method: "POST",
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        content: memoContent,
      }),
    });

    if (!response.ok) throw new Error("서버 응답 실패");

    alert("메모가 저장되었습니다!");
  } catch (error) {
    console.error("메모 저장 실패:", error);
    alert("메모 저장 중 오류가 발생했습니다.");
  }
};

  return (
      <S.Container>
        <S.ChatSection>
          <S.Header>
            <S.Title>『1984』 - 조지 오웰</S.Title>
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
          {messages.map((msg, index) => {
            const isMine = msg.userName === username;
            const isAI = msg.userId === 0;

            const userName = isAI ? "AI 진행자" : msg.userName;
            const avatar = "/assets/chatbot.png";
            const messageStyle = isAI ? "bot-message" : isMine ? "my-message" : "user-message";

            return (
              <S.Message key={index} className={messageStyle}>
                {isAI && <S.Avatar src={avatar} alt="AI avatar" />}
                  <div>
                    <strong>{userName}</strong>
                    {msg.userId === 0 && msg.messageType === "SUMMARY" ? (
                      formatSummary(msg.content).map((para, i) => <p key={i}>{para}</p>)
                    ) : (
                      <div>{msg.content}</div>
                    )}
                </div>
              </S.Message>
            );
          })}

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
              <S.SaveMemoButton onClick={saveMemo}>저장</S.SaveMemoButton>
            </S.MemoSection>
        )}
      </S.Container>
  );
}