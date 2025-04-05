import { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as S from "./MyGroups.style";
import Header from "../common/Header";

export function MyGroups() {
  const navigate = useNavigate();

  const [myPosts, setMyPosts] = useState([
    { id: 1, title: "독서 모임 1", representativeImage: "" },
    { id: 2, title: "독서 모임 2", representativeImage: "" },
    { id: 3, title: "독서 모임 3", representativeImage: "" },
  ]);

  const [selectedPosts, setSelectedPosts] = useState([]);

  const handleSelect = (id) => {
    setSelectedPosts((prev) =>
      prev.includes(id) ? prev.filter((postId) => postId !== id) : [...prev, id]
    );
  };

  const handleSelectAll = () => {
    setSelectedPosts(
      selectedPosts.length === myPosts.length
        ? []
        : myPosts.map((post) => post.id)
    );
  };

  const handleDeleteSelected = () => {
    if (selectedPosts.length === 0) return alert("선택된 게시글이 없습니다.");
    if (!window.confirm("선택한 게시글을 삭제하시겠습니까?")) return;
    setMyPosts(myPosts.filter((post) => !selectedPosts.includes(post.id)));
    setSelectedPosts([]);
  };

  const handleDeleteAll = () => {
    if (!window.confirm("전체 게시글을 삭제하시겠습니까?")) return;
    setMyPosts([]);
    setSelectedPosts([]);
  };

  const handleEdit = () => {
    if (selectedPosts.length !== 1)
      return alert("수정할 게시글을 하나만 선택해주세요.");
    alert("수정 페이지로 이동합니다.");
    navigate(`/register/${selectedPosts[0]}`);
  };

  return (
    <S.Container>
      <Header />
      <S.Title>내 독서모임</S.Title>
      <S.ButtonContainer>
        <S.Button onClick={handleDeleteSelected}>선택삭제</S.Button>
        <S.Button onClick={handleDeleteAll}>전체삭제</S.Button>
        <S.Button onClick={handleEdit} disabled={selectedPosts.length !== 1}>
          독서 모임 정보 수정
        </S.Button>
      </S.ButtonContainer>

      <S.PostGrid>
        {myPosts.map((post) => (
          <S.PostCard key={post.id}>
            <S.CheckboxContainer>
              <input
                type="checkbox"
                checked={selectedPosts.includes(post.id)}
                onChange={() => handleSelect(post.id)}
              />
            </S.CheckboxContainer>
            <S.PostImage />
            <S.PostTitle>{post.title}</S.PostTitle>
          </S.PostCard>
        ))}
      </S.PostGrid>

      <S.SelectAllContainer>
        <S.SelectAllButton onClick={handleSelectAll}>
          {selectedPosts.length === myPosts.length ? "전체해제" : "전체선택"}
        </S.SelectAllButton>
      </S.SelectAllContainer>
    </S.Container>
  );
}
