import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { UploadTaskSnapshot } from '@angular/fire/compat/storage/interfaces';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { Post } from 'src/app/classes/post';
import { BackendService } from 'src/app/services/backend.service';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Comment } from 'src/app/classes/comment';


@Component({
  selector: 'app-social-comment-editor',
  templateUrl: './social-comment-editor.component.html',
  styleUrls: ['./social-comment-editor.component.css']
})
export class SocialCommentEditorComponent implements OnInit {

  uploadPercent!: Observable<number | undefined> ;
  uploadedFilesUrls: string[] = new Array<string>();
  uploadedFiles: string[] = new Array<string>();
  selectedFiles: File[] = new Array<File>();
  selectedFilesPreview: string[] = new Array<string>();
  isCommentEditor: boolean = false;
  parentPost!: Post;

  isWorking: boolean = false;

  @ViewChild('CommentEditor_UploadFile') userNameInput!: ElementRef;
  @ViewChild('CommentEditor_Text') textInput!: ElementRef;


  constructor(private storage: AngularFireStorage, private backend: BackendService, @Inject(MAT_DIALOG_DATA) public data: {post: Post}) 
  {
    if (data.post != null) {
      this.isCommentEditor = true;
      this.parentPost = data.post;
    }
  }

  ngOnInit(): void {
    this.randomNumber();
  }

  
  selectFiles(event: any) {

    this.selectedFiles = [];
    this.selectedFilesPreview = [];

    for (var i = 0; i < event.target.files.length && i < 4; i++) 
    {
      var file = event.target.files[i];
      this.selectedFiles.push(file);
      this.getPreview(file).toPromise().then(result => {
        this.selectedFilesPreview.push(result);
      });
      
    }

  }

  getPreview(file: File): Observable<string> {
    return new Observable(obs => {
      const reader = new FileReader();
      reader.onload = () => {
        obs.next(reader.result as string);
        obs.complete();
      }
      reader.readAsDataURL(file);
    });
  }


  submit() {
    this.isWorking = true;
    if (this.isCommentEditor) this.createComment();
    else this.createPost();
  }

  createComment() {
    var newComment = this.makePost().then(result => {
      this.backend.createComment(result, this.parentPost).then(result => {
        this.isWorking = false;
        if (result.status == "success") window.location.reload();
      },
      error => {
        this.isWorking = false;
        alert(error);
      });
    });
  }


  createPost() {
    var newPost = this.makePost().then(result => {
      this.backend.createPost(result).then(result => {
        this.isWorking = false;
        if (result.status == "success") window.location.reload();
      },
      error => {
        this.isWorking = false;
        alert(error);
      });
    });
  }

  async makeComment() : Promise<Comment> {
    var newPost = new Comment();
    newPost.description = this.textInput.nativeElement.value;
    return newPost;
  }

  async makePost() : Promise<Post> {
    var newPost = new Post();
    newPost.description = this.textInput.nativeElement.value;
    for (var i = 0; i < this.selectedFiles.length; i++) {
      await this.uploadFile(this.selectedFiles[i]).then(result => {
        switch (i) {
          case 0:
            newPost.imageUrl1 = result;
            break;
          case 1:
            newPost.imageUrl2 = result;
            break;
          case 2:
            newPost.imageUrl3 = result;
            break;
          case 3:
            newPost.imageUrl4 = result;
            break;
        }
      });
    }
    return newPost;
  }

  uploadFile(file: File) : Promise<string> {
    var fileName = this.randomNumber() + "-" + file.name;
    var filePath = 'uploads/' + fileName;
    var uploadTask = this.storage.ref(filePath).put(file);

    return uploadTask.then(snapshot => {
      return snapshot.ref.getDownloadURL();
    }).then((url) => {
      return url
    });
  }




  //Simple randomNumber so files with the same name can be uploaded without overriding
  randomNumber(){
    return Math.random().toString().slice(2,12);
  }

}
