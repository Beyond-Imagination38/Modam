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
    setData(data);
  }, [clubId]);

 useEffect(() => {
    const getData = async () => {
      try {
        const url = API_URLS.bookclubDetail(clubId);
        const { status, data } = await fetchApi(url, { method: "GET" });

        if (status !== 200) throw new Error("서버 응답 실패");

        setData(data); 
      } catch (error) {
        console.error("상세 정보 로드 실패:", error);
        alert("상세 정보를 불러오는 데 실패했습니다.");
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
              참여자: ({data.currentMembers}/{data.maxMembers})
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
