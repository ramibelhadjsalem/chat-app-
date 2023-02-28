import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { ChatLayoutComponent } from './layout/chat-layout/chat-layout.component';
import { ErrorLayoutComponent } from './layout/error-layout/error-layout.component';

const routes: Routes = [
  {
    path: '', component: ChatLayoutComponent, children: [
      {path: '', redirectTo: '/', pathMatch: 'full'},
      {
        path: '',
        loadChildren: () => import('./chat/chat.module').then(m => m.ChatModule)
      }
    ]
  },
  { 
    path: '', component: AuthLayoutComponent, children: [
      {
        path: '',
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
      }
    ]
  },
  {
    path: '', component: ErrorLayoutComponent, children: [
      {path: '**', redirectTo: '404', pathMatch: 'full'},
      {
        path: '',
        loadChildren: () =>  import('./error/error.module').then(m => m.ErrorModule)
      }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
