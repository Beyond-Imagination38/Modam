import Header from "../common/Header";
import * as S from "./Detail.style";
import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Detail() {
  const { clubId } = useParams();
  const [data, setData] = useState(null); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user?.id;

  const handleJoinClub = async () => {
    const confirmed = window.confirm("모임을 신청하겠습니까?");
    if (!confirmed) return;

    try {
      const response = await fetch(`http://localhost:8080/api/bookclubs/${clubId}/join?userId=${userId}`, {
        method: "POST",
      });

      const responseText = await response.text();

      if (!response.ok) {
        if (responseText.includes("이미 신청한 사용자입니다")) {
          alert("이미 신청하신 모임입니다.");
        } else {
          alert("모임 신청에 실패했습니다.");
        }
        return;
      }

      setData((prevData) => ({
        ...prevData,
        currentParticipants: prevData.currentParticipants + 1,
      }));
      alert(responseText);

      setData((prevData) => ({
        ...prevData,
        currentParticipants: prevData.currentParticipants + 1,
      }));
    } catch (error) {
      console.error("모임 신청 중 오류 발생:", error);
      alert("모임 신청에 실패했습니다.");
    }
  };

  useEffect(() => {
    const getData = async () => {

      try {
        const response = await fetch(`http://localhost:8080/api/bookclubs/${clubId}/detail?userId=${userId}`, {
          method: "GET",
        });
        
        if (!response.ok) throw new Error("서버 응답 실패");

        const result = await response.json();
        setData(result);
      } catch (error) {
        console.error("상세 정보 로드 실패:", error);
        setError("상세 정보를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    
    getData();
  }, [clubId, userId]);

  if (loading) return <div>로딩 중...</div>;
  if (!data) return <div>데이터가 없습니다.</div>;

  return (
    <>
      <Header />
      <S.Container>
        <S.BookCover src={data.imageUrl} alt={data.bookTitle} />
        <S.Content>
          <div>
            <S.Title>{data.bookTitle}</S.Title>
            <S.Date>{data.meetingDateTime}</S.Date>
            <S.Description>{data.clubDescription}</S.Description>
            <S.Participants>
              참여자: ({data.currentParticipants}/{data.maxParticipants})
            </S.Participants>
          </div>
          <S.ButtonContainer>
            {data.status === "OPEN" && (
              <>
                {console.log("현재 로그인한 사용자 ID:", Number(userId))}
                {console.log("이 모임을 만든 사용자 ID:", Number(data.userId))}
                {console.log("내가 만든 모임인가?", Number(data.userId) === Number(userId))}

                {data.userId === userId ? (
                  // 내가 만든 모임일 경우
                  <Link to={`/Chat/${data.clubId}`}>
                    <S.Button $primary>모임 시작</S.Button>
                  </Link>
                ) : (
                  // 내가 만든 모임이 아닐 경우
                  <S.Button as="button" $primary onClick={handleJoinClub}>
                    모임 신청
                  </S.Button>
                )}
                <Link to={`/Bookreport/${data.clubId}`}>
                  <S.Button>독후감 작성</S.Button>
                </Link>
              </>
            )}

            {data.status === "CLOSED" && (
              <S.Button as="button" disabled>모집 마감</S.Button>
            )}

            {data.status === "ONGOING" && (
              <>
                <Link to={`/Chat/${data.clubId}`}>
                  <S.Button $primary>모임 시작</S.Button>
                </Link>
                <Link to={`/Bookreport/${data.clubId}`}>
                  <S.Button>독후감 작성</S.Button>
                </Link>
              </>
            )}

            {data.status === "COMPLETED" && (
              <S.Button as="button">모임 후기 보기</S.Button>
            )}
        </S.ButtonContainer>
        </S.Content>
      </S.Container>
    </>
  );
}
