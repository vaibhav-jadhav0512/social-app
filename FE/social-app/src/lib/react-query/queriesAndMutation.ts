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
  likePost,
  unLikePost,
  getCurrentUser,
  getSavedPosts,
  savePost,
  unSavePost,
  getPostById,
  getUserPosts,
  deletePost,
  explore,
  searchPosts,
  getAllUsers,
  getLikedPosts,
  getUserByUserName,
  updateUser,
} from "../functions/httpRequests";
import {
  INewPost,
  INewUser,
  ISignIn,
  IUpdatePost,
  IUpdateUser,
  Likes,
  UserInfo,
} from "@/types";
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
        queryKey: [QUERY_KEYS.GET_POST_BY_ID, data],
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

export const useLikePost = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ like }: { like: Likes }) => likePost(like),
    onSuccess: (postId) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POST_BY_ID, postId],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_CURRENT_USER],
      });
    },
  });
};

export const useUnLikePost = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ like }: { like: Likes }) => unLikePost(like),
    onSuccess: (postId) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POST_BY_ID, postId],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_CURRENT_USER],
      });
    },
  });
};
export const useGetCurrentUser = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_CURRENT_USER],
    queryFn: getCurrentUser,
  });
};

export const useGetSavedPosts = () => {
  const { data: currentUser } = useGetCurrentUser();
  return useQuery({
    queryKey: [QUERY_KEYS.GET_SAVED_POSTS],
    queryFn: () =>
      currentUser ? getSavedPosts() : Promise.reject("User not logged in"),
  });
};
export const useSavePost = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      userName,
      postId,
    }: {
      userName: string;
      postId: number;
    }) => {
      return savePost(userName, postId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_CURRENT_USER],
      });
    },
  });
};

export const useUnSavePost = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async ({
      userName,
      postId,
    }: {
      userName: string;
      postId: number;
    }) => {
      return unSavePost(userName, postId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_POSTS],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_CURRENT_USER],
      });
    },
  });
};

export const useGetPostById = (postId?: number) => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_POST_BY_ID, postId],
    queryFn: () => {
      if (postId === undefined) {
        return Promise.reject(new Error("postId is undefined"));
      }
      return getPostById(postId);
    },
    enabled: !!postId,
  });
};

export const useGetUserPosts = (userName?: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_USER_POSTS, userName],
    queryFn: () => {
      if (userName === undefined) {
        return Promise.reject(new Error("userName is undefined"));
      }
      return getUserPosts(userName);
    },
    enabled: !!userName,
  });
};
export const useDeletePost = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ postId }: { postId?: number }) => {
      if (postId === undefined) {
        return Promise.reject(new Error("postId is undefined"));
      }
      return deletePost(postId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_RECENT_POSTS],
      });
    },
  });
};
export const useGetPosts = () => {
  return useInfiniteQuery({
    queryKey: [QUERY_KEYS.GET_INFINITE_POSTS],
    queryFn: ({ pageParam }) => explore(pageParam),
    initialPageParam: 1,
    getNextPageParam: (lastPage, allPages) => {
      if (lastPage && lastPage.length === 0) {
        return null;
      }
      const nextPage = allPages.length + 1;
      return nextPage;
    },
  });
};

export const useSearchPosts = (searchTerm: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.SEARCH_POSTS, searchTerm],
    queryFn: () => searchPosts(searchTerm),
    enabled: !!searchTerm,
  });
};
export const useGetAllUsers = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_USERS],
    queryFn: () => getAllUsers(),
  });
};
export const useGetLikedPosts = () => {
  const { data: currentUser } = useGetCurrentUser();
  return useQuery({
    queryKey: [QUERY_KEYS.GET_LIKED_POSTS],
    queryFn: () =>
      currentUser ? getLikedPosts() : Promise.reject("User not logged in"),
  });
};
export const useGetUserByUserName = (userName?: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.GET_USER_BY_ID, userName],
    queryFn: () => {
      if (userName === undefined) {
        return Promise.reject(new Error("userName is undefined"));
      }
      return getUserByUserName(userName);
    },
    enabled: !!userName,
  });
};
export const useUpdateUser = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (user: IUpdateUser) => updateUser(user),
    onSuccess: (data: UserInfo) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_CURRENT_USER],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.GET_USER_BY_ID, data.userName],
      });
    },
  });
};
