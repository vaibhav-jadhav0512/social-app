import { PostType } from "@/types";
import React from "react";

type PostStatProps = {
  post?: PostType;
  userName: string;
};
const PostStats = ({ post, userName }: PostStatProps) => {
  return <div>PostStats</div>;
};

export default PostStats;
