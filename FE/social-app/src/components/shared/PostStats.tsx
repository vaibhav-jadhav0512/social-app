import {
  useLikePost,
  useUnLikePost,
} from "@/lib/react-query/queriesAndMutation";
import { checkIsLiked } from "@/lib/utils";
import { Likes, PostType } from "@/types";
import { useState } from "react";
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

  const handleLikePost = (
    e: React.MouseEvent<HTMLImageElement, MouseEvent>
  ) => {
    e.stopPropagation();
    let likesArray = [...likes];
    const userIndex = likesArray.findIndex(
      (user) => user.userName === userName
    );

    if (userIndex !== -1) {
      if (post?.id === undefined) {
        console.error("Post ID is undefined. Cannot add to likes.");
        return;
      }

      const unlike: Likes = {
        id: 0, // Replace with the actual id value or generate one
        userName: userName, // Ensure userName is defined
        postId: post.id, // Ensure post.id is defined
      };

      likesArray.splice(userIndex, 1);

      // Wrap the unlike object in an object with a `like` property
      unLikePost({ like: unlike });
    } else {
      if (post?.id === undefined) {
        console.error("Post ID is undefined. Cannot add to likes.");
        return;
      }
      const like: {
        id: number;
        userName: string;
        postId: number;
      } = {
        id: 0,
        userName: userName,
        postId: post.id,
      };

      likesArray.push(like);
      likePost({ like });
    }

    setLikes(likesArray); // Update the state with the new likes array
    console.log(likesArray); // Log
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
        />
      </div>
    </div>
  );
};

export default PostStats;
