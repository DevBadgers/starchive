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
  Wrapper } 
  from './PostItem.style';
import { Tag } from '../../../../components/TagWrapper/TagWrapper.style';

interface PostItemProps {
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

function PostItem({ title, content, createdAt, userName, userIntro, categoryHier, hashTags }: PostItemProps) {
  return (
    <Wrapper>
      <TagContainer>
        {
          categoryHier?.map(({ categoryId, categoryName }, i) => 
            <>
              {i !== 0 ? <span> &gt; </span> : null}
              <Link to={`/1?categoryId=${categoryId}`}>{categoryName}</Link>
            </>
          )
        }
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
      <HashTagContainer>
        {hashTags.map(tag => (
          <Tag key={tag.hashTagId} $isSelected={false}>{ '#' + tag.name }</Tag>
        ))}
      </HashTagContainer>
    </Wrapper>
  )
}

export default PostItem;