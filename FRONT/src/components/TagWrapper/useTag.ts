import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { fetchPostList } from "@_services/postApi";
import { fetchAllTagList, fetchTagListByCategory } from "@_services/tagApi";
import { PostParams } from "../../types/post";
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

  const filterUndefined = (obj: Record<string, any>) =>
    Object.fromEntries(Object.entries(obj).filter(([_, value]) => value !== undefined));

  const { data } = useQuery<ApiResponse<{
    currentPage: number,
    totalPages: number,
    totalCount: number,
    posts: PostParams[]
  }>>({
    queryKey: [
      "postData", 
      categoryId, 
      selectedTag, 
      pageSize || 10, 
      page || 1
    ],
    queryFn: () => 
      fetchPostList(
        filterUndefined({
          category: categoryId,
          tag: selectedTag,
          pageSize,
          page,
        })
      ),
  });

  const currentPage = data?.data?.currentPage ?? 0;
  const totalPages = data?.data?.totalPages ?? 0;
  const totalCount = data?.data?.totalCount ?? 0;
  const posts = data?.data?.posts ?? [];

  const handleTagClick = (tagId: number) => {
    const newSelectedTag = selectedTag === tagId ? undefined : tagId;
    setSelectedTag(newSelectedTag);
  };

  return {
    tagList: tagList?.data || [],
    selectedTag,
    posts: posts,
    handleTagClick,
    totalPages,
    currentPage,
    totalCount,
  };
};