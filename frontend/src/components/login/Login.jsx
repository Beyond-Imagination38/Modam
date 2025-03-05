import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "../signup/Signup.style.jsx";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

const User = {
  email: "ewha1886@naver.com",
  pw: "womansuni012",
};

export function Login() {
  const [email, setEmail] = useState("");
  const [pw, setPw] = useState("");

  const navigate = useNavigate();

  const onClickConfirmButton = () => {
    if (email === User.email && pw === User.pw) {
      //email, pw 체크
      alert("로그인에 성공했습니다.");
      navigate("/main");
    } else {
      alert("이메일 또는 비밀번호를 확인해 주세요. ");
    }
    /*
  const onClickConfirmButton = async () => {
    try {
      const response = await fetchApi(API_URLS.login, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });

      console.log("📌 로그인 API 응답:", response);

      if (response.status === 200 && response.data) {
        if (response.data.token && response.data.userId) {
          // ✅ API 응답에서 userId를 받아서 저장
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("userId", response.data.userId);

          alert("로그인 성공!");
          navigate("/main");
        } else {
          alert("서버 응답이 올바르지 않습니다.");
        }
      } else if (response.status === 400) {
        console.log("📌 400 응답 데이터:", response);
        alert(response.data?.error || "요청이 올바르지 않습니다.");
      } else if (response.status === 401) {
        console.log("📌 401 응답 데이터:", response);
        alert(
          response?.data?.error || "이메일 또는 비밀번호가 올바르지 않습니다."
        );
      } else {
        console.log("📌 예외 처리되지 않은 응답:", response);
        alert("알 수 없는 오류가 발생했습니다.");
      }
    } catch (error) {
      console.error("🚨 로그인 요청 오류:", error);
      alert(
        error.response?.data?.error ||
          "네트워크 오류가 발생했습니다. 다시 시도해 주세요."
      );
    }*/
  };

  return (
    <S.Page>
      <S.ContentWrap>
        <S.InputTitle>이메일</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </S.InputWrap>

        <S.InputTitle style={{ marginTop: "26px" }}>비밀번호</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="password"
            value={pw}
            onChange={(e) => setPw(e.target.value)}
          />
        </S.InputWrap>

        <S.BottomButton onClick={onClickConfirmButton}>로그인</S.BottomButton>
      </S.ContentWrap>
    </S.Page>
  );
}
