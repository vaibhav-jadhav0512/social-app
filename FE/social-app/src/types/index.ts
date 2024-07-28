export type INavLink = {
  imgURL: string;
  route: string;
  label: string;
};

export type IUpdateUser = {
  userId: string;
  name: string;
  bio: string;
  imageId: string;
  imageUrl: URL | string;
  file: File[];
};

export type INewPost = {
  userName: string;
  caption: string;
  files: File[];
  location: string;
  tags: string;
};

export type IUpdatePost = {
  postId: number;
  caption: string;
  files: File[];
  location: string;
  tags: string;
};

export type IUser = {
  userId: string;
  name: string;
  userName: string;
  email: string;
  imageUrl: string;
  bio: string;
};

export type INewUser = {
  name: string;
  email: string;
  userName: string;
  password: string;
};

export type ISignIn = {
  userName: string;
  password: string;
};

export type IContextType = {
  user: IUser;
  isLoading: boolean;
  setUser: React.Dispatch<React.SetStateAction<IUser>>;
  isAuthenticated: boolean;
  setIsAuthenticated: React.Dispatch<React.SetStateAction<boolean>>;
  checkAuthUser: () => Promise<boolean>;
};

export type Document = {
  id: string;
  title: string;
  content: string;
  authorId: string;
  createdAt: Date;
  updatedAt: Date;
};

export type IPostType = {
  caption: string;
  file: [];
  location: string;
  tags: string;
  imageUrl: string;
};

export type PostType = {
  postId: number;
  userName: string;
  caption: string;
  location: string;
  tags: string;
  createdAt: string;
  files: Files[];
  likes: Likes[];
  imageUrl: string;
};
export type Likes = {
  id: number;
  userName: string;
  postId: number;
};

export type Files = {
  id: number;
  imageUrl: string;
};
