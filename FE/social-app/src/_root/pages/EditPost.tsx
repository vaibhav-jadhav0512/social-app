import { useParams } from "react-router-dom";

import Loader from "@/components/shared/Loader";
import PostForm from "@/_root/forms/PostForm";
import { useGetPostById } from "@/lib/react-query/queriesAndMutation";

const EditPost = () => {
  const { id } = useParams<{ id?: string }>();
  const numericId = id ? Number(id) : undefined;

  const { data: post, isLoading } = useGetPostById(numericId);
  console.log(post);
  if (isLoading)
    return (
      <div className="flex-center w-full h-full">
        <Loader />
      </div>
    );

  return (
    <div className="flex flex-1">
      <div className="common-container">
        <div className="flex-start gap-3 justify-start w-full max-w-5xl">
          <img
            src="/assets/icons/edit.svg"
            width={36}
            height={36}
            alt="edit"
            className="invert-white"
          />
          <h2 className="h3-bold md:h2-bold text-left w-full">Edit Post</h2>
        </div>

        {isLoading ? <Loader /> : <PostForm action="Update" post={post} />}
      </div>
    </div>
  );
};

export default EditPost;
