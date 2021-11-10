import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { BackendService } from 'src/app/services/backend.service';
import { ResponseMessage } from 'src/app/classes/response-message';
import {Router, ROUTES} from "@angular/router"
import { UserService } from 'src/app/services/user.service';
import { AppRoutingModule } from 'src/app/app-routing.module';
import * as Constants from 'src/app/classes/constants';


@Component({
  selector: 'app-login-host',
  templateUrl: './login-host.component.html',
  styleUrls: ['./login-host.component.css']
})
export class LoginHostComponent implements OnInit {

  @ViewChild('Login_UserName') userNameInput!: ElementRef;
  @ViewChild('Login_Password') passwordInput!: ElementRef;

  isWorking: boolean = false;

  constructor(private service: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  createAccount() {
    this.router.navigateByUrl(Constants.PATH_CreateAccountURL);
  }

  forgotPassword() {
    this.router.navigateByUrl(Constants.PATH_ForgotPasswordURL);
  }

  attemptLogin() {
    this.isWorking = true;
    var userName = this.userNameInput.nativeElement.value;
    var password = this.passwordInput.nativeElement.value;
    this.service.login(userName, password).then(
      result => {
        var status = result.status;
        this.isWorking = false;
        if (status == 'success') 
        {
          this.router.navigateByUrl(Constants.PATH_GlobalFeedURL);
        } 
      },
      error => {
        alert(error);
      },
    );
    

  }

}
