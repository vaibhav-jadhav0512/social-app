import { Link } from "react-router-dom";

import { Button } from "../ui/button";
import { UserInfo } from "@/types";

type UserCardProps = {
  user: UserInfo;
};

const UserCard = ({ user }: UserCardProps) => {
  return (
    <Link to={`/profile/${user.userName}`} className="user-card">
      <img
        src={user.profileImage || "/assets/icons/profile-placeholder.svg"}
        alt="creator"
        className="rounded-full w-14 h-14"
      />

      <div className="flex-center flex-col gap-1">
        <p className="base-medium text-light-1 text-center line-clamp-1">
          {user.fullName}
        </p>
        <p className="small-regular text-light-3 text-center line-clamp-1">
          @{user.userName}
        </p>
      </div>

      <Button type="button" size="sm" className="shad-button_primary px-5">
        Follow
      </Button>
    </Link>
  );
};

export default UserCard;
