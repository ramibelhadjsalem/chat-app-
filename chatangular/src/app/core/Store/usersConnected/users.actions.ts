import { Injectable } from "@angular/core";
import { AppUser } from "../../models/AppUser";
import { ADD_USER } from "./UsersReducer";

@Injectable()
export class UserConnectedActions {
    static ADD_USER = '[USERS] ADD_USER'
    static ADD_USERS = '[USERS] ADD_USERS' 
    static REMOVE_USER = '[USERS] REMOVE_USER'
    

    addUserToListConnected =(data:AppUser)=>({type : ADD_USER ,payload :data})
    addUsersToListConnected =(data:AppUser[])=>({type : UserConnectedActions.ADD_USERS ,payload :data})
    removeUsersFromListConnected =(data:AppUser)=>({type : UserConnectedActions.REMOVE_USER ,payload :data})
}

