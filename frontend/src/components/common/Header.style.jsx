import styled from "styled-components";

export const Header = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: rgb(255, 255, 255);
  height: 60px;
  padding: 0 20px;
  border-bottom: 1px solid #ccc;
`;

export const Logo = styled.div`
  display: inline-block;
  color: #65558f;
  font-size: 28px;
  padding: 5px 10px;
  border-radius: 50px;
  font-weight: 800;
  margin-left: 20px;

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
  font-size: 18px;
  padding: 10px 20px;
  font-weight: 600;
  border-radius: 30px;
  transition: all 0.3s ease;
  cursor: pointer;

  &:hover {
    color: #4a3c6a;
    text-decoration: underline;
  }

  &:active {
    transform: translateY(1px);
  }
`;
