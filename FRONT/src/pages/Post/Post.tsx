import { PostContainer, MainContent, ButtonContainer, Line, PostHashTagContainer, Content } from "./Post.style";
import {
  TagContainer,
  Title,
  UserProfileWrapper,
  UserImage,
  UserInfoWrapper,
  UserNameWrapper,
  UserName,
  SubInfo,
} from "../Home/components/PostItem/PostItem.style";
import { Tag } from "@_components/TagWrapper/TagWrapper.style";
import { Link } from "react-router-dom";
import Button from "@_components/Button/Button";
import MarkdownRenderer from "@_components/MarkdownRenderer/MarkdownRenderer";
import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { fetchPost } from '@_services/postApi';
import { ApiResponse } from '../../services/api';
import { PostParams } from '../../types/post';

function Post() {
  const { postId } = useParams();

  const {data} = useQuery<ApiResponse<PostParams>>({
    queryKey: ['post', postId],
    queryFn: () => fetchPost(Number(postId)),
  });

  const { title, author, createdAt, content, hashTags, categoryHier } = data?.data || {};

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
            <span key={categoryId}>
              {i !== 0 ? <span> &gt; </span> : null}
              <Link to={`/1?categoryId=${categoryId}`}>{categoryName}</Link>
            </span>
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
              <SubInfo>{createdAt?.split("T")[0]}</SubInfo>
            </UserNameWrapper>
            <SubInfo>어쩌구 저쩌구 개발자입니다.</SubInfo>
          </UserInfoWrapper>
        </UserProfileWrapper>
        <Line/>
        <Content><MarkdownRenderer markdown={content ?? ""} /></Content>
        <PostHashTagContainer>
          {hashTags?.map((tag) => (
            <Tag key={tag.hashTagId} $isSelected={false}>
              {"#" + tag.name}
            </Tag>
          ))}
        </PostHashTagContainer>
      </MainContent>
    </PostContainer>
  );
}

export default Post;
