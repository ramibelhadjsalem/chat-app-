import { AppUser } from "../models/AppUser";

export interface AppState {
    readonly users: AppUser[];
  }