import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppUser } from 'src/app/core/models/AppUser';
import { ChatService } from 'src/app/core/services/chat.service';
import { AppState } from 'src/app/core/Store/AppState';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit{
  usersConnected!: Observable<AppUser[]>
  constructor(public chatservice :ChatService,private store :Store<AppState>){}
  
  
  ngOnInit(): void {
   this.usersConnected= this.store.select(state => state.users);
  }

}
