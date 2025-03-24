import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  width: 70%;
  height: 700px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 12px;
  box-sizing: border-box;
  margin: 0 auto;
  position: relative;
`;

export const BookCover = styled.img`
  width: 30%;
  height: auto;
  border-radius: 8px;
  object-fit: cover;
  margin-right: 20px;
`;

export const Content = styled.div`
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  justify-content: space-between;
`;

export const Title = styled.h2`
  font-size: 28px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
`;

export const Date = styled.p`
  font-size: 18px;
  color: #777;
  margin-bottom: 20px;
`;

export const Description = styled.p`
  font-size: 16px;
  color: #555;
  line-height: 1.6;
`;

export const Participants = styled.p`
  font-size: 16px;
  color: #888;
  margin-top: 10px;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  margin-top: 20px;
`;

export const Button = styled.button`
  padding: 12px 24px;
  font-size: 16px;
  color: #fff;
  background-color: ${({ primary }) => (primary ? "#65558f" : "#aaa")};
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;

  &:hover {
    background-color: ${({ primary }) => (primary ? "#544270" : "#888")};
  }
`;

export const HeartIcon = styled.button`
  position: absolute;
  top: 16px;
  right: 16px;
  background: none;
  border: none;
  font-size: 32px;
  color: #ff6b6b;
  cursor: pointer;
  padding: 0;
`;
