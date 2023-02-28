import { Action } from '@ngrx/store';
import { AppUser } from '../../models/AppUser';


export const ADD_USER = 'ADD_USER';
export const ADD_LIST_USER = 'ADD_LIST_USER';
export const REMOVE_USER = 'REMOVE_USER';
export function usersReducer(state: AppUser[] = [], action:any) {
    switch (action.type) {
        case ADD_USER:
            return [...state, action.payload];
        case ADD_LIST_USER:
            return [...state, ...action.payload];
        case REMOVE_USER : 
            return state.filter(x=>x.id !==action.payload.id)
        default:
            return state;
    }
}