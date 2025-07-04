import styled from "styled-components";

export const Container = styled.div`
  min-height: 100vh;
  background-color: #f3f4f6;
    position: relative;
`;

export const Button = styled.button`
  background-color: white;
  color: #65558f;
  padding: 8px 16px;
  border: 2px solid white;
  cursor: pointer;
`;

export const SearchContainer = styled.div`
  display: flex;
  justify-content: center;
  margin: 16px 0;
  gap: 12px;
`;

export const SearchInput = styled.input`
  width: 40%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 24px;
  font-size: 14px;
  color: #555;
`;

export const SearchButton = styled.button`
  padding: 12px;
  background-color: #e5e7eb;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  font-size: 16px;

  &:hover {
    background-color: #919191;
  }
`;

export const ProductGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 24px;
  padding: 0 24px;
  max-width: 600px;
  margin: 0 auto;
`;

export const ProductCard = styled.div`
  background: white;
  padding: 12px;
  border-radius: 12px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  position: relative;
  

  &:hover {
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);

    img {
      transform: scale(1.2);
      transition: transform 0.3s ease-in-out;
    }
  }
`;

export const HamburgerButton = styled.button`
  position: absolute;
  top: 16px;
  left: 16px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  z-index: 1000;
`;

export const Layout = styled.div`
  display: flex;
  min-height: 90vh;
`;

export const SideMenu = styled.div`
  width: 200px;
  background-color: #fff;
  padding: 24px;s
  border-right: 1px solid #ddd;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 170vh;
`;

export const SideMenuFooter = styled.div`
  margin-top: auto;
`;
export const MenuItem = styled.div`
  padding: 12px 8px;
  font-size: 14px;
  cursor: pointer;
  color: ${({ $active }) => ($active ? "#65558f" : "#333")};
  font-weight: ${({ $active }) => ($active ? "bold" : "normal")};
  background-color: ${({ $active }) =>
    $active ? "#f0f0f0" : "transparent"};
  border-radius: 4px;
  margin-bottom: 8px;

  &:hover {
    background-color: #eee;
  }
`;

export const ContentArea = styled.div`
  flex: 1;
  padding: 24px;
`;


export const ImageContainer = styled.div`
  overflow: hidden;
  width: 100%;
  height: 160px;
  background-color: #d1d5db;
  border-radius: 8px;
`;

export const ProductImage = styled.img`
  width: 100%;
  height: 200px;
  background-color: #d1d5db;
  border-radius: 8px;
`;

export const ProductTitle = styled.p`
  margin-top: 8px;
  font-size: 14px;
  color: #333;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

export const ProductTime = styled.p`
  font-size: 16px;
  font-weight: bold;
`;

export const RegisterButton = styled.button`
  bottom: 24px;
  left: 24px;
  color: #65558f;
  padding: 12px 16px;
  border-radius: 8px;
  font-size: 14px;
  cursor: pointer;
  margin-left: 24px;
  font-weight: 600;
  border: none;
  transition: all 0.3s ease;

  &:hover {
    color: #4a3c6a;
    text-decoration: underline;
  }

  &:active {
    transform: translateY(1px);
  }
`;

export const NoResults = styled.p`
  text-align: center;
  font-size: 16px;
  color: gray;
  margin-top: 20px;
`;
