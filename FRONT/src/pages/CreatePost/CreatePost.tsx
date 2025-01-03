import { ButtonGroup, CreatePostContainer, PostTitleInput } from "./CreatePost.style";
import CategorySelector from "./components/CategorySelector/CategorySelector";
import { Category } from "../../types/category";
import { fetchCategories } from "@_services/categoryApi";
import { useQuery } from "@tanstack/react-query";
import { ApiResponse } from "../../services/api";
import EditorContainer from "./components/EditorContainer/EditorContainer";
import Button from "@_components/Button/Button";
import NicknamePasswordInput from "./components/NicknamePasswordInput/NicknamePasswordInput";
import useCreatePost from "./hooks/useCreatePost";

function CreatePost() {
  const { post, handlePostChange, handleSaveButtonClick } = useCreatePost();
  const { data } = useQuery<ApiResponse<Category[]>>({
    queryKey: ["categories"],
    queryFn: () => fetchCategories(),
  });

  return (
    <CreatePostContainer>
      <CategorySelector categories={data?.data as Category[]} />
      <PostTitleInput
        placeholder="제목을 입력하세요"
        value={post.title}
        onChange={(e) => handlePostChange('title', e.target.value)} 
      />
      <EditorContainer />
      <NicknamePasswordInput />
      <ButtonGroup>
        <Button content='임시저장' type='Primary' handleButtonClick={()=>{}} />
        <Button content='저장하기' type='Primary' handleButtonClick={handleSaveButtonClick} />
      </ButtonGroup>
    </CreatePostContainer>
  );
}

export default CreatePost;
