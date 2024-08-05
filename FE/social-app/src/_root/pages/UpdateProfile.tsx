import * as z from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate, useParams } from "react-router-dom";

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useToast } from "@/components/ui/use-toast";

import { ProfileValidation } from "@/lib/validation";
import { useUserContext } from "@/context/AuthContext";
import {
  useGetUserByUserName,
  useUpdateUser,
} from "@/lib/react-query/queriesAndMutation";
import Loader from "@/components/shared/Loader";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import ProfileUploader from "@/components/shared/ProfileUploader";
import { useState } from "react";

const UpdateProfile = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const { userName: id } = useParams();
  const { user, setUser } = useUserContext();
  console.log(user);
  const { data: currentUser, isLoading } = useGetUserByUserName(id || "");
  const [loading, setloading] = useState(false);
  const form = useForm<z.infer<typeof ProfileValidation>>({
    resolver: zodResolver(ProfileValidation),
    defaultValues: {
      file: [],
      fullName: user.fullName || "",
      userName: user.userName || "",
      email: user.email || "",
      bio: currentUser?.bio || "",
    },
  });
  // Queries
  const { mutateAsync: updateUser } = useUpdateUser();

  if (isLoading) {
    return (
      <div className="flex-center w-full h-full">
        <Loader />
      </div>
    );
  }
  if (!currentUser) {
    return <div>Failed to load user data.</div>;
  }

  // Handler
  const handleUpdate = async (value: z.infer<typeof ProfileValidation>) => {
    setloading(true);
    try {
      const updatedUser = await updateUser({
        userName: currentUser.userName,
        fullName: value.fullName,
        bio: value.bio,
        file: value.file,
        profileImage: currentUser.profileImage,
      });

      if (updatedUser) {
        setUser({
          ...user,
          fullName: updatedUser.fullName,
          bio: updatedUser.bio,
          imageUrl: updatedUser.profileImage,
        });
        navigate(`/profile/${id}`);
      } else {
        toast({
          title: `Update user failed. Please try again.`,
        });
      }
    } catch (error) {
      toast({
        title: `An error occurred. Please try again.`,
      });
    } finally {
      setloading(false);
    }
  };

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
          <h2 className="h3-bold md:h2-bold text-left w-full">Edit Profile</h2>
        </div>

        <Form {...form}>
          <form
            onSubmit={form.handleSubmit(handleUpdate)}
            className="flex flex-col gap-7 w-full mt-4 max-w-5xl"
          >
            <FormField
              control={form.control}
              name="file"
              render={({ field }) => (
                <FormItem className="flex">
                  <FormControl>
                    <ProfileUploader
                      fieldChange={field.onChange}
                      mediaUrl={currentUser.profileImage}
                    />
                  </FormControl>
                  <FormMessage className="shad-form_message" />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="fullName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="shad-form_label">Name</FormLabel>
                  <FormControl>
                    <Input type="text" className="shad-input" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="userName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="shad-form_label">Username</FormLabel>
                  <FormControl>
                    <Input
                      type="text"
                      className="shad-input"
                      {...field}
                      disabled
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="shad-form_label">Email</FormLabel>
                  <FormControl>
                    <Input
                      type="text"
                      className="shad-input"
                      {...field}
                      disabled
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="bio"
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="shad-form_label">Bio</FormLabel>
                  <FormControl>
                    <Textarea
                      className="shad-textarea custom-scrollbar"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage className="shad-form_message" />
                </FormItem>
              )}
            />

            <div className="flex gap-4 items-center justify-end">
              <Button
                type="button"
                className="shad-button_dark_4"
                onClick={() => navigate(-1)}
              >
                Cancel
              </Button>
              <Button
                type="submit"
                className="shad-button_primary whitespace-nowrap"
                disabled={loading}
              >
                {loading && <Loader />}
                Update Profile
              </Button>
            </div>
          </form>
        </Form>
      </div>
    </div>
  );
};

export default UpdateProfile;
