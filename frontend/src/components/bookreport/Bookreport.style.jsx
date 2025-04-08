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

export const TextareaContainer = styled.div`
  display: flex;
  justify-content: center;
  flex-grow: 1;
  padding: 0 40px;
  margin-top: 20px;
`;

export const Textarea = styled.textarea`
  width: 100%;
  max-width: 700px;
  height: 600px;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: ${(props) => (props.readOnly ? "#65558f5" : "#fff")};
  color: ${(props) => (props.readOnly ? "#aaa" : "black")};
  box-sizing: border-box;
  margin: 0 10px;

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
  margin-right: 200px;
`;

export const Actions = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 20px;
`;

export const Button = styled.button`
  width: 300px; /* 버튼 너비 제한 */
  margin: 0 5px; /* 버튼 사이 간격 유지 */
  padding: 10px;
  font-size: 14px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #65558f;
  color: white;

  &:hover {
    background-color: #c7c7c7;
  }
`;
