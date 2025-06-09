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

  useEffect(() => {
    const getData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/bookclubs/${clubId}/status`, {
          method: "GET",
        });
        console.log("clubId 파라미터:", clubId);
        if (!response.ok) throw new Error("서버 응답 실패");

        const result = await response.json();
        setData(result);
      } catch (error) {
        console.error("상세 정보 로드 실패:", error);
      } finally {
        setLoading(false);
      }
    };

    getData();
  }, [clubId]);

  if (loading) return <div>로딩 중...</div>;
  if (!data) return <div>데이터가 없습니다.</div>;


  return (
    <>
      <Header />
      <S.Container>
        <S.BookCover src={data.coverImage || "/default.jpg"} alt={data.bookTitle} />
        <S.Content>
          <div>
            <S.Title>{data.bookTitle}</S.Title>
            <S.Date>{data.meetingDateTime}</S.Date>
            <S.Description>{data.clubDescription}</S.Description>
            <S.Participants>
              참여자: ({data.participants}/{4})
            </S.Participants>
          </div>
          <S.ButtonContainer>
            <Link to="/Chat">
              <S.Button $primary>모임 시작</S.Button>
            </Link>
            <Link to="/Bookreport">
              <S.Button>독후감 작성</S.Button>
            </Link>
          </S.ButtonContainer>
        </S.Content>
      </S.Container>
    </>
  );
}
