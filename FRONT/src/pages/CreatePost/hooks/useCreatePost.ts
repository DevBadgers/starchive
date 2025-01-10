import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { createPost } from '@_services/createPostApi';
import { useNavigate } from 'react-router-dom';
import { CreatePostParams } from '../../../types/post';
import { postTag } from '../../../services/tagApi';

function useCreatePost(tags: string[]) {
  const navigate = useNavigate();
  const [post, setPost] = useState<CreatePostParams>({
    title: '',
    content: '',
    author: '',
    password: '',
    categoryId: 0,
    hashTagIds: [],
    imageIds: [],
  });

  const handlePostChange = (field: keyof CreatePostParams, value: string | number | number[]) => {
    setPost((prev) => ({ ...prev, [field]: value }));
  };

  const handleMarkdownChange = (markdown: string) => {
    handlePostChange('content', markdown);
  };

  const mutation = useMutation({
    mutationFn: (newPost: CreatePostParams) => createPost(newPost),
    onSuccess: () => navigate('/'),
    onError: () =>  alert("저장에 실패하였습니다.")
  });

  const validateRequiredFields = () => {
    const missingFields = [];

    if (!post['title']) missingFields.push('제목');
    if (!post['content']) missingFields.push('내용');
    if (!post['author']) missingFields.push('닉네임');
    if (!post['password']) missingFields.push('비밀번호');
    if (!post['categoryId']) missingFields.push('카테고리');

    if (missingFields.length > 0) {
      alert(`${missingFields.join(' ,')} 항목을 입력해주세요`);
      return false;
    }
    return true
  }

  const processTags = async (tagNames: string[]) => {
    if (tagNames.length === 0) return;

    await Promise.all(
      tagNames.map(async (tag) => {
        const response = await postTag(tag);
        if (response.data) {
          post.hashTagIds.push(response.data.id);
        }
      })
    );
  };

  const handleSaveButtonClick = () => {
    processTags(tags);

    if (validateRequiredFields()) {
      mutation.mutate(post);
    }
  };

  return {
    post,
    handlePostChange,
    handleMarkdownChange,
    handleSaveButtonClick,
  };
};

export default useCreatePost;