import { useState } from "react";
import Header from "../common/Header";
import * as S from "./MyPage.style";

export function MyPage() {
  const [name, setName] = useState("");

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