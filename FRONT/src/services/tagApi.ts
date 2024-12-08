import { getRequest } from "./api";
import { Tag } from "../types/tag";

export const fetchAllTagList = () => getRequest<Tag[]>('/hashtags');
export const fetchTagListByCategory = (categoryId: number) => getRequest<Tag[]>(`/categories/${categoryId}/hashtags`);