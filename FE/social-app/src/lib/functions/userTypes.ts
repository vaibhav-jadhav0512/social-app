export interface UserSignUpData {
  userName: string;
  password: string;
  email: string;
  name: string;
}

export interface SignUpResponse {
  access_token: string;
  user_name: string;
}
