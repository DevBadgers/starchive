import { CreatePostParams, Post } from "../types/post";
import { getRequest, postRequest } from "./api";

interface PostListParams {
  category?: number;
  tag?: number;
  pageSize?: number;
  page?: number;
}

export const fetchPostList = (params?: PostListParams) => {
  const queryParams = {
    pageSize: 10,
    page: 1,
    ...params
  };

  const stringifiedParams: Record<string, string> = Object.fromEntries(
    Object.entries(queryParams).map(([key, value]) => [key, String(value)])
  );

  return getRequest<Post[]>('/posts', { 
    params: stringifiedParams
  });
};

export const createPost = (params: CreatePostParams) => {
  return postRequest<CreatePostParams>('/post', {
    body: params,
    headers: {
      'Content-Type': 'application/json',
    }
  })
}