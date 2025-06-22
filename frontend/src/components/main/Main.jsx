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
    console.log("useEffect 실행됨", activeCategory);

    const fetchClubs = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const userId = user?.id;
        console.log("user 확인:", user);

        let url = "";

        if (activeCategory === "PENDING") {
          url = API_URLS.allBookclubs;
        } else if (activeCategory === "COMPLETED") {
          if (!userId) {
            alert("로그인 후 이용할 수 있는 메뉴입니다.");
            return;
          }
          url = API_URLS.myCompleted(userId);
        } else if (activeCategory === "ONGOING") {
          if (!userId) {
            alert("로그인 후 이용할 수 있는 메뉴입니다.");
            return;
          }
          url = API_URLS.myOngoing(userId);
        }

        console.log("불러올 URL:", url);

        const { status, data } = await fetchApi(url, { method: "GET" });
        console.log("API 호출 완료:", status, data);

        if (status !== 200) {
          throw new Error(`서버 응답 실패: ${status}`);
        }

        const mapped = data.map((item, index) => ({
          clubId: item.clubId,
          title: item.bookTitle,
          time: item.meetingDateTime,
          representativeImage: item.coverImage,
          participants: item.participants,
          description: item.clubDescription,
        }));

        setItems(mapped);
      } catch (error) {
        console.error("에러 발생:", error);
        alert("서버에서 독서 모임 정보를 불러오지 못했습니다.");
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
              진행 중인 내 독서모임
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
                ({ representativeImage, title, time, clubId }, index) => {
                  return (
                    <div
                      key={clubId}
                      style={{ textDecoration: "none", color: "inherit", cursor: "pointer" }}
                      onClick={() => {
                        const user = JSON.parse(localStorage.getItem("user"));
                        if (!user) {
                          alert("로그인 후 이용할 수 있습니다.");
                          return;
                        }
                        navigate(`/detail/${clubId}`);
                      }}
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
                    </div>
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