import { getRequest } from "./api";
import { Post } from "../types/post";
import { Tag } from "../types/tag";

export const fetchPostsByTag = (tagName: string) => getRequest<Post[]>('/hashtag', { params: { tag: tagName } });
export const fetchAllTagList = () => getRequest<Tag[]>('/hashtags');
export const fetchTagListByCategory = (categoryId: number) => getRequest<Tag[]>(`/categorys/${categoryId}/hashtags`);