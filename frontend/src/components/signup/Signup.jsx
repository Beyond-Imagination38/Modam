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
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,20}$/
    if (regex.test(e.target.value)) {
      setPwValid(true);
    } else {
      setPwValid(false);
    }
  };

  const handleSignup = async () => {
  if (!emailValid || !pwValid || pw !== pwcheck || !userName.trim()) {
    alert("입력한 정보를 다시 확인해주세요.");
    return;
  }
    
      try {
      const { status, data } = await fetchApi(API_URLS.signup, {
        method: "POST",
        body: JSON.stringify({
          user_name: userName,
          email,
          pw,
          profile_image: null,
        }),
      });

      if (status === 409) {
        alert(data?.message || "이미 존재하는 계정입니다.");
        return;
      }

      if (status !== 200) {
        throw new Error("회원가입에 실패했습니다. 다시 시도해주세요.");
      }
      console.log("회원가입 성공:", data);

      localStorage.setItem("token", data.token);
      localStorage.setItem("userEmail", email);
      localStorage.setItem("userName", userName);

      alert("회원가입이 완료되었습니다!");
      navigate("/login");
    } catch (error) {
      console.error("회원가입 오류:", error);
      alert("회원가입 중 문제가 발생했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <S.Page>
      <S.Title onClick={() => navigate("/")}>Modam</S.Title>
      <S.ContentWrap>
        <S.InputTitle>이메일</S.InputTitle>
        <S.InputWrap>
          <S.Input type="text" value={email} onChange={handleEmail} />
        </S.InputWrap>
        <S.ErrorMessageWrap>
          {!emailValid && email.length > 0 && (
            <div>올바른 이메일을 입력해주세요.</div>
          )}
        </S.ErrorMessageWrap>

        <S.InputTitle>비밀번호</S.InputTitle>
        <S.InputWrap>
          <S.Input type="password" value={pw} onChange={handlepw} />
        </S.InputWrap>
        <S.ErrorMessageWrap>
          {!pwValid && pw.length > 0 && (
            <div>
              비밀번호는 8~20자이며, 영문과 숫자, 특수문자를 포함해야 합니다.
            </div>
          )}
        </S.ErrorMessageWrap>

        <S.InputTitle>비밀번호 확인</S.InputTitle>
        <S.InputWrap>
          <S.Input
            type="password"
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