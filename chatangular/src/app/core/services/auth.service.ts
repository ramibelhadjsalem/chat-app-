import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { map, Observable, ReplaySubject } from 'rxjs';
import { environment } from 'src/environments/environment';

import { loggedin } from '../models/loggedin';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSource = new ReplaySubject<loggedin |null>(1);
  currentUser$ = this.currentUserSource.asObservable();

  apiUrl=environment.apiurl
  constructor(private http:HttpClient,private route:Router) { }

  login(model:any){
    return this.http.post<loggedin>(this.apiUrl+"auth/signin" ,model).pipe(
      map((res:any)=>{
        const user = res;
        if(user){
          this.setCurrentUser(user)
          this.route.navigateByUrl("/")
         
        }
      })
    )
  }
  public register(model:any,url:String ): Observable<any>{
    localStorage.setItem("phoneNumber",JSON.stringify(model.username))
    return this.http.post<any >(this.apiUrl+"auth/signup/"+url, model);
  }
  
  setCurrentUser(user:loggedin){
    localStorage.setItem('user',JSON.stringify(user));
    this.currentUserSource.next(user);
  }
}
