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
        <S.Logo>Modam</S.Logo>
      <S.Nav>
        <Link to="/mypage">
          <S.Button>마이페이지</S.Button>
        </Link>
      </S.Nav>
    </S.Header>
  );
}
