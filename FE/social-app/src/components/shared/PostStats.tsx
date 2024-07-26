import {
  useGetCurrentUser,
  useLikePost,
  useUnLikePost,
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
  const [isSaved, setIsSaved] = useState(false);
  const { mutate: likePost } = useLikePost();
  const { mutate: unLikePost } = useUnLikePost();
  const { data: currentUser } = useGetCurrentUser();
  const savedPostRecord = currentUser?.saved.find(
    (record: Models.Document) => record.post.$id === post.id
  );
  useEffect(() => {
    setIsSaved(!!savedPostRecord);
  }, [currentUser]);
  const handleLikePost = (
    e: React.MouseEvent<HTMLImageElement, MouseEvent>
  ) => {
    e.stopPropagation();
    if (post?.id === undefined) {
      console.error("Post ID is undefined. Cannot process likes.");
      return;
    }
    const like: Likes = {
      id: 0,
      userName: userName,
      postId: post.id,
    };
    let updatedLikes = [...likes];
    const userIndex = updatedLikes.findIndex(
      (user) => user.userName === userName
    );
    if (userIndex !== -1) {
      updatedLikes.splice(userIndex, 1);
      unLikePost({ like });
    } else {
      updatedLikes.push(like);
      likePost({ like });
    }
    setLikes(updatedLikes);
  };
  const handleSavePost = (
    e: React.MouseEvent<HTMLImageElement, MouseEvent>
  ) => {
    e.stopPropagation();

    if (savedPostRecord) {
      setIsSaved(false);
      return deleteSavePost(savedPostRecord.$id);
    }

    savePost({ userName: userName, postId: post?.id });
    setIsSaved(true);
  };
  return (
    <div
      className={`flex justify-between items-center z-20 ${containerStyles}`}
    >
      <div className="flex gap-2 mr-5">
        <img
          src={`${
            checkIsLiked(likes, userName)
              ? "/assets/icons/liked.svg"
              : "/assets/icons/like.svg"
          }`}
          alt="like"
          width={20}
          height={20}
          onClick={(e) => handleLikePost(e)}
          className="cursor-pointer"
        />
        <p className="small-medium lg:base-medium">{post?.likes.length}</p>
      </div>

      <div className="flex gap-2">
        <img
          src={isSaved ? "/assets/icons/saved.svg" : "/assets/icons/save.svg"}
          alt="share"
          width={20}
          height={20}
          className="cursor-pointer"
          onClick={(e) => handleSavePost(e)}
        />
      </div>
    </div>
  );
};

export default PostStats;
