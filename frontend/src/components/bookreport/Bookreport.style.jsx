import styled from "styled-components";

export const Container = styled.div`
  width: 100%;
  height: 100%;
  margin: 0 auto;
  padding: 0;
  box-sizing: border-box;
  background: #f8f8f8;
  display: flex;
  flex-direction: column;
  overflow: hidden;
`;

export const Header = styled.h1`
  font-size: 18px;
  color: white;
  background-color: #65558f;
  text-align: left;
  padding: 10px;
  margin: 0;
`;

export const TextareaContainer = styled.div`
  display: flex;
  justify-content: space-between;
  flex-grow: 1;
`;

export const Textarea = styled.textarea`
  width: 100%;
  height: 600px;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
  background-color: ${(props) => (props.readOnly ? "#f5f5f5" : "#fff")};
  color: ${(props) => (props.readOnly ? "#aaa" : "black")};
  box-sizing: border-box;

  &::placeholder {
    color: #ccc;
    font-size: 14px;
  }
`;

export const RemainingCount = styled.div`
  text-align: right;
  font-size: 14px;
  color: #4a4a4a;
  margin-bottom: 10px;
`;

export const Actions = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
`;

export const Button = styled.button`
  flex: 1;
  margin: 0 5px;
  padding: 10px;
  font-size: 14px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: ${(props) => props.bg || "#d9d9d9"};
  color: ${(props) => props.color || "black"};

  &:hover {
    background-color: ${(props) =>
      props.bgHover || (props.bg === "#d9d9d9" ? "#c7c7c7" : "#5c4091")};
  }
`;
