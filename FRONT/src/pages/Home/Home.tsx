import PostItem from "./components/PostItem/PostItem";
import PagingButton from "@_components/PagingButton/PagingButton";
import { ButtonWrapper, PagingButtonWrapper, PostItemContainer, PostListWithTags, Wrapper } from "./Home.style";
import { PostParams } from "../../types/post";
import TagWrapper from "@_components/TagWrapper/TagWrapper";
import { useTag } from "@_components/TagWrapper/useTag";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import Button from "@_components/Button/Button";

function Home() {
  const navigate = useNavigate();
  const { page } = useParams();
  const currentPage = page ? Number(page) : 1;
  const [searchParams ] = useSearchParams();
  const categoryId = searchParams.get('categoryId') ? Number(searchParams.get('categoryId')) : undefined;
  const pageSize = searchParams.get('pageSize') ? Number(searchParams.get('pageSize')) : undefined;
  const {
    tagList,
    selectedTag,
    posts,
    handleTagClick,
    totalPages,
  } = useTag({ categoryId, page: currentPage - 1, pageSize });

  return (
    <Wrapper>
      <ButtonWrapper>
        <Button content='작성하기' type='Primary' handleButtonClick={() => navigate('/create-post')} />
      </ButtonWrapper>
      <PostListWithTags>
        <PostItemContainer>
        {
          posts.map((postItem: PostParams, i: number) =>
            <PostItem
              postId={postItem.postId}
              title={postItem.title}
              createdAt={postItem.createdAt}
              content={postItem.content}
              userName={postItem.author}
              userIntro="어쩌구 저쩌구 개발자입니다."
              categoryHier={postItem.categoryHier}
              hashTags={postItem.hashTags}
              key={i}
            />
          )
        }
        </PostItemContainer>
        <TagWrapper tagList={tagList} onTagClick={handleTagClick} selectedTag={selectedTag}/>
      </PostListWithTags>
      <PagingButtonWrapper>
        <PagingButton
          totalPages={totalPages}
          currentPage={currentPage}
          setCurrentPage={(newPage: number) => navigate(`/${newPage}?${searchParams.toString()}`)}
        />
      </PagingButtonWrapper>
    </Wrapper>
  )
}

export default Home;