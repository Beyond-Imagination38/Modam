import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  height: 100vh;
  background: rgb(255, 255, 255);
  justify-content: center;
  align-items: center;
  gap: 20px;
  max-width: 1100px;
  margin: 0 auto;
`;

export const ChatSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  max-width: 700px;
  height: 100%;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #65558f;
  padding: 12px 16px;
  color: white;
  font-size: 18px;
  border-radius: 8px;
`;

export const RightSection = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

export const Title = styled.div`
  font-weight: bold;
`;

export const NoteText = styled.div`
  background: #fff;
  color: #65558f;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;

  &:hover {
    background-color: #eaeaea;
  }
`;

export const ExitButton = styled.button`
  background: #fff;
  color: #65558f;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  border: 1px solid #ccc;

  &:hover {
    background-color: #eaeaea;
  }
`;

export const ChatBox = styled.div`
  flex: 1;
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  padding: 16px;
  overflow-y: auto;
  border-radius: 8px;
`;

export const Message = styled.div`
  display: flex;
  align-items: flex-start;
  margin: 5px 0;
  gap: 3px;

  &.my-message {

  text-align: left;

    strong {
      color: #333;
    }

    div {
      background-color: #e6e6ff;
    }
  }

  &.user-message {
    strong {
      color: #333;
    }

    div {
      background-color: #f1f0f0;
    }
  }

  &.bot-message {
    strong {
      color: #0c5460;
    }

    div {
      background-color: #d1ecf1;
    }
  }

  div {
    padding: 8px 12px;
    border-radius: 12px;
    max-width: 100%;
  }
`;

export const Avatar = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
`;

export const MemoSection = styled.div`
  width: 300px;
  padding: 10px;
  border-left: 1px solid #ccc;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  height: 95%;
`;

export const MemoTitle = styled.h3`
  margin-bottom: 8px;
  font-size: 16px;
  color: #333;
`;

export const MemoInput = styled.textarea`
  flex: 1;
  width: 100%;
  height: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
  outline: none;

  &:focus {
    border-color: rgb(18, 1, 114);
  }
`;

export const InputContainer = styled.div`
  display: flex;
  align-items: center;
  background: #e0e0e0;
  padding: 8px 12px;
  border-radius: 8px;
`;

export const Input = styled.input`
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 20px;
  outline: none;
  font-size: 16px;
  background: white;
`;

export const SendButton = styled.button`
  background: none;
  border: none;
  color: #65558f;
  font-size: 16px;
  cursor: pointer;
  font-weight: bold;

  &:hover {
    background-color: #eaeaea;
  }
`;
