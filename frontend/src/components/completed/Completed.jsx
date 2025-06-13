import { useEffect, useState } from "react";
import * as S from "./Completed.style";
import Header from "../common/Header";
import { useParams } from "react-router-dom";
import { API_URLS } from "../../consts";

export function Completed() {
  const [activeTab, setActiveTab] = useState("요약된 내용");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [selectedTopic, setSelectedTopic] = useState("주제 1");
  const [bookData, setBookData] = useState(null);

  const { clubId } = useParams();

    useEffect(() => {
    async function fetchData() {
      try {
        const response = await fetch(API_URLS.completedDetail(clubId));
        if (!response.ok) {
          throw new Error("네트워크 응답에 문제가 있습니다");
        }
        const data = await response.json();
        setBookData(data);
        if (data.summaries.length > 0) {
          setSelectedTopic(data.summaries[0].topic);
        }
      } catch (error) {
        console.error("데이터 불러오기 실패:", error);
      }
    }

    fetchData();
  }, [clubId]);

  const summaryTopics = bookData
    ? bookData.summaries.reduce((acc, cur) => {
        acc[cur.topic] = cur.content;
        return acc;
      }, {})
    : {};

  const tabContents = {
    "요약된 내용": (
      <>
        <S.DropdownContainer>
          <S.DropdownHeader onClick={() => setDropdownOpen(!dropdownOpen)}>
            {selectedTopic}
          </S.DropdownHeader>
          {dropdownOpen && (
            <S.DropdownList>
              {Object.keys(summaryTopics).map((topic) => (
                <S.DropdownItem
                  key={topic}
                  onClick={() => {
                    setSelectedTopic(topic);
                    setDropdownOpen(false);
                  }}
                >
                  {topic}
                </S.DropdownItem>
              ))}
            </S.DropdownList>
          )}
        </S.DropdownContainer>
        <p>{summaryTopics[selectedTopic]}</p>
      </>
    ),
    "독후감": (
      <div>
        {bookData?.readingNotes?.map((note, index) => (
          <p key={index}>{note.content}</p>
        ))}
      </div>
    ),
    "메모": (
      <div>
        {bookData?.memos?.map((memo, index) => (
          <p key={index}>{memo.content}</p>
        ))}
      </div>
    ),
  };


  return (
    <>
      <Header />
      <S.Container>
        <S.Title>{bookData?.bookTitle || "도서 제목"}</S.Title>
        <S.Subtitle>
          by {bookData?.bookAuthor || "저자 미상"} |{" "}
          {bookData?.meetingDate || "날짜 미정"}
        </S.Subtitle>
        <S.Card>
          <S.TabHeader>
            {Object.keys(tabContents).map((tab) => (
              <S.TabButton
                key={tab}
                $active={activeTab === tab} 
                onClick={() => setActiveTab(tab)}
              >
                {tab}
              </S.TabButton>
            ))}
          </S.TabHeader>
          <S.TabContent>{tabContents[activeTab]}</S.TabContent>
        </S.Card>
      </S.Container>
    </>
  );
}
