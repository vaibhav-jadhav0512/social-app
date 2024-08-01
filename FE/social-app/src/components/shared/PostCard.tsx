import { useUserContext } from "@/context/AuthContext";
import { multiFormatDateString } from "@/lib/utils";
import { PostType } from "@/types";
import { Link } from "react-router-dom";
import PostStats from "./PostStats";
type PostProps = {
  post?: PostType;
};
const PostCard = ({ post }: PostProps) => {
  const { user } = useUserContext();
  return (
    <div className="post-card">
      <div className="flex-between">
        <div className="flex items-center gap-3">
          <Link to={`/profile/${post?.userName}`}>
            <img
              src={
                post?.profileImage || "/assets/icons/profile-placeholder.svg"
              }
              alt="creator"
              className="w-12 lg:h-12 rounded-full"
            />
          </Link>

          <div className="flex flex-col">
            <p className="base-medium lg:body-bold text-light-1">
              {post?.userName}
            </p>
            <div className="flex-center gap-2 text-light-3">
              <p className="subtle-semibold lg:small-regular ">
                {multiFormatDateString(post?.createdAt)}
              </p>
              •
              <p className="subtle-semibold lg:small-regular">
                {post?.location}
              </p>
            </div>
          </div>
        </div>

        <Link
          to={`/update-post/${post?.postId}`}
          className={`${user?.userName !== post?.userName && "hidden"}`}
        >
          <img
            src={"/assets/icons/edit.svg"}
            alt="edit"
            width={20}
            height={20}
          />
        </Link>
      </div>

      <Link to={`/posts/${post?.postId}`}>
        <div className="small-medium lg:base-medium py-5">
          <p>{post?.caption}</p>
          <ul className="flex gap-1 mt-2">
            {post?.tags.split(",").map((tag: string, index: number) => (
              <li key={`${tag}${index}`} className="text-light-3 small-regular">
                #{tag}
              </li>
            ))}
          </ul>
        </div>

        <img
          src={
            post?.files[0].imageUrl || "/assets/icons/profile-placeholder.svg"
          }
          alt="post image"
          className="post-card_img"
        />
      </Link>

      <PostStats post={post} userName={user.userName} />
    </div>
  );
};

export default PostCard;
