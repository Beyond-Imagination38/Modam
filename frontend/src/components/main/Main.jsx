import Header from "../common/Header";
import * as S from "./Main.style";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";
import 군주론 from "./군주론.jpg";
import 앵무새죽이기 from "./앵무새죽이기.jpg";
import 자아폭발 from "./자아폭발.jpg";

const ITEMS_PER_PAGE = 3;

export function Main() {
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [items, setItems] = useState([]);

  const data = [
    {
      postId: 1,
      userId: 10,
      title: "군주론",
      time: "2025-04-10 20:00",
      representativeImage: 군주론,
    },
    {
      postId: 2,
      userId: 20,
      title: "앵무새 죽이기",
      time: "2025-04-15 21:00",
      representativeImage: 앵무새죽이기,
    },
    {
      postId: 5,
      userId: 20,
      title: "자아폭발",
      time: "2025-04-13 16:00",
      representativeImage: 자아폭발,
    },
    {
      title: "참을 수 없는 존재의 가벼움",
      time: "2.11 8시",
      representativeImage: "https://picsum.photos/600/300?random=3",
    },

    {
      title: "왜 나는 너를 사랑하는가",
      time: "2.11 10시",
      representativeImage: "https://picsum.photos/600/300?random=4",
    },
    {
      title: "데미안",
      time: "2.16 8시",
      representativeImage: "https://picsum.photos/600/300?random=5",
    },
    {
      title: "죽음의 수용소에서",
      time: "2.15 8시",
      representativeImage: "https://picsum.photos/600/300?random=6",
    },
    {
      title: "싯다르타",
      time: "2.15 4시",
      representativeImage: "https://picsum.photos/600/300?random=7",
    },
    {
      title: "소크라테스 익스프레스",
      time: "2.11 6시",
      representativeImage: "https://picsum.photos/600/300?random=8",
    },
    {
      title: "소공녀",
      time: "2.11 8시",
      representativeImage: "https://picsum.photos/600/300?random=9",
    },
  ];

  useEffect(() => {
    const storedData = data;
    const storedPosts = JSON.parse(localStorage.getItem("posts")) || [];
    setItems([...storedPosts, ...storedData]);
  }, []);

  /*const fetchItems = async () => {
    try {
      const response = await fetchApi(API_URLS.posts, {
        method: "GET",
      });
  
      console.log("게시글 API 응답:", response); 
  
      if (response.status === 200 && response.data?.content) {
        setItems(response.data.content); 
      } else {
        console.error("게시글 데이터가 비어 있습니다:", response);
        setItems([]);
      }
    } catch (err) {
      console.error("게시글 불러오기 실패:", err);
    }
  };
  

  useEffect(() => {
    fetchItems();
  }, []);*/

  const filteredItems = items.filter((item) =>
    item.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const totalPages = Math.ceil(filteredItems.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const currentItems = filteredItems.slice(
    startIndex,
    startIndex + ITEMS_PER_PAGE
  );

  return (
    <S.Container>
      <Header />
      <S.SearchContainer>
        <S.SearchInput
          type="text"
          placeholder="모임명 검색"
          value={searchTerm}
          onChange={(e) => {
            setSearchTerm(e.target.value);
            setCurrentPage(1);
          }}
        />
        <S.SearchButton>🔍</S.SearchButton>
        <Link to="/Register">
          <S.RegisterButton>모임 등록하기</S.RegisterButton>
        </Link>
      </S.SearchContainer>

      <S.ProductGrid>
        {currentItems.length > 0 ? (
          currentItems.map(({ representativeImage, title, time, postId }) => (
            <Link
              to={`/post/${postId}`}
              key={postId}
              style={{ textDecoration: "none", color: "inherit" }}
            >
              <S.ProductCard key={postId}>
                <S.ImageContainer>
                  <S.ProductImage src={representativeImage} alt="상품 이미지" />
                </S.ImageContainer>
                <S.ProductTitle>{title}</S.ProductTitle>
                <S.ProductTime>{time}</S.ProductTime>
              </S.ProductCard>
            </Link>
          ))
        ) : (
          <S.NoResults>검색 결과가 없습니다.</S.NoResults>
        )}
      </S.ProductGrid>

      {/* 페이지네이션 */}
      <S.Pagination>
        <S.PageButton
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
        >
          이전
        </S.PageButton>{" "}
        {Array.from({ length: totalPages }, (_, i) => (
          <S.PageButton
            key={i}
            onClick={() => setCurrentPage(i + 1)}
            $active={currentPage === i + 1}
          >
            {i + 1}
          </S.PageButton>
        ))}
        <S.PageButton
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages}
        >
          다음
        </S.PageButton>
      </S.Pagination>
    </S.Container>
  );
}
