import styled from "styled-components";

export const Container = styled.div`
  background-color: #f8f8f8;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 50px;
  font-size: 16px;
`;

export const Title = styled.h2`
  color: #65558f;
  margin-bottom: 20px;
  font-size: 32px;
`;

export const Form = styled.form`
  min-width: 500px;
  display: flex;
  flex-direction: column;
  gap: 15px;
`;

export const Label = styled.label`
  font-weight: bold;
`;

export const Input = styled.input`
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
`;

export const Select = styled.select`
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
`;

export const TextArea = styled.textarea`
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  height: 100px;
`;

export const CheckboxContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

export const ButtonContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
`;

export const Button = styled.button`
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  color: white;
  background-color: ${(props) => (props.primary ? "#65558f" : "#ccc")};

  &:hover {
    background-color: ${(props) =>
      props.primary ? "rgb(67, 43, 125)" : "#aaa"};
  }
`;

export const ImagePreviewContainer = styled.div`
  display: flex;
  gap: 10px;
  margin-top: 10px;
`;

export const ImagePreview = styled.img`
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ddd;
`;
