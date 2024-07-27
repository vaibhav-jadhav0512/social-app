import {
  INewPost,
  INewUser,
  ISignIn,
  IUpdatePost,
  IUser,
  Likes,
  PostType,
} from "@/types";
import { SignUpResponse } from "./userTypes";

import axios, { AxiosResponse } from "axios";

export const signUpUser = async (
  userData: INewUser
): Promise<SignUpResponse> => {
  const apiUrl = "http://192.168.1.110:8181/auth/sign-up";
  try {
    const response: AxiosResponse<SignUpResponse> = await axios.post(
      apiUrl,
      userData
    );
    localStorage.setItem("token", response.data.access_token);
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const signInUser = async (
  userData: ISignIn
): Promise<SignUpResponse> => {
  const apiUrl = "http://192.168.1.110:8181/auth/sign-in";
  try {
    const credentials = btoa(`${userData.userName}:${userData.password}`);
    const response: AxiosResponse<SignUpResponse> = await axios.post(
      apiUrl,
      {},
      {
        headers: {
          Authorization: "Basic " + credentials,
        },
      }
    );
    localStorage.setItem("token", response.data.access_token);
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const getCurrentUser = async (): Promise<IUser> => {
  const apiUrl = "http://192.168.1.110:8181/auth/user";
  try {
    const response: AxiosResponse<IUser> = await axios.get(apiUrl, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
export const signOutAccount = async () => {
  await localStorage.removeItem("token");
};
export const createPost = async (post: INewPost): Promise<INewPost> => {
  const apiUrl = "http://192.168.1.110:8001/posts/create";
  const formData = new FormData();
  post.files.forEach((file, index) => {
    formData.append(`files`, file);
  });
  formData.append("userName", post.userName);
  formData.append("location", post.location);
  formData.append("tags", post.tags);
  formData.append("caption", post.caption);
  console.log(post);
  try {
    const response: AxiosResponse<INewPost> = await axios.post(
      apiUrl,
      formData,
      {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const updatePost = async (post: IUpdatePost): Promise<IUpdatePost> => {
  const apiUrl = "http://192.168.1.110:8181/post/create";
  const formData = new FormData();
  post.files.forEach((file, index) => {
    formData.append(`images[${index}]`, file);
  });
  formData.append("postId", post.postId);
  formData.append("location", post.location);
  formData.append("tags", post.tags);
  formData.append("caption", post.caption);
  try {
    const response: AxiosResponse<IUpdatePost> = await axios.post(
      apiUrl,
      formData,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const getRecentPosts = async (): Promise<PostType[]> => {
  const apiUrl = "http://192.168.1.110:8181/posts/recent";
  try {
    const response: AxiosResponse<PostType[]> = await axios.get(apiUrl, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const likePost = async (like: Likes): Promise<number> => {
  const apiUrl = "http://192.168.1.110:8181/likes/like";
  try {
    const response: AxiosResponse<number> = await axios.post(
      `${apiUrl}/${like.userName}/${like.postId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const unLikePost = async (like: Likes): Promise<number> => {
  const apiUrl = "http://192.168.1.110:8181/likes/unlike";
  try {
    const response: AxiosResponse<number> = await axios.delete(
      `${apiUrl}/${like.userName}/${like.postId}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
export const getSavedPosts = async (userName: string): Promise<PostType[]> => {
  const apiUrl = "http://192.168.1.110:8181/saver/get";
  try {
    const response: AxiosResponse<PostType[]> = await axios.get(
      `${apiUrl}/${userName}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
export const savePost = async (
  userName: string,
  postId: number
): Promise<number> => {
  const apiUrl = "http://192.168.1.110:8181/saver/save";
  try {
    const response: AxiosResponse<number> = await axios.post(
      `${apiUrl}/${userName}/${postId}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const unSavePost = async (
  userName: string,
  postId: number
): Promise<number> => {
  const apiUrl = "http://192.168.1.110:8181/saver/unsave";
  try {
    const response: AxiosResponse<number> = await axios.delete(
      `${apiUrl}/${userName}/${postId}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};

export const getPostById = async (postId: number): Promise<PostType> => {
  const apiUrl = "http://192.168.1.110:8181/posts/get";
  try {
    const response: AxiosResponse<PostType> = await axios.get(
      `${apiUrl}/${postId}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      }
    );
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
