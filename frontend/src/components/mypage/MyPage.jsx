import React from "react";
import * as S from "./MyPage.style";
import Header from "../common/Header";
import { Link } from "react-router-dom";
import { fetchApi } from "../../utils";
import { API_URLS } from "../../consts";

export function MyPage() {
  return (
    <div>
      <Header />
      <S.SelectMenu>
        <Link to="/mypage/myinfo" style={{ textDecorationLine: "none" }}>
          <S.Button>내 정보 관리</S.Button>
        </Link>
        <Link to="/mypage/mymeetings" style={{ textDecorationLine: "none" }}>
          <S.Button>독서 모임 관리</S.Button>
        </Link>
        <Link to="/mypage/like" style={{ textDecorationLine: "none" }}>
          <S.Button>좋아요한 독서모임</S.Button>
        </Link>
        <Link to="/" style={{ textDecorationLine: "none" }}>
          <S.Button>로그아웃</S.Button>
        </Link>
        <S.Text style={{ cursor: "pointer" }}>탈퇴하기</S.Text>
      </S.SelectMenu>
    </div>
  );
}
