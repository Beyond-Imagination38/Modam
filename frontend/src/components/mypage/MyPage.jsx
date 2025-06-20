import { useState, useEffect } from "react";
import Header from "../common/Header";
import * as S from "./MyPage.style";
import { API_URLS } from "../../consts";

export function MyPage() {
  const [email, setEmail] = useState("");
  const [userName, setUserName] = useState("");
  const [currentPw, setCurrentPw] = useState("");
  const [newPw, setNewPw] = useState("");

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.id;

    if (!userId) {
      const goToLogin = window.confirm("로그인 정보가 없습니다. 로그인 페이지로 이동하시겠습니까?");
      if (goToLogin) {
        window.location.href = "/login"; 
      }
      return;
    }

    fetch(API_URLS.getUserInfo(userId), {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => {
        if (!res.ok) throw new Error("회원 정보 조회 실패");
        return res.json();
      })
      .then((data) => {
        console.log("Fetched user data:", data);
        setEmail(data.email);           
        setUserName(data.userName);    
      })
      .catch((err) => {
        console.error(err);
        alert("회원 정보를 불러오지 못했습니다.");
      });
  }, []);

  const handleSave = async () => {
    const isConfirmed = window.confirm("수정하시겠습니까?");
    if (!isConfirmed) return;

    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.id;
    const token = localStorage.getItem("token");

    let isChanged = false; 

    try {
      // 닉네임(이름) 서버에 반영
      const nicknameRes = await fetch(API_URLS.updateUserName(userId), {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({ userName }),
      });

      if (!nicknameRes.ok) {
        const errorText = await nicknameRes.text();
        //throw new Error(`닉네임 수정 실패: ${errorText}`);
        alert("변경 사항이 저장되었습니다!");
      }

      localStorage.setItem("nickname", userName);
      isChanged = true;

      // 비밀번호 변경 요청 (입력된 경우에만)
      if (currentPw && newPw) {
        if (currentPw === newPw) {
          alert("기존 비밀번호와 새 비밀번호가 같습니다.");
          return;
        }

        const pwRes = await fetch(API_URLS.updateUserPassword(userId), {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify({
            currentPw,
            newPw,
          }),
        });
      
        if (!pwRes.ok) {
          const errorText = await pwRes.text();
          //throw new Error(`비밀번호 변경 실패: ${errorText}`);
          alert("변경 사항이 저장되었습니다!");
        }
        isChanged = true;
      }

      if (isChanged) {
        alert("변경 사항이 저장되었습니다!");
      }
    } catch (error) {
      alert(`오류 발생: ${error.message}`);
    }
  };



  return (
    <>
      <Header />
      <S.Container>
        <div>
          <S.Label>이메일</S.Label>
          <S.Input type="text" value={email} disabled />
        </div>

        <S.NameWrapper>
          <S.Label>이름(닉네임)</S.Label>
          <S.Input
            type="text"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
        </S.NameWrapper>

        <div>
          <S.Label>비밀번호 변경</S.Label>
          <S.Input
            type="password"
            placeholder="현재 비밀번호를 입력해주세요."
            value={currentPw}
            onChange={(e) => setCurrentPw(e.target.value)}
          />
          <S.Input
            type="password"
            placeholder="변경할 비밀번호를 입력해주세요."
            value={newPw}
            onChange={(e) => setNewPw(e.target.value)}
          />
        </div>
        <S.SaveButton onClick={handleSave}>수정</S.SaveButton>
      </S.Container>
    </>
  );
}
