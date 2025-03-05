import styled from "styled-components";

export const Page = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  min-width: 600px;
`;

export const ContentWrap = styled.div`
  margin-top: 32px;
  flex: 1;
`;

export const InputTitle = styled.div`
  font-size: 12px;
  font-weight: 600;
  color: #262626;
`;

export const InputWrap = styled.div`
  display: flex;
  border-radius: 8px;
  padding: 16px;
  margin-top: 8px;
  background-color: rgb(251, 251, 251);
  border: 1px solid #e2e0e0;

  &:focus-within {
    border: 1px solid #65558f;
  }
`;

export const Input = styled.input`
  width: 100%;
  outline: none;
  border: none;
  height: 17px;
  font-size: 14px;
  font-weight: 400;
`;

export const BottomButton = styled.button`
  width: 100%;
  height: 48px;
  border: none;
  font-weight: bold;
  border-radius: 64px;
  background-color: #65558f;
  color: #fff8f3;
  margin-bottom: 16px;
  margin-top: 26px;
  cursor: pointer;
`;

export const ErrorMessageWrap = styled.div`
  margin-top: 8px;
  color: #ef0000;
  font-size: 12px;
`;
