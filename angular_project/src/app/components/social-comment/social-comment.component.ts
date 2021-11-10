import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Comment } from 'src/app/classes/comment';
import * as Constants from 'src/app/classes/constants';

@Component({
  selector: 'app-social-comment',
  templateUrl: './social-comment.component.html',
  styleUrls: ['./social-comment.component.css']
})
export class SocialCommentComponent implements OnInit {
  
  private _commentData!: Comment;
    
  @Input() set commentData(value: Comment) {
  
     this._commentData = value;
     this.updateUI(this._commentData);
  
  }
  
  get commentData(): Comment {
  
    return this._commentData;
  
  }

  firstName: string = 'First';
  lastName: string = 'Last';
  timestamp: string = new Date().toLocaleString();
  description: string = 'Description Text';
  authorProfilePicUrl: string = '';

  constructor(private router: Router, private route: ActivatedRoute) {}

   onSubmit() : void 
   {

   }

   goToProfile() {
    this.router.navigateByUrl(Constants.PATH_ProfileURL + '/' + this.commentData.author.username, { });
    }  

   updateUI(data: Comment) 
   {
    this.firstName = data.author.firstName;
    this.lastName = data.author.lastName;
    this.timestamp = new Date(data.submittedAt).toLocaleString();
    this.description = data.description;
    this.authorProfilePicUrl = data.author.imageURL;
   }
    
   ngOnInit() 
   {  

   }
}
