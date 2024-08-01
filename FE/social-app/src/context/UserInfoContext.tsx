import { createContext, useContext, useEffect, useState } from "react";
import { UserInfo } from "@/types";
import { getUserInfoFromToken } from "@/lib/functions/httpRequests";
interface IUserInfoContext {
  user: UserInfo;
  isLoading: boolean;
  error: string | null;
}

const INITIAL_USER_INFO = {
  userName: "",
  mobile: "",
  profileImage: "",
  fullName: "",
  bio: "",
};

const INITIAL_STATE: IUserInfoContext = {
  user: INITIAL_USER_INFO,
  isLoading: false,
  error: null,
};

const UserInfoContext = createContext<IUserInfoContext>(INITIAL_STATE);

const UserInfoProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<UserInfo>(INITIAL_USER_INFO);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      const token = localStorage.getItem("token");
      if (!token) return;

      setIsLoading(true);
      try {
        const userInfo = await getUserInfoFromToken(token);
        setUser(userInfo);
        console.log(userInfo);
      } catch (err) {
        setError("Failed to fetch user information");
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  const value = { user, isLoading, error };

  return (
    <UserInfoContext.Provider value={value}>
      {children}
    </UserInfoContext.Provider>
  );
};

export default UserInfoProvider;

export const useUserInfoContext = () => useContext(UserInfoContext);
