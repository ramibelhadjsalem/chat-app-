import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { BehaviorSubject, Observable } from 'rxjs';
//@ts-ignore
import * as io from 'socket.io-client';
import { AppUser } from '../models/AppUser';
import { AppState } from '../Store/AppState';
import { ADD_LIST_USER, ADD_USER } from '../Store/usersConnected/UsersReducer';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class ChatService {
  socket: any
  private _list: AppUser[] = [];
  private _observableList=new  BehaviorSubject<AppUser[]>([]);

  get observableList(): Observable<AppUser[]> { return this._observableList.asObservable() }

  constructor(private store: Store<AppState>,private auth : AuthService){}

  add(person: AppUser) {
      this._list.push(person);
      this._observableList.next(this._list);
  }
  addlist(personne :AppUser[]){
    this._list =[...this._list ,...personne]
    this._observableList.next(this._list)
  }
  remove(person:AppUser){
    const index = this._list.indexOf(person)
    if(index){
      this._list.splice(index,1);
      this._observableList.next(this._list)
    }
    
  }

  initSocket() {
    
    this.socket = io("http://localhost:8878", {
      reconnection: true,
      query: {
        Authorization: "Bearer " +this.accessToken()
      }

    })
    this.socket.on('connect', () => {
      console.log("connected to socket io");
    });
    this.socket.on("listconnected", (res: any) => {
      console.log("connected to webSoket", res)
      this.store.dispatch({
        type: ADD_LIST_USER,
        payload: res
      });
      
    })
    this.socket.on("userconnect", (res: AppUser) => {
      console.log("connected to webSoket", res)
      this.store.dispatch({
        type: ADD_USER,
        payload: res
      });
      
    })
    this.socket.on("userdeconnect", (res: any) => {
      console.log("connected to webSoket", res)
      this.remove(res)
      
    })
    this.socket.on("chatmessage", (res: any) => {
      // const sound = new Audio(messageaudio);
      console.log("new message", res);

    })
  }


  accessToken = () => {
    const user = localStorage.getItem("user")
    if (user != null) {
      const { token } = JSON.parse(user)
      return token
    }
    return ""
  }
}

