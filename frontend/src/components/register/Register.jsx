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

  //이미지 파일을 업로드할 때 미리보기
  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files);
    const imageUrls = files.map((file) => URL.createObjectURL(file));
    setImages(imageUrls);
  };

  /*
  useEffect(() => {
    if (isEditMode) {
      async function fetchPostDetail() {
        try {
          const response = await fetchApi(`${API_URLS.posts}/${postId}`, {
            method: "GET",
          });
          if (response) {
          //추가
          
            if (postData.images) {
              setImages(postData.images);
            }
          }
        } catch (err) {
          console.error(err);
          alert("게시글 정보를 불러오는 데 실패했습니다.");
        }
      }
      fetchPostDetail();
    }
  }, [isEditMode, postId]);
*/

  const handleSubmit = async (e) => {
    e.preventDefault();

    const postData = {
      title,
      meetingDate,
      time,
      content,
    };

    console.log("📌 서버로 보낼 데이터:", JSON.stringify(postData, null, 2));
    /*
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("로그인이 필요합니다.");
        return;
      }

      const response = await fetchApi(API_URLS.posts, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(postData),
      });

      console.log("📌 게시글 등록 API 응답:", response);

      if (response && (response.status === 200 || response.status === 201)) {
        alert("게시글이 등록되었습니다.");
        navigate("/main");
      } else {
        console.error("🚨 오류 응답:", response);
        alert(response?.message || "요청 중 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error("🚨 요청 실패:", error);
      alert("서버 오류가 발생했습니다. 다시 시도해주세요.");
    }*/
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
          <S.Select
            name="meetingDate"
            value={meetingDate}
            onChange={(e) => setMeetingDate(e.target.value)}
          >
            <option>선택하세요</option>
          </S.Select>

          <S.Label>시간</S.Label>
          <S.Select
            name="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          >
            <option>선택하세요</option>
          </S.Select>

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
            <S.Button primary onClick={handleSubmit}>
              등록
            </S.Button>
          </S.ButtonContainer>
        </S.Form>
      </S.Container>
    </>
  );
}
