import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthLayoutComponent } from './auth-layout/auth-layout.component';
import { ChatLayoutComponent } from './chat-layout/chat-layout.component';
import { ErrorLayoutComponent } from './error-layout/error-layout.component';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';



@NgModule({
  declarations: [
    AuthLayoutComponent,
    ChatLayoutComponent,
    ErrorLayoutComponent,
    NavComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports:[
    AuthLayoutComponent,
    ChatLayoutComponent,
    ErrorLayoutComponent
  ]
})
export class LayoutModule { }
