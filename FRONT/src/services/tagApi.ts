import { getRequest, postRequest } from "./api";
import { Tag } from "../types/tag";

export const fetchAllTagList = () => {
  return getRequest<Tag[]>('/hashtags');
}

export const fetchTagListByCategory = (categoryId: number) => {
  return getRequest<Tag[]>(`/categories/${categoryId}/hashtags`);
}

export const postTag = (name: string) => {
  return postRequest<Tag>('/hashtag', {
    body: { 'name': name },
    headers: { 'Content-Type': 'application/json' },
  })
}