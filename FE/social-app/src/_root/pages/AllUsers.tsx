import Loader from "@/components/shared/Loader";
import UserCard from "@/components/shared/UserCard";
import { useGetAllUsers } from "@/lib/react-query/queriesAndMutation";
import { UserInfo } from "@/types";
import { useUserContext } from "@/context/AuthContext";

const AllUsers = () => {
  const { data: users } = useGetAllUsers();
  const { user: loggedUser } = useUserContext();
  const filteredUsers = users?.filter(
    (user) => user.userName !== loggedUser.userName
  );
  return (
    <div className="common-container">
      <div className="user-container">
        <h2 className="h3-bold md:h2-bold text-left w-full">All Users</h2>
        {!users ? (
          <Loader />
        ) : (
          <ul className="user-grid">
            {filteredUsers?.map((user: UserInfo) => (
              <li
                key={user?.userName}
                className="flex-1 min-w-[200px] w-full  "
              >
                <UserCard user={user} />
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default AllUsers;
