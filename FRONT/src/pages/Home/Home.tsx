import styled from "styled-components";
import PostItem from "./components/PostItem/PostItem";
import PagingButton from "@_components/PagingButton/PagingButton";
import { useState } from "react";
import TagWrapper from "../../components/TagWrapper/TagWrapper";

const Wrapper = styled.main`
  max-width: 700px;
  padding: 30px 20px;
  margin: auto;
`

const PostItemContainer = styled.section`
  width: 100%;
`;

const PagingButtonWrapper = styled.section`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
`;

const ContentWrapper = styled.section`
  display: flex;
  flex-direction: row;
  gap: 20px;

  @media screen and (max-width: 768px) {
    flex-direction: column;
    gap: 30px;
  }
`;

const TagContainer = styled.section`
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;

  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const TagTitle = styled.h3`
  color: var(--text-color);
`;


function Home() {
  const [currentPage, setCurrentPage] = useState<number>(1);
  const tagList = ["Design Pattern", "Factory Pattern", "CI/CD", "DevOps", "Software Engineering","Design Pattern", "Factory Pattern", "CI/CD", "DevOps", "Software Engineering"];
  
  return (
    <Wrapper>
      <ContentWrapper>
        <PostItemContainer>
          <PostItem 
            title="뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목"
            createdAt="2024-11-12"
            content="요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.
            요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. "
            userIntro="어쩌구 저쩌구 개발자입니다."
            userName="도안탄히엔"
            />
          <PostItem 
            title="뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목"
            createdAt="2024-11-12"
            content="요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.
            요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. "
            userIntro="어쩌구 저쩌구 개발자입니다."
            userName="도안탄히엔"
            />
          <PostItem 
            title="뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목뭐시기 제목"
            createdAt="2024-11-12"
            content="요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.
            요거슨 내용 입니다. 요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다.요거슨 내용 입니다. "
            userIntro="어쩌구 저쩌구 개발자입니다."
            userName="도안탄히엔"
            />
        </PostItemContainer>
        <TagContainer>
          <TagTitle>#태그</TagTitle>
          <TagWrapper tagList={tagList} />
        </TagContainer>
      </ContentWrapper>
      <PagingButtonWrapper>
        <PagingButton totalPages={10} currentPage={currentPage} setCurrentPage={setCurrentPage} />
      </PagingButtonWrapper>
    </Wrapper>
  )
}

export default Home;