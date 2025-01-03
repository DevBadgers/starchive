import { useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { createPost } from '@_services/postApi';
import { CreatePostParams } from '../../../types/post';
import { useNavigate } from 'react-router-dom';

function useCreatePost() {
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

  const mutation = useMutation({
    mutationFn: (newPost: CreatePostParams) => createPost(newPost),
    onSuccess: () => navigate('/'),
    onError: () => alert("저장에 실패했습니다."),
  });

  const handleSaveButtonClick = () => {
    const requiredFields: (keyof CreatePostParams)[] = ['title', 'content', 'author', 'password', 'categoryId'];
    const missingFields: (keyof CreatePostParams)[] = [];
    requiredFields.forEach((field) => {
      if (!post[field]) missingFields.push(field);
    });
    if (missingFields.length > 0) {
      alert(`${missingFields.join(', ')}항목들을 입력해주세요.`);
      return;
    }
    mutation.mutate(post);
  };

  return {
    post,
    handlePostChange,
    handleSaveButtonClick,
  };
};

export default useCreatePost;