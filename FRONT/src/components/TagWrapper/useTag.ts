import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { fetchPostList } from "@_services/postApi";
import { fetchAllTagList, fetchTagListByCategory } from "@_services/tagApi";
import { Post } from "../../types/post";
import { ApiResponse } from "../../services/api";
import { Tag } from "../../types/tag";

interface UseTagParams {
  categoryId?: number;
  pageSize?: number;
  page?: number;
}

export const useTag = ({ categoryId, pageSize, page }: UseTagParams = {}) => {
  const [selectedTag, setSelectedTag] = useState<number | undefined>(undefined);

  const { data: tagList } = useQuery<ApiResponse<Tag[]>>({
    queryKey: categoryId ? ['tagList', categoryId] : ['tagList'],
    queryFn: () =>
      categoryId ? fetchTagListByCategory(categoryId) : fetchAllTagList(),
  });

  const { data } = useQuery<ApiResponse<Post[]>>({
    queryKey: [
      "postData", 
      categoryId, 
      selectedTag, 
      pageSize || 10, 
      page || 1
    ],
    queryFn: () => 
      fetchPostList({ 
        category: categoryId, 
        tag: selectedTag, 
        pageSize, 
        page 
      }),
  });

  const handleTagClick = (tagId: number) => {
    const newSelectedTag = selectedTag === tagId ? undefined : tagId;
    setSelectedTag(newSelectedTag);
  };

  return {
    tagList: tagList?.data || [],
    selectedTag,
    posts: data?.data || [],
    handleTagClick,
  };
};