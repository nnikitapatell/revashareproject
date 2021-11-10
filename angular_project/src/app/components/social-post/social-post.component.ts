import { Component, Directive, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Post } from 'src/app/classes/post';
import { BackendService } from 'src/app/services/backend.service';
import { Comment } from 'src/app/classes/comment';
import { ActivatedRoute, Router } from '@angular/router';
import * as Constants from 'src/app/classes/constants';
import { MatDialog } from '@angular/material/dialog';
import { SocialCommentEditorComponent } from '../social-comment-editor/social-comment-editor.component';

@Component({
  selector: 'app-social-post',
  templateUrl: './social-post.component.html',
  styleUrls: ['./social-post.component.css']
})

export class SocialPostComponent implements OnInit
{
  get postData(): Post {  return this._postData; }
  @Input() set postData(value:Post) {  this._postData = value; this.updateUI(this._postData); }
  private _postData!: Post;

  @ViewChild('Post-Img1') imageContainer1!: ElementRef;
  @ViewChild('Post-Img2') imageContainer2!: ElementRef;
  @ViewChild('Post-Img3') imageContainer3!: ElementRef;
  @ViewChild('Post-Img4') imageContainer4!: ElementRef;

  
  comments: Comment[] = [];
  firstName: string = 'First';
  lastName: string = 'Last';
  timestamp: string = new Date().toLocaleString();
  description: string = 'Description Text';
  authorProfilePicUrl: string = '';
  image1: string = '';
  image2: string = '';
  image3: string = '';
  image4: string = '';
  commentCount: number = 0;
  likeCount: number = 0;
<<<<<<< HEAD
=======
  youTubeUrl: string = '';

  isCommentsWaiting: boolean = false;

  commentListOpenState: boolean = false;
  isLiked: boolean = false;

  pagination_offset: number = 0;
  showBackwards: boolean = false;
  showForwards: boolean = false;
  
  constructor(private backendService: BackendService, private router: Router, private route: ActivatedRoute, public dialog: MatDialog) {}
>>>>>>> 8d61c07401592b201d43f849fe629232cc0eab5b

  goBackwards() {
    this.pagination_offset -= Constants.PaginationLimit;
    this.updateComments(this.postData);
  }

  goForwards() {
    this.pagination_offset += Constants.PaginationLimit;
    this.updateComments(this.postData);
  }


  updateLikeIcon(data: Post) {
    this.backendService.isPostLiked(data).then(isLiked => {
      this.isLiked = isLiked;
    },
    error => {});
  }

  updateLikeCount(data: Post)  {
    this.backendService.getPostLikeCount(data).then(result => {
      this.likeCount = result;
    },
    error => {});
  }

  updateComments(data: Post) {
    this.comments = [];
    this.isCommentsWaiting = true;
    this.backendService.getPostComments(data, this.pagination_offset, Constants.PaginationLimit).then(result => {
      this.isCommentsWaiting = false;
      if(result != undefined && result != null){
        for(var i = 0; i < result.length ; i++){
            this.comments.push(result[i]);
        }
      }
      this.updateButtons();

   },
   error => {
    this.isCommentsWaiting = false;
   });
  }

  updateCommentCount(data: Post) {
    this.backendService.getPostCommentCount(data).then(result => {
      this.commentCount = result;
    },
    error => {});
  }

  updateUI(data: Post)  {
    if (data == null) return;

    if (data.author != null) {
      this.firstName = data.author.firstName;
      this.lastName = data.author.lastName;
      this.authorProfilePicUrl = data.author.imageURL;
    }

    this.timestamp = new Date(data.submittedAt).toLocaleString();
    this.description = data.description;

    
    this.image1 = data.imageUrl1;
    this.image2 = data.imageUrl2;
    this.image3 = data.imageUrl3;
    this.image4 = data.imageUrl4;
    this.youTubeUrl = data.youTubeUrl;
    
    this.updateLikeCount(data);
    this.updateLikeIcon(data);
    
    this.updateCommentCount(data);
    this.updateComments(data);
  }

  ngOnInit(): void {

  }

  goToProfile() {
    this.router.navigateByUrl(Constants.PATH_ProfileURL + '/' + this.postData.author.username, { });
  }  

  showComments() {
    this.commentListOpenState = !this.commentListOpenState
  }

  createComment() : void {
    var dialogRef = this.dialog.open(SocialCommentEditorComponent, {
      height: 'auto',
      width: '75%',
      data: { post: this.postData}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  likePost() : void {
      this.backendService.isPostLiked(this.postData).then(isLiked => {
        if (isLiked) {
          this.backendService.unlikePost(this.postData).then(result => {
            this.updateLikeCount(this.postData);
            this.updateLikeIcon(this.postData);
          });
        }
        else {
          this.backendService.likePost(this.postData).then(result => {
            this.updateLikeCount(this.postData);
            this.updateLikeIcon(this.postData);
          });
        }
      });
  }

  updateButtons() {
    if (this.comments.length >= Constants.PaginationLimit) this.showForwards = true;
    else this.showForwards = false;

    if (this.pagination_offset > 0) this.showBackwards = true;
    else this.showBackwards = false;
  }

}
