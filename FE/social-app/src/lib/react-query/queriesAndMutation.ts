import {
  useQuery,
  useMutation,
  useQueryClient,
  useInfiniteQuery,
} from "@tanstack/react-query";
import { signInUser, signUpUser } from "../functions/httpRequests";
import { INewUser, ISignIn } from "@/types";

export const useCreateUserAccountMutation = () => {
  return useMutation({
    mutationFn: (userData: INewUser) => signUpUser(userData),
  });
};
export const useSignInMutation = () => {
  return useMutation({
    mutationFn: (userData: ISignIn) => signInUser(userData),
  });
};
export const useSignInAccount = () => {
  return useMutation({
    mutationFn: (user: { userName: string; password: string }) =>
      signInUser(user),
  });
};
