import { INewPost, INewUser, ISignIn, IUpdatePost, IUser } from "@/types";
import { SignUpResponse, UserSignUpData } from "./userTypes";

import axios, { AxiosResponse } from "axios";
import { useUserContext } from "@/context/AuthContext";

export const signUpUser = async (
  userData: INewUser
): Promise<SignUpResponse> => {
  const apiUrl = "http://192.168.1.110:8181/auth/sign-up";
  try {
    const response: AxiosResponse<SignUpResponse> = await axios.post(
      apiUrl,
      userData
    );
    console.log("Sign-up successful:", response.data);
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
    console.log("Sign-in successful:", response.data);
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
    console.log("User:", response.data);
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
  const apiUrl = "http://192.168.1.110:8181/posts/create";
  const formData = new FormData();
  post.files.forEach((file, index) => {
    formData.append(`images[${index}]`, file);
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
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
    console.log("User:", response.data);
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
    console.log("User:", response.data);
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
