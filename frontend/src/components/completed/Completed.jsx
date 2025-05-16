import { useState } from "react";
import * as S from "./Completed.style";
import Header from "../common/Header";

export function Completed() {
  const [activeTab, setActiveTab] = useState("요약된 내용");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [selectedTopic, setSelectedTopic] = useState("주제 1");
  
  const summaryTopics = {
    "주제 1": "논의에서는 1984 소설 속 '빅 브라더' 체제가 현대의 스마트폰, CCTV, AI 감시, 대규모 데이터 수집 등과 연결될 수 있음을 토론하였다. 이는 현대 사회에서의 프라이버시 침해와 자유 억압 가능성을 경고하며, 참여적 감시를 통한 정보 제공에 대한 경각심과 민주주의를 지키기 위한 지속적 노력의 필요성을 강조하였다.",
    "주제 2": "1984에서는 '이중사고', 과거 기록의 조작, 텔레스크린 등을 통해 개인의 사고가 억압되고 있다는 논의가 이루어졌다. 이는 현대 사회의 정보 조작, 심리적 통제, 가짜 뉴스와 역사 왜곡 문제, 디지털 감시 기술과 여론 조작 등과 연결될 수 있다는 의견이 제시되었다.",
    "주제 3": "1984에서는 감시 체제, '뉴스피크' 언어 통제, 과거 기록의 조작 등을 통해 인간의 본성과 욕망이 억압된다는 논의가 진행되었다. 이를 통해 현대 사회의 프라이버시 침해, 정보와 언론의 조작, 가짜 뉴스와 역사 왜곡의 위험성에 대한 경각심과 비판적 사고의 중요성, 그리고 자유와 인간성을 회복하기 위한 지속적인 노력의 필요성이 강조되었다.",
    "주제 4": "군주의 이상적인 자질에 대한 의견들이 활발히 공유되었다.",
  };

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
    "독후감": <p>조지 오웰의 『1984』는 전체주의 사회의 극단적인 미래를 그린 디스토피아 소설로, 읽는 내내 섬뜩한 현실감과 무력감을 안겨주었다. ‘빅 브라더’가 모든 것을 감시하고 통제하는 사회에서는 사적인 공간조차 존재하지 않으며, 인간의 사고마저 국가가 지배한다. 주인공 윈스턴은 체제에 저항하려 하지만, 결국 자신의 사랑과 생각까지도 무너지고 만다.

이 소설에서 가장 충격적인 부분은 '이중사고(Doublethink)'와 '신어(Newspeak)'라는 개념이다. 국가가 언어와 사고를 통제함으로써 진실 자체를 재정의하는 모습은, 현재 우리가 살아가는 시대에도 경고를 주는 것처럼 느껴진다. 가짜 뉴스, 여론 조작, 감시 기술 등 현대 사회의 다양한 문제들이 소설 속 현실과 맞닿아 있기에, 1949년에 쓰인 이 책이 여전히 유효하다는 점이 놀랍다.

무엇보다도 이 책은 자유의 소중함과 인간성의 의미를 다시금 생각하게 만든다. 윈스턴의 저항은 실패로 끝났지만, 그 과정에서 우리가 지켜야 할 가치들이 무엇인지 독자에게 강하게 전달된다. 『1984』는 단순한 소설을 넘어, 정치적 경각심과 윤리적 질문을 던지는 깊이 있는 작품이다. 개인과 진실이 억압받는 세상에서 우리가 어떤 선택을 해야 할지를 끊임없이 되묻게 만든다.</p>,
    "메모": <p>전체주의의 위험성: 감시와 통제를 통해 개인의 자유가 철저히 억압된다. 지금 현실에서도 CCTV, 빅데이터, SNS 감시 등을 통해 유사한 통제가 가능해지고 있다.</p>,
  };


  return (
    <>
    <Header />
    <S.Container>
      <S.Title>1984</S.Title>
      <S.Subtitle>by 조지 오웰 | 2025년 04월 10일 목요일 오후 20시 00분</S.Subtitle>
      <S.Card>
        <S.TabHeader>
          {Object.keys(tabContents).map((tab) => (
            <S.TabButton
              key={tab}
              active={activeTab === tab}
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
