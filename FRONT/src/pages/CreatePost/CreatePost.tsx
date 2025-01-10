import { ButtonGroup, CreatePostContainer, PostTitleInput } from "./CreatePost.style";
import CategorySelector from "./components/CategorySelector/CategorySelector";
import { Category } from "../../types/category";
import { fetchCategories } from "@_services/categoryApi";
import { useQuery } from "@tanstack/react-query";
import { ApiResponse } from "../../services/api";
import Button from "../../components/Button/Button";
import NicknamePasswordInput from "./components/NicknamePasswordInput/NicknamePasswordInput";
import TagWrapper from "./components/TagWrapper/TagWrapper";
import MarkdownEditor from "./components/MarkdownEditor/MarkdownEditor";
import useCreatePost from "./hooks/useCreatePost";
import useMarkdownEditor from "./hooks/useMarkdownEditor";
import useTag from "./hooks/useTag";

function CreatePost() {
  const { tags, newTag, isInputVisible, handleAddTagClick, handleInputChange, handleInputKeyDown, handleRemoveTag } = useTag();
  const { post, handlePostChange, handleSaveButtonClick } = useCreatePost(tags);
  const { handleIconButtonClick, handleFileUpload, textareaRef, fileInputRef } = useMarkdownEditor({
    initialMarkdown: post.content,
    imageIds: post.imageIds,
    onContentChange: (value: string) => handlePostChange('content', value),
    onImageIdsChange: (value: number[]) => handlePostChange('imageIds', value),
  });
  const { data } = useQuery<ApiResponse<Category[]>>({
    queryKey: ["categories"],
    queryFn: () => fetchCategories(),
  });

  return (
    <CreatePostContainer>
      <CategorySelector
        categories={data?.data as Category[]}
        onCategorySelect={(id) => handlePostChange('categoryId', id)}
      />
      <PostTitleInput
        placeholder="제목을 입력하세요"
        value={post.title}
        onChange={(e) => handlePostChange('title', e.target.value)} 
      />
      <MarkdownEditor
        markdown={post.content}
        onChange={(value) => handlePostChange('content', value)}
        onIconButtonClick={handleIconButtonClick}
        textareaRef={textareaRef}
        fileInputRef={fileInputRef}
        onFileUpload={handleFileUpload}
      />
      <TagWrapper
        tags={tags}
        newTag={newTag}
        onAddTagClick={handleAddTagClick}
        onInputChange={handleInputChange}
        onInputKeyDown={handleInputKeyDown}
        onRemoveTag={handleRemoveTag}
        isInputVisible={isInputVisible}
      />
      <NicknamePasswordInput
        author={post.author}
        password={post.password}
        onAuthorChange={(value) => handlePostChange('author', value)}
        onPasswordChange={(value) => handlePostChange('password', value)}
      />
      <ButtonGroup>
        <Button content='임시저장' type='Primary' handleButtonClick={()=>{}} />
        <Button content='저장' type='Primary' handleButtonClick={handleSaveButtonClick} />
      </ButtonGroup>
    </CreatePostContainer>
  );
}

export default CreatePost;