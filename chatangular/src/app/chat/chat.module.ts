import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChatRoutingModule } from './chat-routing.module';
import { ChatComponent } from './chat/chat.component';
import { RoomItemComponent } from './conponents/room-item/room-item.component';


@NgModule({
  declarations: [
    ChatComponent,
    RoomItemComponent
  ],
  imports: [
    CommonModule,
    ChatRoutingModule
  ]
})
export class ChatModule { }
