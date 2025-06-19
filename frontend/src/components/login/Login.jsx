import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import * as S from "./Login.style";
import { API_URLS } from "../../consts";

export function Login() {
  const [email, setEmail] = useState("");
  const [pw, setPw] = useState("");
  const navigate = useNavigate();

  const onClickConfirmButton = async () => {
    try {
      const response = await fetch(`${API_URLS.user}/login`, {
        method: "POST",
        body: JSON.stringify({ email, pw }),
      });

      console.log("로그인 API 응답:", response);

      const data = await response.json();  
      const status = response.status;

      if (response.status === 200 && data?.token && data?.userId) {
        localStorage.setItem("user", JSON.stringify({ id: data.userId }));
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId);
        
        alert("로그인 성공!");
        navigate("/main");
      } else if (response.status === 400) {
        alert(data?.error || "잘못된 요청입니다.");
      } else if (response.status === 401) {
        alert(data?.error || "이메일 또는 비밀번호가 올바르지 않습니다.");
      } else {
        alert("알 수 없는 오류가 발생했습니다.");
        console.error("예외 처리되지 않은 응답:", data);
      }
    } catch (error) {
      console.error("로그인 요청 오류:", error);
      alert("네트워크 오류가 발생했습니다. 다시 시도해 주세요.");
    }
  };

  
  return (
    <S.Page>
      <S.ContentWrap>
        <S.Title onClick={() => navigate("/")}>Modam</S.Title>
        <S.InputTitle>이메일</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </S.InputWrap>

        <S.InputTitle>비밀번호</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="password"
            value={pw}
            onChange={(e) => setPw(e.target.value)}
          />
        </S.InputWrap>

        <S.BottomButton onClick={onClickConfirmButton}>로그인</S.BottomButton>
        <Link to="/main">
          <S.GoButton>일단 둘러보기</S.GoButton>
        </Link>
        <Link to="/signup">
          <S.SignupLink>회원가입하기</S.SignupLink>
        </Link>
      </S.ContentWrap>
    </S.Page>
  );
}