import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { fetchPostList } from "../../services/postApi";
import { fetchAllTagList, fetchPostsByTag, fetchTagListByCategory } from "../../services/tagApi";
import { Post } from "../../types/post";
import { ApiResponse } from "../../services/api";
import { Tag } from "../../types/tag";

export const useTag = (categoryId?: number) => {
  const [selectedTag, setSelectedTag] = useState<string | null>(null);

  const { data: tagList } = useQuery<ApiResponse<Tag[]>>({
    queryKey: categoryId ? ['tagList', categoryId] : ['tagList'],
    queryFn: () =>
      categoryId ? fetchTagListByCategory(categoryId) : fetchAllTagList(),
  });

  const { data, refetch } = useQuery<ApiResponse<Post[]>>({
    queryKey: selectedTag ? ["postData", selectedTag] : ["postData"],
    queryFn: () =>
      selectedTag ? fetchPostsByTag(selectedTag) : fetchPostList(), //태그가 선택되지 않았을 때는 fetchPostList()만 호출
  });

  const handleTagClick = (tagName: string) => {
    const newSelectedTag = selectedTag === tagName ? null : tagName;
    setSelectedTag(newSelectedTag);

    if (newSelectedTag) {
      refetch();
    }
  };

  return {
    tagList: tagList?.data || [],
    selectedTag,
    posts: data?.data || [],
    handleTagClick,
  };
};