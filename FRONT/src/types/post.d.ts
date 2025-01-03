export interface Post {
  categoryHier: {
      categoryId: number,
      categoryName: string
  }[]
  postId: number,
  title: string,
  author: string,
  createdAt: string,
  content: string,
  hashTags: {
    hashTagId: number,
    name: string
  }[]
}

export interface CreatePostParams {
  title: string;
  content: string;
  author: string;
  password: string;
  categoryId: Category;
  hashTagIds?: number[];
  imageIds?: number[];
}