import React, { useState } from "react";
import Header from "../common/Header";
import * as S from "./MyPage.style";

export function MyPage() {
  const [images, setImages] = useState([]);
  const [name, setName] = useState("");

  const handleImageUpload = (e) => {
    const files = Array.from(e.target.files);
    const imageUrls = files.map((file) => URL.createObjectURL(file));
    setImages((prevImages) => [...prevImages, ...imageUrls]);
  };

  const handleImageDelete = (indexToDelete) => {
    setImages((prevImages) => prevImages.filter((_, i) => i !== indexToDelete));
  };

  const handleWithdraw = () => {
    const confirm = window.confirm("계정을 탈퇴하시겠습니까?");
    if (confirm) {
      window.location.href = "/";
    }
  };
  
  const handleSave = () => {
    const confirm = window.confirm("수정하시겠습니까?");
    if (confirm) {
      localStorage.setItem("nickname", name);
      alert("저장되었습니다!");
      window.location.href = "/main";
    }
  };

  return (
    <>
      <Header />
        <S.Container>
            <S.ProfileSection>
              <S.ProfileImage>
                <span role="img" aria-label="profile">👤</span>
              </S.ProfileImage>
            <div>
              <S.Label htmlFor="fileUpload">파일선택</S.Label>
              <S.Input
                id="fileUpload"
                type="file"
                accept="image/*"
                multiple
                onChange={handleImageUpload}
                style={{ display: 'block' }}
              />
            <S.ImagePreviewContainer>
              {images.map((src, index) => (
                <S.ImageWrapper key={index}>
                  <S.ImagePreview src={src} alt={`미리보기 ${index + 1}`} />
                  <S.DeleteButton onClick={() => handleImageDelete(index)}>×</S.DeleteButton>
                </S.ImageWrapper>
              ))}
              </S.ImagePreviewContainer>
            </div>
          </S.ProfileSection>

            <div>
              <S.Label>아이디</S.Label>
              <S.Input type="text" value="" disabled />
            </div>

            <S.NameWrapper>
              <S.Label>이름(닉네임)</S.Label>
              <S.Input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </S.NameWrapper>


            <div>
              <S.Label>비밀번호 변경</S.Label>
              <S.Input type="password" placeholder="현재 비밀번호를 입력해주세요." />
              <S.Input type="password" placeholder="변경할 비밀번호를 입력해주세요." />
            </div>

      <S.RightAlignBox>
        <S.WithdrawButton onClick={handleWithdraw}>계정 탈퇴</S.WithdrawButton>
      </S.RightAlignBox>
      <S.SaveButton onClick={handleSave}>수정</S.SaveButton>
    </S.Container>
    </>
  );
}