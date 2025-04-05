import React, { useState, useEffect } from "react";
import * as S from "./Header.style";
import { Link } from "react-router-dom";

export default function Header() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsLoggedIn(token === "true");
  }, []);

  return (
    <S.Header>
      <Link to="/main">
        <S.Logo>Modam</S.Logo>
      </Link>
      <S.Nav>
        {!isLoggedIn ? (
          <Link to="/login">
            <S.Button>로그인</S.Button>
          </Link>
        ) : (
          <Link to="/main">
            <S.Button>독서모임</S.Button>
          </Link>
        )}
        <Link to="/mypage">
          <S.Button>마이페이지</S.Button>
        </Link>
      </S.Nav>
    </S.Header>
  );
}
