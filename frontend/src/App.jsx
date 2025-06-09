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
  Detail,
  MyGroups,
  Completed,
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
        <Route path="/mygroups" element={<MyGroups />} />
        <Route path="/register" element={<Register />} />
        <Route path="/register/:postId" element={<Register />} />
        <Route path="/chat" element={<Chat />} />         {/* soo: 일반 채팅 */}
        <Route path="/chat/:clubId" element={<Chat />} />        {/* soo: /추가: clubId 있는 채팅 */}
        <Route path="/bookreport" element={<Bookreport />} />
        <Route path="/detail/:clubId" element={<Detail />} />
        <Route path="/completed" element={<Completed />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
