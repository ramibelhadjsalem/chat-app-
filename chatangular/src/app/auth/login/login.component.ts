import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { loggedin } from 'src/app/core/models/loggedin';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{


  constructor(private auth :AuthService,private fb:FormBuilder,private route :Router){}
  

  form !:FormGroup

  login(){
    this.auth.login(this.form.value).subscribe((res)=>{
      this.route.navigateByUrl("/")
      
    },err=>{
      console.log("err",err);
      
    })
    
    
  }

  ngOnInit(): void {
   this.initForm()
  }
  initForm(){
    this.form =this.fb.group({
      username :["",Validators.required],
      password :["",Validators.required]
    })
  }

}
