import {
  useQuery,
  useMutation,
  useQueryClient,
  useInfiniteQuery,
} from "@tanstack/react-query";
import {
  signInUser,
  signUpUser,
  signOutAccount,
  createPost,
  updatePost,
  getRecentPosts,
} from "../functions/httpRequests";
import { INewPost, INewUser, ISignIn, IUpdatePost } from "@/types";
import { QUERY_KEYS } from "./queryKeys";

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
export const useSignOutAccount = () => {
  return useMutation({
    mutationFn: signOutAccount,
  });
};
export const useCreatePost = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (post: INewPost) => createPost(post),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
    },
  });
};
export const useUpdatePost = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (post: IUpdatePost) => updatePost(post),
    onSuccess: (data: IUpdatePost) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POST_BY_ID, data?.postId],
      });
    },
  });
};

export const useGetRecentPosts = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
    queryFn: getRecentPosts,
  });
};
