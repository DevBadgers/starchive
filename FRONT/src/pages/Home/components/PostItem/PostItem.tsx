import { Link } from 'react-router-dom';
import { 
  Content, 
  SubInfo, 
  TagContainer, 
  Title, 
  UserImage, 
  UserInfoWrapper, 
  UserName, 
  UserNameWrapper, 
  UserProfileWrapper, 
  Wrapper } 
  from './PostItem.style';
import TagWrapper from '@_components/TagWrapper/TagWrapper';

interface PostItemProps {
  title: string,
  content: string,
  createdAt: string,
  userName: string,
  userIntro: string,
  tagList: string[]
}

function PostItem({ title, content, createdAt, userName, userIntro, tagList }: PostItemProps) {
  return (
    <Wrapper>
      <TagContainer>
        Software Engineering &gt; CI/CD
      </TagContainer>
      <Link to="#">
        <Title>{ title }</Title>
      </Link>
      <UserProfileWrapper>
        <UserImage>
          <img src="https://avatars.githubusercontent.com/u/95044821?v=4" alt="profile" width="100%" />
        </UserImage>
        <UserInfoWrapper>
          <UserNameWrapper>
            <UserName>{ userName }</UserName>
            <SubInfo>{ createdAt }</SubInfo>
          </UserNameWrapper>
          <SubInfo>{ userIntro }</SubInfo>
        </UserInfoWrapper>
      </UserProfileWrapper>
      <Link to="#">
        <Content>{ content }</Content>
      </Link>
      <TagWrapper tagList={tagList} />
    </Wrapper>
  )
}

export default PostItem;