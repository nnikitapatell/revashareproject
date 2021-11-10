import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { User } from 'src/app/classes/user';
import { BackendService } from 'src/app/services/backend.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-social-profile',
  templateUrl: './social-profile.component.html',
  styleUrls: ['./social-profile.component.css']
})
export class SocialProfileComponent implements OnInit {

  get userData(): User {  return this._userData; }
  @Input() set userData(value:User) {  this._userData = value; this.updateUI(this._userData); }
  private _userData!: User;

  displayName: string = 'DisplayName';
  userName: string = '@UserName';
  aboutMe: string = 'Description Text';
  authorProfilePicUrl: string = '';


  constructor() { }

  ngOnInit(): void 
  {
  }

  updateUI(data: User) 
  {
      this.displayName = data.firstName + ' ' + data.lastName;
      this.userName = data.username;
      this.aboutMe = data.aboutMe;
      this.authorProfilePicUrl = data.imageURL;
  }

}
