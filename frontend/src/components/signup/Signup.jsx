import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "./Signup.style.jsx";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Signup() {
  const [email, setEmail] = useState("");
  const [userName, setuserName] = useState("");
  const [pw, setpw] = useState("");
  const [pwcheck, setPwcheck] = useState("");

  const navigate = useNavigate();

  const [emailValid, setEmailValid] = useState(false);
  const [pwValid, setPwValid] = useState(false);

  const handleEmail = (e) => {
    setEmail(e.target.value);
    const regex =
      /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/;
    if (regex.test(e.target.value)) {
      setEmailValid(true);
    } else {
      setEmailValid(false);
    }
  };

  const handlepw = (e) => {
    setpw(e.target.value);
    const regex = /^[A-Za-z0-9]{8,20}$/;
    if (regex.test(e.target.value)) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  };

  const handleSignup = async () => {
    if (!emailValid || !pwValid || pw !== pwcheck) {
      alert("입력한 정보를 다시 확인해주세요.");
      return;
    }
    alert("회원가입이 완료되었습니다!");
    navigate("/login");
    /*
    try {
      const signupResponse = await fetchApi(API_URLS.signup, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, userName, pw, pwcheck }),
      });

      console.log("회원가입 API 응답:", signupResponse);

      if (signupResponse.status === 200 && signupResponse.data?.userId) {
        const loginResponse = await fetchApi(API_URLS.login, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email, pw }),
        });

        console.log("로그인 API 응답:", loginResponse);

        if (loginResponse.status === 200 && loginResponse.data?.token) {
          localStorage.setItem("token", loginResponse.data.token);
          localStorage.setItem("userId", loginResponse.data.userId); 

          alert("회원가입이 완료되었습니다! 자동 로그인되었습니다.");
          navigate("/main");
        } else {
          alert(
            "회원가입은 완료되었지만 자동 로그인에 실패했습니다. 로그인 페이지로 이동합니다."
          );
          navigate("/login");
        }
      } else {
        alert(signupResponse?.data?.error || "회원가입에 실패했습니다.");
      }
    } catch (error) {
      console.error("회원가입 오류:", error);
      alert(
        error.response?.data?.error ||
          "서버 오류가 발생했습니다. 다시 시도해주세요."
      );
    }*/
  };

  return (
    <S.Page>
      <S.Title onClick={() => navigate("/")}>Modam</S.Title>
      <S.ContentWrap>
        <S.InputTitle marginTop="100px">이메일</S.InputTitle>
        <S.InputWrap>
          <S.Input type="text" value={email} onChange={handleEmail} />
        </S.InputWrap>
        <S.ErrorMessageWrap>
          {!emailValid && email.length > 0 && (
            <div>올바른 이메일을 입력해주세요.</div>
          )}
        </S.ErrorMessageWrap>

        <S.InputTitle marginTop="26px">비밀번호</S.InputTitle>
        <S.InputWrap>
          <S.Input type="pw" value={pw} onChange={handlepw} />
        </S.InputWrap>
        <S.ErrorMessageWrap>
          {!pwValid && pw.length > 0 && (
            <div>올바른 비밀번호를 입력해주세요.</div>
          )}
        </S.ErrorMessageWrap>

        <S.InputTitle>비밀번호 확인</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="pw"
            value={pwcheck}
            onChange={(e) => setPwcheck(e.target.value)}
          />
        </S.InputWrap>
        <S.ErrorMessageWrap>
          {pw !== pwcheck && pwcheck.length > 0 && (
            <div>비밀번호가 일치하지 않습니다.</div>
          )}
        </S.ErrorMessageWrap>

        <S.InputTitle>닉네임</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="text"
            value={userName}
            onChange={(e) => setuserName(e.target.value)}
          />
        </S.InputWrap>

        <S.BottomButton onClick={handleSignup}>회원가입</S.BottomButton>
        <S.BottomText>
          이미 계정이 있다면?{" "}
          <span onClick={() => navigate("/login")}>로그인하기</span>
        </S.BottomText>
      </S.ContentWrap>
    </S.Page>
  );
}
