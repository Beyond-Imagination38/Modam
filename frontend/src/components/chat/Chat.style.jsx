import styled from "styled-components";

export const Container = styled.div`
  display: flex;
  height: 100vh;
  background: #f5f5f5;
  flex-direction: row;
`;

export const ChatSection = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #65558f;
  padding: 12px 16px;
  color: white;
  font-size: 18px;
`;

export const RightSection = styled.div`
  display: flex;
  align-items: center;
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
    background-color: #ccc;
  }
`;

export const ExitButton = styled.button`
  background: #fff;
  color: #65558f;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;

  &:hover {
    background-color: #ccc;
  }
`;

export const ChatBox = styled.div`
  flex: 1;
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  overflow-y: auto;
`;

export const Message = styled.div`
  margin-bottom: 15px;
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  background: #f9f9f9;
`;

export const MemoSection = styled.div`
  width: 300px;
  padding: 10px;
  border-left: 1px solid #ccc;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  order: 1;
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
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  resize: none;
  box-sizing: border-box;
  outline: none;

  &:focus {
    border-color: #007bff;
  }
`;

export const InputContainer = styled.div`
  display: flex;
  align-items: center;
  background: #e0e0e0;
  padding: 8px 16px;
`;

export const Input = styled.input`
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 20px;
  outline: none;
  font-size: 16px;
  margin: 0 8px;
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
    background-color: #ccc;
  }
`;
