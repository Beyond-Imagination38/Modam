import Header from "../common/Header";
import * as S from "./Main.style";
import { Link,useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { API_URLS } from "../../consts";
import { fetchApi } from "../../utils";

export function Main() {
  const [searchTerm, setSearchTerm] = useState("");
  const [items, setItems] = useState([]);
  const [activeCategory, setActiveCategory] = useState("PENDING");
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const navigate = useNavigate();

  const toggleSidebar = () => {
    setIsSidebarOpen((prev) => !prev);
  };

  useEffect(() => {
    const fetchClubs = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const userId = user?.id;

        let url = "";
        if (activeCategory === "PENDING") {
          url = API_URLS.allBookclubs;
        } else if (activeCategory === "COMPLETED") {
          if (!userId) throw new Error("로그인된 사용자 정보를 찾을 수 없습니다.");
          url = API_URLS.myCompleted(userId);
        } else if (activeCategory === "ONGOING") {
          if (!userId) throw new Error("로그인된 사용자 정보를 찾을 수 없습니다.");
          url = API_URLS.myOngoing(userId);
        }

        const { status, data } = await fetchApi(url, { method: "GET" });

        if (status !== 200) {
          throw new Error(`서버 응답 실패: ${status}`);
        }

        const mapped = data.map((item, index) => ({
          postId: index,
          title: item.bookTitle,
          time: item.meetingDateTime,
          representativeImage: item.coverImage,
          participants: item.participants,
          description: item.clubDescription,
        }));

        console.log("서버 응답 데이터:", data);
        setItems(mapped);
      } catch (error) {
        console.error("독서 모임 데이터를 불러오지 못했습니다:", error);
        alert("서버에서 독서 모임 정보를 불러오지 못했습니다. 나중에 다시 시도해주세요.");
        setItems([]);
      }
    };

    fetchClubs();
  }, [activeCategory]);

  
  const filteredItems = items.filter(
    (item) =>
      item.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.description?.toLowerCase().includes(searchTerm.toLowerCase())
  );
  
  const currentItems = filteredItems;
  
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
              }}
            >
              전체 독서 모임
            </S.MenuItem>
            <S.MenuItem
              $active={activeCategory === "COMPLETED"}
              onClick={() => {
                setActiveCategory("COMPLETED");
              }}
            >
              완료된 독서모임
            </S.MenuItem>
            <S.MenuItem
              $active={activeCategory === "ONGOING"}
              onClick={() => {
                setActiveCategory("ONGOING");
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
        </S.ContentArea>
      </S.Layout>
    </S.Container>
  );
}