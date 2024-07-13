import { INewUser, ISignIn, IUser } from "@/types";
import { SignUpResponse, UserSignUpData } from "./userTypes";

import axios, { AxiosResponse } from "axios";

export const signUpUser = async (
  userData: INewUser
): Promise<SignUpResponse> => {
  const apiUrl = "http://localhost:8080/auth/sign-up";
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
  const apiUrl = "http://localhost:8080/auth/sign-in";
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
  const apiUrl = "http://localhost:8080/auth/user";
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
