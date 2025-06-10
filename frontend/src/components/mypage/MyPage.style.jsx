import styled from "styled-components";

export const Container = styled.div`
  max-width: 500px;
  margin: 0 auto;
  padding: 16px;
  display: flex;
  flex-direction: column;
  margin-top:50px;
  gap: 24px;
`;

export const Label = styled.label`
  display: block;
  font-size: 14px;
  margin-bottom: 4px;
`;

export const Input = styled.input`
  width: 100%;
  padding: 8px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #ECE6F0;
`;

export const NameWrapper = styled.div`
  position: relative;
`;

export const WithdrawButton = styled.button`
  color: black;
  text-decoration: underline;
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  align-self: flex-end;
`;

export const SaveButton = styled.button`
  margin-top: 8px;
  padding: 10px 16px;
  background-color: #65558F;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
`;

export const RightAlignBox = styled.div`
  display: flex;
  justify-content: flex-end;
`;

export const ImagePreviewContainer = styled.div`
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-top: 8px;
`;

export const ImageWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const ImagePreview = styled.img`
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 8px;
  border: 1px solid #ccc;
`;

export const DeleteButton = styled.button`
  position: absolute;
  top: 4px;
  right: 4px;
  background-color: white: 
  color: white;
  border: none;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  font-size: 16px;
  line-height: 1;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;

  &:hover {
    background-color:rgb(68, 68, 68);
  }
`;

export const EditIcon = styled.span`
  position: absolute;
  right: 8px;
  top: 36px;
  cursor: pointer;
  font-size: 20px;
`;