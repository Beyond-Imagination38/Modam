import { useState } from "react";
import * as S from "./Completed.style";
import Header from "../common/Header";

export function Completed() {
  const [activeTab, setActiveTab] = useState("요약된 내용");

  const tabContents = {
    "요약된 내용": (
      <p>
        논의에서는 군주의 권력 유지에서 도덕보다 실용성을 중시해야 한다는 마키아벨리의 주장에 대해 찬반이 나뉘었다. 또한,
        두려움과 존경 중 무엇이 더 효과적인지, 현대 정치에서도 적용될 수 있는지에 대한 논의가 이루어졌다.
      </p>
    ),
    "독후감": <p>독후감 내용입니다.</p>,
    "메모": <p>메모 내용입니다.</p>,
  };

  return (
    <>
    <Header />
    <S.Container>
      <S.Title>군주론</S.Title>
      <S.Subtitle>by 마키아벨리 | 2025년 02월 13일 목요일 오후 07시 30분</S.Subtitle>

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
