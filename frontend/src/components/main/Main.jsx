import Header from "../common/Header";
import * as S from "./Main.style";
import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

const ITEMS_PER_PAGE = 8; // 한 페이지당 표시할 아이템 개수

export function Main() {
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [items, setItems] = useState([]);

  const data = [
    {
      postId: 1,
      userId: 10,
      title: "세이노의 가르침",
      time: "2.11 8시",
      representativeImage: "https://picsum.photos/600/300",
    },
    {
      postId: 4,
      userId: 20,
      title: "앵무새 죽이기",
      time: "2.15 9시",
      representativeImage: "https://picsum.photos/600/100",
    },
    {
      postId: 5,
      userId: 20,
      title: "자아폭발",
      time: "2.13 4시",
      representativeImage: "https://picsum.photos/600/200",
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
    setItems(data);
  }, []);

  /*const fetchItems = async () => {
    try {
      const response = await fetchApi(API_URLS.posts, {
        method: "GET",
      });
  
      console.log("📌 게시글 API 응답:", response); // 응답 확인용 로그 추가
  
      if (response.status === 200 && response.data?.content) {
        setItems(response.data.content); // ✅ 올바른 데이터 경로로 설정
      } else {
        console.error("🚨 게시글 데이터가 비어 있습니다:", response);
        setItems([]);
      }
    } catch (err) {
      console.error("🚨 게시글 불러오기 실패:", err);
    }
  };
  

  useEffect(() => {
    fetchItems();
  }, []);*/

  // 검색 필터 적용
  const filteredItems = items.filter((item) =>
    item.title.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // 현재 페이지의 아이템 계산
  const totalPages = Math.ceil(filteredItems.length / ITEMS_PER_PAGE);
  //전체 페이지 수 계산 = 현재 필터링된 항목의 총 개수/ 한 페이지에 표시할 항목 수 - 나눗셈 결과 올림
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE; //현재 페이지에서 시작할 데이터의 인덱스
  const currentItems = filteredItems.slice(
    startIndex,
    startIndex + ITEMS_PER_PAGE
  ); //현재 페이지의 데이터만 추출

  return (
    <S.Container>
      <Header />
      <S.SearchContainer>
        <S.SearchInput
          type="text"
          placeholder="상품명 검색"
          value={searchTerm}
          onChange={(e) => {
            setSearchTerm(e.target.value);
            setCurrentPage(1); // 검색 시 첫 페이지로 이동
          }}
        />
        <S.SearchButton>🔍</S.SearchButton>
        <Link to="/Register">
          <S.RegisterButton>모임 등록하기</S.RegisterButton>
        </Link>

        <Link to="/Chat">
          <S.RegisterButton>채팅방 입장</S.RegisterButton>
        </Link>
      </S.SearchContainer>

      <S.ProductGrid>
        {/* currentItems 배열이 비어 있지 않으면 상품 목록을 출력 */}
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
        {/* 다음 버튼 */}
        <S.PageButton
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
        >
          이전
        </S.PageButton>{" "}
        {/*첫 페이지일 때 비활성화 */}
        {/* 페이지 번호 버튼들 (totalPages 만큼 생성) */}
        {Array.from({ length: totalPages }, (_, i) => (
          <S.PageButton
            key={i} // 각 버튼에 고유 키 부여
            onClick={() => setCurrentPage(i + 1)} // 페이지 번호 클릭 시 해당 페이지로 이동
            $active={currentPage === i + 1} // 현재 페이지 강조
          >
            {i + 1} {/* 페이지 번호 표시 */}
          </S.PageButton>
        ))}
        {/* 다음 버튼 */}
        <S.PageButton
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages} // 마지막 페이지일 때 비활성화
        >
          다음
        </S.PageButton>
      </S.Pagination>
    </S.Container>
  );
}
