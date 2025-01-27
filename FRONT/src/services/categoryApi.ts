import { Category } from "../types/category";
import { deleteRequest, getRequest, postRequest, putRequest } from "./api";

export const fetchCategories = () => getRequest<Category[]>('/categories');

export const createCategory = (name: string, parentId: number | null) =>
  postRequest<Category>('/categories', {
    body: { name, parentId }
  });

export const updateCategory = (categoryId: number, name: string, parentId: number | null) => 
  putRequest<Category>('/categories', {
    body: { categoryId, name, parentId }
  });

export const deleteCategory = (categoryId: number) =>
  deleteRequest<void>( `/categories/${categoryId}` );