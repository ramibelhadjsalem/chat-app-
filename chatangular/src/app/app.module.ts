import { NgModule, OnInit } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './layout/layout.module';
import {  HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { usersReducer } from './core/Store/usersConnected/UsersReducer';

@NgModule({
  declarations: [
    AppComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    HttpClientModule,
    StoreModule.forRoot({users: usersReducer})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule implements OnInit{



  ngOnInit(): void {
    console.log(window);
    
  }
}
