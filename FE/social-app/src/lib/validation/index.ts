import { z } from "zod";

// ============================================================
// USER
// ============================================================
export const SignupValidation = z.object({
  name: z.string().min(5, { message: "Name must be at least 5 characters." }),
  userName: z
    .string()
    .min(5, { message: "UserName must be at least 5 characters." }),
  email: z.string().email(),
  password: z
    .string()
    .min(5, { message: "Password must be at least 5 characters." }),
});

export const SigninValidation = z.object({
  userName: z
    .string()
    .min(5, { message: "Name must be at least 5 characters." }),
  password: z
    .string()
    .min(5, { message: "Password must be at least 5 characters." }),
});

export const ProfileValidation = z.object({
  file: z.custom<File[]>(),
  fullName: z
    .string()
    .min(2, { message: "Name must be at least 2 characters." }),
  userName: z
    .string()
    .min(2, { message: "Name must be at least 2 characters." }),
  email: z.string().email(),
  bio: z.string(),
});

// ============================================================
// POST
// ============================================================
export const PostValidation = z.object({
  caption: z
    .string()
    .min(5, { message: "Minimum 5 characters." })
    .max(2200, { message: "Maximum 2,200 caracters" }),
  files: z.custom<File[]>(),
  location: z
    .string()
    .min(1, { message: "This field is required" })
    .max(1000, { message: "Maximum 1000 characters." }),
  tags: z.string(),
  imageUrl: z.string(),
});
