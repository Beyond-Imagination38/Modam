import styled from "styled-components";

export const HomeContainer = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  min-width: 500px;
`;

export const Title = styled.h1`
 display: flex;
  justify-content: center;
  margin-bottom: 20px;

  img {
    width: 300x;  
    height: 100px;
  }
`;

export const Nav = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
`;

export const Button = styled.button`
  display: inline-block;
  padding: 12px 24px;
  border-radius: 8px;
  border: none;
  font-size: 18px;
  font-weight: 500;
  cursor: pointer;
  color: #fff;
  background-color: #65558f;
  width: 180px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease, transform 0.1s ease;

  &:hover {
    background-color: #56487a;
    transform: translateY(-2px);
  }

  &:active {
    background-color: #4c3f6b;
    transform: translateY(0);
  }
`;

export const SignupButton = Button;
export const LoginButton = Button;

export const GoButton = styled.p`
  font-size: 15px;
  color: #857f7f;

  &:hover {
    opacity: 0.8;
  }
`;
