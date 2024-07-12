import { SignUpResponse, UserSignUpData } from "./userTypes";

import axios, { AxiosResponse } from "axios";

export const signUpUser = async (
  userData: UserSignUpData
): Promise<SignUpResponse> => {
  const apiUrl = "http://localhost:8080/auth/sign-up";
  try {
    const response: AxiosResponse<SignUpResponse> = await axios.post(
      apiUrl,
      userData
    );
    console.log("Sign-up successful:", response.data);
    return response.data;
  } catch (error: unknown) {
    console.log(error);
    throw error;
  }
};
