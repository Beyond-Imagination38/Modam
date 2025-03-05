import styled from "styled-components";

export const Header = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #65558f;
  height: 60px;
  padding: 0 20px;
`;

export const Logo = styled.div`
  display: inline-block;
  color: #fcffec;
  font-size: 28px;
  padding: 5px 10px;
  border-radius: 50px;

  &:hover {
    transform: scale(1.1);
  }
`;

export const Nav = styled.nav`
  display: flex;
  gap: 20px;
  padding: 5px 0 0 0;
`;

export const Button = styled.div`
  display: inline-block;
  color: #65558f;
  font-size: 25px;
  background-color: #fcffec;
  padding: 5px 10px;
  border-radius: 50px;
  font-weight: 700;

  &:hover {
    background-color: #65558f;
    color: #fcffec;
  }
`;

export const MypageButton = styled.div`
  img {
    height: 45px;
  }

  &:hover img {
    transform: scale(1.1);
  }
`;
