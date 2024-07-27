import {
  useGetCurrentUser,
  useGetSavedPosts,
  useLikePost,
  useSavePost,
  useUnLikePost,
  useUnSavePost,
} from "@/lib/react-query/queriesAndMutation";
import { checkIsLiked } from "@/lib/utils";
import { Likes, PostType } from "@/types";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

type PostStatProps = {
  post?: PostType;
  userName: string;
};

const PostStats = ({ post, userName }: PostStatProps) => {
  const location = useLocation();
  const containerStyles = location.pathname.startsWith("/profile")
    ? "w-full"
    : "";
  const [likes, setLikes] = useState<Likes[]>(post?.likes || []);
  const [isSaved, setIsSaved] = useState<boolean>(false);

  const { mutate: likePost } = useLikePost();
  const { mutate: unLikePost } = useUnLikePost();
  const { data: currentUser, isLoading: isCurrentUserLoading } =
    useGetCurrentUser();
  const {
    data: savedPosts,
    isLoading: isSavedPostsLoading,
    isError: isSavedPostsError,
  } = useGetSavedPosts();
  const { mutate: savePost } = useSavePost();
  const { mutate: unSavePost } = useUnSavePost();

  useEffect(() => {
    if (currentUser) {
      const savedPostRecord = savedPosts?.some((item) => item.id === post?.id);
      setIsSaved(!!savedPostRecord);
    }
  }, [currentUser, savedPosts, post?.id]);

  const handleLikePost = async (
    e: React.MouseEvent<HTMLImageElement, MouseEvent>
  ) => {
    e.stopPropagation();
    if (!post?.id) {
      console.error("Post ID is undefined. Cannot process likes.");
      return;
    }
    const like: Likes = {
      id: 0,
      userName,
      postId: post.id,
    };
    let updatedLikes = [...likes];
    const userIndex = updatedLikes.findIndex(
      (user) => user.userName === userName
    );
    if (userIndex !== -1) {
      updatedLikes.splice(userIndex, 1);
      await unLikePost({ like });
    } else {
      updatedLikes.push(like);
      await likePost({ like });
    }
    setLikes(updatedLikes);
  };

  const handleSavePost = async (
    e: React.MouseEvent<HTMLImageElement, MouseEvent>
  ) => {
    e.stopPropagation();
    if (!post?.id) {
      console.error("Post ID is undefined");
      return;
    }
    if (isSaved) {
      await unSavePost({ userName, postId: post.id });
      setIsSaved(false);
    } else {
      await savePost({ userName, postId: post.id });
      setIsSaved(true);
    }
  };

  if (isCurrentUserLoading || isSavedPostsLoading) {
    return <p>Loading...</p>;
  }

  if (isSavedPostsError) {
    return <p>Error loading saved posts</p>;
  }

  return (
    <div
      className={`flex justify-between items-center z-20 ${containerStyles}`}
    >
      <div className="flex gap-2 mr-5">
        <img
          src={
            checkIsLiked(likes, userName)
              ? "/assets/icons/liked.svg"
              : "/assets/icons/like.svg"
          }
          alt="like"
          width={20}
          height={20}
          onClick={handleLikePost}
          className="cursor-pointer"
        />
        <p className="small-medium lg:base-medium">{likes.length}</p>
      </div>
      <div className="flex gap-2">
        <img
          src={isSaved ? "/assets/icons/saved.svg" : "/assets/icons/save.svg"}
          alt="save"
          width={20}
          height={20}
          onClick={handleSavePost}
          className="cursor-pointer"
        />
      </div>
    </div>
  );
};

export default PostStats;
