import { fetchApi } from "../../utils";
import Header from "../common/Header";
import * as S from "./Detail.style";
import { useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import img1984 from "./1984.jpg";

export function Detail() {
  //const [data, setData] = useState(null);
  const [data, setData] = useState({
    postId: 1,
    clubId: 2,
    bookTitle: "1984",
    author: "조지 오웰",
    meetingDate: "2025-04-10 20:00",
    maxMembers: 4,
    currentMembers: 2,
    summary:
      "책의 의미를 현대적 관점에서 해석하며 현실 정치와 리더십에 대해 탐구합니다.",
  });
  const { postId } = useParams();
  const [liked, setLiked] = useState(false);

  useEffect(() => {
    setData(data);
  }, [postId]);

  const handleHeartClick = () => {
    setLiked(!liked);
  };

  /*
  useEffect(() => {
    const getData = async () => {
      try {
        const result = await fetchApi(clubId);
        setData(result);
      } catch (error) {
        console.error("데이터 불러오기 실패:", error);
      } finally {
        setLoading(false);
      }
    };

    getData();
  }, [clubId]);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  if (!data) {
    return <div>데이터가 없습니다.</div>;
  }
*/
  return (
    <>
      <Header />
      <S.Container>
        <S.BookCover
          src={img1984}
          alt="bookTitle" 
        //src={`/images/book-cover-${data.clubId}.jpg`}
        //alt={data.bookTitle}
        />
        <S.Content>
          <div>
            <S.Title>{data.bookTitle}</S.Title>
            <S.Date>{data.meetingDate}</S.Date>
            <S.Description>{data.summary}</S.Description>
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
            <S.HeartIcon onClick={handleHeartClick}>
              {liked ? "❤" : "♡"}
            </S.HeartIcon>
          </S.ButtonContainer>
        </S.Content>
      </S.Container>
    </>
  );
}
