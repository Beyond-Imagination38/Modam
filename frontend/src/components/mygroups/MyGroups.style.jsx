import styled from "styled-components";

export const Container = styled.div`
  min-height: 100vh;
  background-color: #f3f4f6;
`;

export const Title = styled.h2`
  font-size: 24px;
  margin-bottom: 20px;
  padding: 0 0 0 20px;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  padding: 0 20px 0 20px;
`;

export const Button = styled.button`
  padding: 10px 16px;
  background-color: #65558f;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
`;

export const PostGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
`;

export const PostCard = styled.div`
  position: relative;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 12px;
  text-align: center;
`;

export const CheckboxContainer = styled.div`
  position: absolute;
  top: 8px;
  left: 8px;
`;

export const PostImage = styled.div`
  width: 100%;
  height: 100px;
  background-color: #d1d5db;
  border-radius: 8px;
`;

export const PostTitle = styled.p`
  margin-top: 8px;
  font-size: 14px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const SelectAllContainer = styled.div`
  margin-top: 20px;
  text-align: center;
`;

export const SelectAllButton = styled.button`
  padding: 8px 16px;
  background-color: #65558f;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
`;
