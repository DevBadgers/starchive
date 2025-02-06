import { Link } from 'react-router-dom';
import { 
  Content, 
  HashTagContainer, 
  SubInfo, 
  TagContainer, 
  Title, 
  UserImage, 
  UserInfoWrapper, 
  UserName, 
  UserNameWrapper, 
  UserProfileWrapper, 
  Wrapper,
  ContentContainer } 
  from './PostItem.style';
import { Tag } from '../../../../components/TagWrapper/TagWrapper.style';
import { useNavigate } from 'react-router-dom';

interface PostItemProps {
  postId: number,
  title: string,
  content: string,
  createdAt: string,
  userName: string,
  userIntro: string,
  categoryHier:{
      categoryId: number,
      categoryName: string
  }[],
  hashTags: {
    hashTagId: number,
    name: string
  }[]
}

function PostItem({ postId, title, content, createdAt, userName, userIntro, categoryHier, hashTags }: PostItemProps) {
  const navigate = useNavigate();
  return (
    <Wrapper>
      <TagContainer>
        {
          categoryHier?.map(({ categoryId, categoryName }, i) => 
            <div key={i}>
              {i !== 0 ? <span> &gt; </span> : null}
              <Link to={`/1?categoryId=${categoryId}`}>{categoryName}</Link>
            </div>
          )
        }
      </TagContainer>
      <ContentContainer onClick={() => navigate(`/post/${postId}`)}>
        <Title>{ title }</Title>
        <UserProfileWrapper>
          <UserImage>
            <img src="https://avatars.githubusercontent.com/u/95044821?v=4" alt="profile" width="100%" />
          </UserImage>
          <UserInfoWrapper>
            <UserNameWrapper>
              <UserName>{ userName }</UserName>
              <SubInfo>{ createdAt.split("T")[0] }</SubInfo>
            </UserNameWrapper>
            <SubInfo>{ userIntro }</SubInfo>
          </UserInfoWrapper>
        </UserProfileWrapper>
        <Content>{ content }</Content>
      </ContentContainer>
      <HashTagContainer>
        {hashTags.map(tag => (
          <Tag key={tag.hashTagId} $isSelected={false}>{ '#' + tag.name }</Tag>
        ))}
      </HashTagContainer>
    </Wrapper>
  )
}

export default PostItem;