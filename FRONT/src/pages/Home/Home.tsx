import PostItem from "./components/PostItem/PostItem";
import PagingButton from "@_components/PagingButton/PagingButton";
import { PagingButtonWrapper, PostItemContainer, Wrapper } from "./Home.style";
import { Post } from "../../types/post";
import TagWrapper from "@_components/TagWrapper/TagWrapper";
import { useTag } from "@_components/TagWrapper/useTag";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";

function Home() {
  const navigate = useNavigate();
  const { page } = useParams();
  const currentPage = page ? Number(page) : 1;
  const [searchParams ] = useSearchParams();
  const categoryId = searchParams.get('categoryId') ? Number(searchParams.get('categoryId')) : undefined;
  const {
    tagList,
    selectedTag,
    posts, 
    handleTagClick 
  } = useTag({ categoryId, page });

  return (
    <Wrapper>
      <TagWrapper tagList={tagList} onTagClick={handleTagClick} selectedTag={selectedTag}/>
      <PostItemContainer>
        {
          posts.map((postItem: Post, i: number) => 
            <PostItem 
              title={postItem.title}
              createdAt={postItem.createdAt}
              content={postItem.content}
              userName={postItem.author}
              userIntro="어쩌구 저쩌구 개발자입니다."
              categoryHier={postItem.categoryHier}
              key={i}
            />
          )
        }
      </PostItemContainer>
      <PagingButtonWrapper>
        <PagingButton
          totalPages={10}
          currentPage={currentPage}
          setCurrentPage={(newPage: number) => navigate(`/${newPage}${searchParams.toString()}`)}
        />
      </PagingButtonWrapper>
    </Wrapper>
  )
}

export default Home;