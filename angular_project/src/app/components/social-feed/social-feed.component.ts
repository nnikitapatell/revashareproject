import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, ParamMap, Params } from '@angular/router'
import { Post } from 'src/app/classes/post';
import { BackendService } from 'src/app/services/backend.service';
import { UserService } from 'src/app/services/user.service';
import { Router } from "@angular/router"
import * as Constants from 'src/app/classes/constants';
import { User } from 'src/app/classes/user';
import { Subject, Subscription } from 'rxjs';
import { takeUntil } from "rxjs/operators"
import { SocialCommentEditorComponent } from '../social-comment-editor/social-comment-editor.component';
import { MatDialog } from '@angular/material/dialog';
import { ProfileEditorComponent } from '../profile-editor/profile-editor.component';

@Component({
  selector: 'app-social-feed',
  templateUrl: './social-feed.component.html',
  styleUrls: ['./social-feed.component.css']
})
export class SocialFeedComponent implements OnInit, OnDestroy {

  showSearchBar: boolean = false;
  isNotificationsPage: boolean = false;
  notificationCount: number = 0;
  pagination_offset: number = 0;
  showBackwards: boolean = false;
  showForwards: boolean = false;
  postPromise!: Promise<Post[]>;
  isPostsWaiting: boolean = false;

  @ViewChild('SearchBox') searchBox!: ElementRef;

  posts!: Post[];
  user!: User;



  constructor(private route: ActivatedRoute, private backendService: BackendService, private userService: UserService, private router: Router, public dialog: MatDialog) 
  { 

  }

  createPost() : void 
  {
    var dialogRef = this.dialog.open(SocialCommentEditorComponent, {
      height: 'auto',
      width: '75%',
      data: { mode: 'post'}
    });
  }

  openNotifications() {
    this.router.navigateByUrl(Constants.PATH_Notifications);
  }

  clearNotifications() {
    this.backendService.makeAllPostsAsRead().then(
      result => {
        this.updateUI();
      },
      error => {
        alert(error);
        this.updateUI();
      }
    )
  }

  updateUI() {
    this.posts = [];
    this.isPostsWaiting = true;
    this.getUserNotificationCount();

    if (this.isNotificationsPage) {
      this.postPromise = this.backendService.getUnreadPosts(this.pagination_offset, Constants.PaginationLimit);
      this.updatePosts();
    } 
    else {
      this.route.paramMap.subscribe((params: ParamMap) => {
        var id = params.get('id');
        if (id != null) {
          this.userService.getUser(id).then(result => {
            if (result != null) {
              this.user = result;
              this.postPromise = this.backendService.getUsersPosts(result.id, this.pagination_offset, Constants.PaginationLimit);
              this.updatePosts();
            }

          });
        }
        else {
          this.postPromise = this.backendService.getPosts(this.pagination_offset, Constants.PaginationLimit);
          this.updatePosts();
        } 

      });
    }
  }

  updatePosts() {
    this.postPromise.then(result => {
      this.posts = result;
      this.isPostsWaiting = false;
      this.updateButtons();
    },
    error => {
      this.isPostsWaiting = false;
    });
  }

  updateButtons() {
    if (this.posts.length >= Constants.PaginationLimit) this.showForwards = true;
    else this.showForwards = false;

    if (this.pagination_offset > 0) this.showBackwards = true;
    else this.showBackwards = false;
  }

  goBackwards() {
    this.pagination_offset -= Constants.PaginationLimit;
    let params = { offset: this.pagination_offset};
    this.router.navigate([], {queryParams: params }).then(
      () => {
        this.updateUI();
      }
    );
  }

  goForwards() {
    this.pagination_offset += Constants.PaginationLimit;
    let params = { offset: this.pagination_offset};
    this.router.navigate([], {queryParams: params }).then(
      () => {
        this.updateUI();
      }
    );
  }

  ngOnInit(): void {
    this.generateSubscriptions();
    this.updateUI();
  }

  generateSubscriptions() {
    this.route.url.subscribe(params => {
      if(params[0].path == Constants.PATH_Notifications) {
        this.isNotificationsPage = true;
      }
      else {
        this.isNotificationsPage = false;
      }
    },
    error => {});

    this.route.paramMap.subscribe((params: ParamMap) => {
      if (params.has('offset')) {
        var offset_s = params.get('offset');
        if (offset_s == null) return;
        var offset = parseInt(offset_s);
        if (offset == null)  return;
        if (offset < 0) offset = 0;
        this.pagination_offset = offset;
      }

      });
  }

  
  getUserNotificationCount() {
    this.backendService.getUnreadCount().then(data => {
      this.notificationCount = data;
    });
  }

  ngOnDestroy(): void {
    
  }

  goHome() {
    this.router.navigateByUrl(Constants.PATH_GlobalFeedURL);
  }

  search() {
    var text = this.searchBox.nativeElement.value;
    this.userService.getUser(text).then(
      result => {
        if (result == null) alert('User does not exist');
        else this.router.navigateByUrl(Constants.PATH_ProfileURL + '/' + result.username);
      },
      error => {
        alert(error);
      },
    );
  }

  settings() {
    var dialogRef = this.dialog.open(ProfileEditorComponent, {
      height: 'auto',
      width: '75%'
    });
  }

  logOut() {
    this.userService.logoff().then(
      result => {
        var status = result.status;
        if (status == 'success') 
        {
          this.router.navigateByUrl(Constants.PATH_LoginURL);
        } 
      },
      error => {
        alert(error);
      },
    );
  }

}
