import { useState, useEffect } from "react";
import Header from "../common/Header";
import * as S from "./MyPage.style";

export function MyPage() {
  const [email, setEmail] = useState("");
  const [userName, setUserName] = useState("");
  const [currentPw, setCurrentPw] = useState("");
  const [newPw, setNewPw] = useState("");

  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.id;

     console.log("token from localStorage:", localStorage.getItem("token"));

    if (!userId) {
      alert("로그인 정보가 없습니다.");
      return;
    }

    fetch(`http://localhost:8080/user/${userId}`, {
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
        setEmail(data.email);           // 이메일 표시용
        setUserName(data.userName);    // 닉네임
      })
      .catch((err) => {
        console.error(err);
        alert("회원 정보를 불러오지 못했습니다.");
      });
  }, []);

  const handleWithdraw = () => {
    const confirm = window.confirm("계정을 탈퇴하시겠습니까?");
    if (confirm) {
      window.location.href = "/";
    }
  };

  const handleSave = async () => {
    const isConfirmed = window.confirm("수정하시겠습니까?");
    if (!isConfirmed) return;

    const userId = localStorage.getItem("userId");
    const token = localStorage.getItem("token");

    try {
      // 닉네임(이름) 서버에 반영
      const nicknameRes = await fetch(`http://localhost:8080/user/${userId}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({ userName }),
      });

      console.log("닉네임 변경 요청", {
        method: "PATCH",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ userName }),
      });

      if (!nicknameRes.ok) {
        const errorText = await nicknameRes.text();
        console.error("닉네임 수정 실패", {
          status: nicknameRes.status,
          userId,
          token,
          requestBody: { userName },
        });
        
        throw new Error(`닉네임 수정 실패: ${errorText}`);
      }

      localStorage.setItem("nickname", userName);
      alert("닉네임이 저장되었습니다!");

      // 비밀번호 변경 요청 (입력된 경우에만)
      if (currentPw && newPw) {
        if (newPw.length < 8) {
          alert("비밀번호는 최소 8자 이상이어야 합니다.");
          return;
        }
        if (currentPw === newPw) {
          alert("기존 비밀번호와 새 비밀번호가 같습니다.");
          return;
        }

        const pwRes = await fetch(`http://localhost:8080/user/${userId}/password`, {
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
          throw new Error("비밀번호 변경 실패");
        }

        alert("비밀번호가 변경되었습니다.");
      }

      // 메인 페이지로 이동
      window.location.href = "/main";
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

        <S.RightAlignBox>
          <S.WithdrawButton onClick={handleWithdraw}>계정 탈퇴</S.WithdrawButton>
        </S.RightAlignBox>
        <S.SaveButton onClick={handleSave}>수정</S.SaveButton>
      </S.Container>
    </>
  );
}
