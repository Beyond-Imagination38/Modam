import React, { useState, useEffect } from "react";
import * as S from "./Header.style";
import { Link } from "react-router-dom";
import buttonmypagepng from "./button_mypage.png";

export default function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const storedLogin = localStorage.getItem("token");
    if (storedLogin === "true") {
      setIsLoggedIn(true);
    }
  }, []);

  return (
    <S.Header>
      <Link to="/">
        <S.Logo>Modam</S.Logo>
      </Link>
      <S.Nav>
        {!isLoggedIn && (
          <Link to="/">
            <S.Button>로그인</S.Button>
          </Link>
        )}
        <Link to="/main">
          <S.Button>독서모임</S.Button>
        </Link>
        <Link to="/mypage">
          <S.MypageButton>
            <img src={buttonmypagepng} alt="마이페이지" />
          </S.MypageButton>
        </Link>
      </S.Nav>
    </S.Header>
  );
}
