import { PostContainer, MainContent, ButtonContainer } from "./Post.style";
import {
  TagContainer,
  Title,
  UserProfileWrapper,
  UserImage,
  UserInfoWrapper,
  UserNameWrapper,
  UserName,
  SubInfo,
  Content,
  HashTagContainer,
} from "../Home/components/PostItem/PostItem.style";
import { Tag } from "@_components/TagWrapper/TagWrapper.style";
import { Link } from "react-router-dom";
import Button from "@_components/Button/Button";

function Post() {
  return (
    <PostContainer>
      <ButtonContainer>
        <Button
          content="수정"
          type="Primary"
          handleButtonClick={() => console.log("Edit")}
        />
        <Button
          content="삭제"
          type="Primary"
          handleButtonClick={() => console.log("Delete")}
        />
      </ButtonContainer>
      <MainContent>
        <TagContainer>
          {categoryHier?.map(({ categoryId, categoryName }, i) => (
            <>
              {i !== 0 ? <span> &gt; </span> : null}
              <Link to={`/1?categoryId=${categoryId}`}>{categoryName}</Link>
            </>
          ))}
        </TagContainer>
        <Title>{title}</Title>
        <UserProfileWrapper>
          <UserImage>
            <img
              src="https://avatars.githubusercontent.com/u/95044821?v=4"
              alt="profile"
              width="100%"
            />
          </UserImage>
          <UserInfoWrapper>
            <UserNameWrapper>
              <UserName>{author}</UserName>
              <SubInfo>{createdAt}</SubInfo>
            </UserNameWrapper>
            <SubInfo>어쩌구 저쩌구 개발자입니다.</SubInfo>
          </UserInfoWrapper>
        </UserProfileWrapper>
        <Content>{content}</Content>
        <HashTagContainer>
          {hashTags.map((tag) => (
            <Tag key={tag.hashTagId} $isSelected={false}>
              {"#" + tag.name}
            </Tag>
          ))}
        </HashTagContainer>
      </MainContent>
    </PostContainer>
  );
}

export default Post;
