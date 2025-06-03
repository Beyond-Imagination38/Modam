import Header from "../common/Header";
import * as S from "./Main.style";
import { Link,useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

const ITEMS_PER_PAGE = 3;

export function Main() {
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [items, setItems] = useState([]);
  const [activeCategory, setActiveCategory] = useState("진행 중");
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [user, setUser] = useState(null); 

  const navigate = useNavigate();

  const toggleSidebar = () => {
    setIsSidebarOpen((prev) => !prev);
  };
  /*
  const data = [
    {
      postId: 1,
      userId: 20,
      title: "1984",
      time: "2025-04-10 20:00",
      representativeImage: "/assets/images/1984.jpg",
      category: "완료",
    },
    {
      postId: 2,
      userId: 20,
      title: "자아폭발",
      time: "2025-04-13 16:00",
      representativeImage: "/assets/images/TheFall.jpg",
      category: "좋아요",
    },
    {
      postId: 3,
      title: "참을 수 없는 존재의 가벼움",
      time: "2025-04-11 08:00",
      representativeImage: "/assets/images/Unbearable.jpg",
      category: "진행 중",
    },
    {
      postId: 4,
      title: "앵무새 죽이기",
      time: "2025-04-11 10:00",
      representativeImage: "/assets/images/mockingbird.jpg",
      category: "좋아요",
    },
    {
      postId: 5,
      title: "데미안",
      time: "2025-04-16 08:00",
      representativeImage: "/assets/images/demian.jpg",
      category: "완료",
    },
    {
      postId: 6,
      title: "죽음의 수용소에서",
      time: "2025-04-15 08:00",
      representativeImage: "/assets/images/searchfor.jpg",
      category: "진행 중",
    },
    {
      postId: 7,
      title: "싯다르타",
      time: "2025-04-15 04:00",
      representativeImage: "/assets/images/Sidd.jpg",
      category: "완료",
    },
    {
      postId: 8,
      title: "소크라테스 익스프레스",
      time: "2025-04-11 06:00",
      representativeImage: "/assets/images/socra.jpg",
      category: "좋아요",
    },
    {
      postId: 9,
      title: "구의 증명",
      time: "2025-04-11 08:00",
      representativeImage: "/assets/images/goo.jpg",
      category: "진행 중",
    },
  ];
  

  useEffect(() => {
    const storedPosts = JSON.parse(localStorage.getItem("posts")) || [];

    const combined = [...storedPosts, ...data];
    const unique = Array.from(
      new Map(combined.map((item) => [item.postId || item.title, item])).values()
    );

    setItems(unique);
  }, [activeCategory, searchTerm, currentPage]);
  */

  useEffect(() => {
    const fetchOngoingClubs = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const userId = user?.id;

        if (!userId) {
          console.error("로그인된 사용자 정보를 찾을 수 없습니다.");
          return;
        }

        const response = await fetch(`http://localhost:8080/api/bookclubs/my/ongoing?userId=${userId}`, {
          method: "GET",
        });

        if (!response.ok) {
          throw new Error(`HTTP 오류! 상태 코드: ${response.status}`);
        }

        const data = await response.json();

        const mapped = data.map((item, index) => ({
          postId: index + 1, // 고유 ID 없으니 임시
          userId: userId,
          title: item.bookTitle,
          time: item.meetingDateTime,
          representativeImage: item.coverImage,
          category: "진행 중",
        }));

        setItems(mapped);
      } catch (error) {
        console.error("독서 모임 데이터를 불러오지 못했습니다:", error);
        setItems([]);
      }
    };

    fetchOngoingClubs();
  }, []);

  
  const filteredItems = items.filter(
  (item) =>
    item.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
    item.category === activeCategory
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
      <S.HamburgerButton onClick={toggleSidebar}>☰</S.HamburgerButton>
      <S.Layout>
        {isSidebarOpen && (
          <S.SideMenu>
            <S.MenuItem
              $active={activeCategory === "PENDING"}
              onClick={() => {
                setActiveCategory("PENDING");
                setCurrentPage(1);
              }}
            >
              전체 독서 모임
            </S.MenuItem>
            <S.MenuItem
              $active={activeCategory === "COMPLETED)"}
              onClick={() => {
                setActiveCategory("COMPLETED)");
                setCurrentPage(1);
              }}
            >
              완료된 독서모임
            </S.MenuItem>
            <S.MenuItem
              $active={activeCategory === "ONGOING"}
              onClick={() => {
                setActiveCategory("ONGOING");
                setCurrentPage(1);
              }}
            >
              진행 중인 독서모임
            </S.MenuItem>
              <S.SideMenuFooter>
                <S.Button
                  onClick={(e) => {
                    e.preventDefault();
                    const confirmLogout = window.confirm("로그아웃 하시겠습니까?");
                    if (confirmLogout) {
                      localStorage.removeItem("user");
                      navigate("/");
                    }
                  }}
                >
                로그아웃
              </S.Button>
            </S.SideMenuFooter>

          </S.SideMenu>
        )}

        <S.ContentArea>
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
              currentItems.map(
                ({ representativeImage, title, time, postId, category }, index) => {
                  return (
                    <Link
                      to={`/detail/${postId}`}
                      key={postId}
                      style={{ textDecoration: "none", color: "inherit" }}
                    >
                      <S.ProductCard>
                        <S.ImageContainer>
                          <S.ProductImage
                            src={representativeImage}
                            alt="도서 이미지"
                          />
                        </S.ImageContainer>
                        <S.ProductTitle>{title}</S.ProductTitle>
                        <S.ProductTime>{time}</S.ProductTime>
                      </S.ProductCard>
                    </Link>
                  );
                }
              )
            ) : (
              <S.NoResults>검색 결과가 없습니다.</S.NoResults>
            )}

          </S.ProductGrid>

          <S.Pagination>
            <S.PageButton
              onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
              disabled={currentPage === 1}
            >
              이전
            </S.PageButton>
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
        </S.ContentArea>
      </S.Layout>
    </S.Container>
  );
}