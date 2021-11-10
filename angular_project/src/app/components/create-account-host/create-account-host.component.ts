import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { BackendService } from 'src/app/services/backend.service';
import { ResponseMessage } from 'src/app/classes/response-message';
import {Router, ROUTES} from "@angular/router"
import { UserService } from 'src/app/services/user.service';
import { AppRoutingModule } from 'src/app/app-routing.module';
import * as Constants from 'src/app/classes/constants';


@Component({
  selector: 'app-create-account-host',
  templateUrl: './create-account-host.component.html',
  styleUrls: ['./create-account-host.component.css']
})
export class CreateAccountHostComponent implements OnInit {

  @ViewChild('CreateAccount_Username') userNameInput!: ElementRef;
  @ViewChild('CreateAccount_First') firstInput!: ElementRef;
  @ViewChild('CreateAccount_Last') lastInput!: ElementRef;
  @ViewChild('CreateAccount_Email') emailInput!: ElementRef;
  @ViewChild('CreateAccount_Password') passwordInput!: ElementRef;

  isWorking: boolean = false;

  constructor(private service: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  attemptCreation() 
  {

    this.isWorking = true;

    var userName = this.userNameInput.nativeElement.value;
    var firstName = this.firstInput.nativeElement.value;
    var lastName = this.lastInput.nativeElement.value;
    var emailAddress = this.emailInput.nativeElement.value;
    var password = this.passwordInput.nativeElement.value;

    this.service.createAccount(userName, firstName, lastName, emailAddress, password).then(
      result => {
        this.isWorking = false;
        if (result != null) 
        {
          this.router.navigateByUrl(Constants.PATH_GlobalFeedURL);
        } 
      },
      error => {
        this.isWorking = false;
        alert(error);
      },
    );
    

  }

}
