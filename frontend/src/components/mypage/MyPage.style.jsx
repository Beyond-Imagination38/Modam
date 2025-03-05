import styled from "styled-components";

export const SelectMenu = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 20px 0;
  padding: 90px;
`;

export const Button = styled.div`
  display: block;
  height: 60px;
  width: 500px;
  border: 3px solid;
  border-radius: 15px;
  background-color: #65558f;
  color: white;
  font-size: 32px;
  text-align: center;
  line-height: 60px;

  &:hover {
    background-color: rgb(102, 76, 167);
    color: white;
  }
`;

export const Text = styled.div`
  color: gray;
  font-size: 20px;
  text-decoration: underline;
  cursor: pointer;
`;
