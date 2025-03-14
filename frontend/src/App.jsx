import { BrowserRouter, Routes, Route } from "react-router-dom";
import {
  Main,
  Home,
  Login,
  Signup,
  MyPage,
  Register,
  Chat,
  Bookreport,
} from "./components";
import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
  body {
    font-family: 'Inter', sans-serif;
    font-size: 12px;
    font-weight: 400;
    text-decoration: none;
    margin: 0;
  }
`;

function App() {
  return (
    <BrowserRouter>
      <GlobalStyle />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/main" element={<Main />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/register" element={<Register />} />
        <Route path="/register/:postId" element={<Register />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="/bookreport" element={<Bookreport />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
