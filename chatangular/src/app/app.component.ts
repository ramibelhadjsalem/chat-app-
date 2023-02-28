import { Component, OnInit } from '@angular/core';
import { ChatService } from './core/services/chat.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'chatangular';
  constructor(private chatService : ChatService){}



  ngOnInit(): void {
   this.chatService.initSocket()
  }
}
