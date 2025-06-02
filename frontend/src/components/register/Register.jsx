import { useState, useEffect } from "react";
import Header from "../common/Header";
import * as S from "./Register.style";
import { Link, useNavigate, useParams } from "react-router-dom";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Register() {
  const navigate = useNavigate();
  const { postId } = useParams();

  const [images, setImages] = useState([]);
  const [title, setTitle] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [time, setTime] = useState("");
  const [content, setContent] = useState("");

  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files);
    const imageUrls = files.map((file) => URL.createObjectURL(file));
    setImages(imageUrls);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userId = localStorage.getItem("userId");

    if (!userId) {
      alert("로그인이 필요합니다.");
      return;
    }

    const postData = {
      hostId: Number(userId),
      bookTitle: title,
      date: meetingDate,
      time: time,
      clubDescription: content,
    };

    console.log("서버로 보낼 데이터:", JSON.stringify(postData, null, 2));

    try {
      const response = await fetchApi(`${API_URLS.bookclubs}`, {
        method: "POST",
        body: JSON.stringify(postData),
      });

      const { status, data } = response;

      if (status >= 200 && status < 300) {
        alert("모임이 등록되었습니다.");
        navigate("/main");
      } else {
        console.error("등록 실패 응답:", data);
        alert(data?.message || "모임 등록에 실패했습니다.");
      }
    } catch (error) {
      console.error("모임 등록 중 오류:", error);
      alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <>
      <Header />
      <S.Container>
        <S.Title>독서 모임 등록</S.Title>
        <S.Form>
          <S.Label>이미지</S.Label>
          <S.Input
            type="file"
            accept="image/*"
            multiple
            onChange={handleImageUpload}
          />

          {/* 이미지 미리보기 */}
          <S.ImagePreviewContainer>
            {images.map((src, index) => (
              <S.ImagePreview
                key={index}
                src={src}
                alt={`미리보기 ${index + 1}`}
              />
            ))}
          </S.ImagePreviewContainer>

          <S.Label>책 제목</S.Label>
          <S.Input
            type="text"
            placeholder="책 제목을 입력하세요"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          <S.Label>날짜</S.Label>
          <S.Input
            type="date"
            value={meetingDate}
            onChange={(e) => setMeetingDate(e.target.value)}
          ></S.Input>

          <S.Label>시간</S.Label>
          <S.Input
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />

          <S.Label>설명</S.Label>
          <S.TextArea
            placeholder="설명을 입력하세요"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          ></S.TextArea>

          <S.ButtonContainer>
            <Link to="/Main">
              <S.Button>돌아가기</S.Button>
            </Link>
            <S.Button $primary onClick={handleSubmit}>
              등록
            </S.Button>
          </S.ButtonContainer>
        </S.Form>
      </S.Container>
    </>
  );
}
