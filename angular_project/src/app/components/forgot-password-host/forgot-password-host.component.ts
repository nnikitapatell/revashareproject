import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { BackendService } from 'src/app/services/backend.service';
import { ResponseMessage } from 'src/app/classes/response-message';
import {Router, ROUTES} from "@angular/router"
import { UserService } from 'src/app/services/user.service';
import { AppRoutingModule } from 'src/app/app-routing.module';
import * as Constants from 'src/app/classes/constants';

@Component({
  selector: 'app-forgot-password-host',
  templateUrl: './forgot-password-host.component.html',
  styleUrls: ['./forgot-password-host.component.css']
})
export class ForgotPasswordHostComponent implements OnInit {

  @ViewChild('ForgotPassword_Email') emailInput!: ElementRef;

  isWorking: boolean = false;


  constructor(private service: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  sendValidation() {
    this.isWorking = true;
    var emailAddress = this.emailInput.nativeElement.value;
    this.service.resetPassword(emailAddress).then(
      result => {
        var status = result.status;
        this.isWorking = false;
        if (status == 'success') 
        {
          this.router.navigateByUrl(Constants.PATH_LoginURL);
        } 
      },
      error => {
        this.isWorking = false;
        alert(error);
      },
    );
    

  }

}
