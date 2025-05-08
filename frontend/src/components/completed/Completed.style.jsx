import styled from "styled-components";

export const Container = styled.div`
  max-width: 600px;
  margin: 40px auto;
`;

export const Title = styled.h1`
  font-size: 24px;
  font-weight: bold;
  color: #65558f;
  text-align: center;
  margin-bottom: 8px;
`;

export const Subtitle = styled.p`
  font-size: 14px;
  color: #6b7280;
  text-align: center;
  margin-bottom: 24px;
`;

export const Card = styled.div`
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow: hidden;
`;

export const TabHeader = styled.div`
  display: flex;
  justify-content: center;
  gap: 16px;
  border-bottom: 1px solid #e5e7eb;
  padding: 12px 16px;
`;

export const TabButton = styled.button`
  background: ${(props) => (props.active ? '#5b21b6' : 'transparent')};
  color: ${(props) => (props.active ? '#ffffff' : '#374151')};
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;

  &:hover {
    background: #ede9fe;
  }
`;

export const TabContent = styled.div`
  padding: 24px;
  color: #374151;
  font-size: 14px;
  line-height: 1.6;
`;
