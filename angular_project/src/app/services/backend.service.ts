import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from '../classes/post';
import { ResponseMessage } from '../classes/response-message';
import { Comment } from '../classes/comment';
import { User } from '../classes/user';
import * as Constants from 'src/app/classes/constants';


@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private http: HttpClient) {

  }

  getPosts(offset: number, limit: number) : Promise<Post[]> {
    let params = new HttpParams().set('offset', offset).set('limit', limit);
    return this.http.get<Post[]>(Constants.PostControllerBackendURL,  { params: params, withCredentials: true }).toPromise();
  }

  getUsersPosts(id: string, offset: number, limit: number) : Promise<Post[]> {
    let params = new HttpParams().set('user', id).set('offset', offset).set('limit', limit);
    return this.http.get<Post[]>(Constants.PostControllerBackendURL,  { params: params, withCredentials: true }).toPromise();
  }

  getPostLikeCount(data: Post): Promise<number> {
    let params = new HttpParams().set('postId', data.id);
    return this.http.get<number>(Constants.PostControllerBackendURL + '/like/count',  { params: params, withCredentials: true }).toPromise();
  }

  getPostComments(data: Post, offset: number, limit: number): Promise<Comment[]> {
    let params = new HttpParams().set('postId', data.id).set('offset', offset).set('limit', limit);
    return this.http.get<Comment[]>(Constants.PostControllerBackendURL + '/comments',  { params: params, withCredentials: true }).toPromise();
  }


  getPostCommentCount(data: Post): Promise<number> {
    let params = new HttpParams().set('postId', data.id);
    return this.http.get<number>(Constants.PostControllerBackendURL + '/comments/count',  { params: params, withCredentials: true }).toPromise();
  }

  isPostLiked(data: Post) : Promise<boolean> {
    let params = new HttpParams().set('postId', data.id);
    return this.http.get<boolean>(Constants.PostControllerBackendURL + '/isliked',  { params: params, withCredentials: true }).toPromise();
  }

  unlikePost(data: Post) : Promise<ResponseMessage> {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      params: new HttpParams().set('postId', data.id),
      withCredentials: true
    };
    return this.http.post<ResponseMessage>(Constants.PostControllerBackendURL + '/unlike', "", httpOptions).toPromise();
  }

  likePost(data: Post) : Promise<ResponseMessage> {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      params: new HttpParams().set('postId', data.id),
      withCredentials: true
    };
    return this.http.post<ResponseMessage>(Constants.PostControllerBackendURL + '/like', "", httpOptions).toPromise();
  }

  createPost(data: Post) {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      withCredentials: true
    };
    return this.http.post<ResponseMessage>(Constants.PostControllerBackendURL, data, httpOptions).toPromise();
  }

  createComment(comment: Comment, post: Post) {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      params: new HttpParams().set('postId', post.id),
      withCredentials: true
    };
    console.log(comment);
    return this.http.post<ResponseMessage>(Constants.PostControllerBackendURL + '/comment', comment, httpOptions).toPromise();
  }

  getUnreadPosts(offset: number, limit: number) : Promise<Post[]> {
    let params = new HttpParams().set('offset', offset).set('limit', limit);
    return this.http.get<Post[]>(Constants.PostControllerBackendURL + '/notifications',  { params: params, withCredentials: true }).toPromise();
  }

  getUnreadCount() : Promise<number> {
    return this.http.post<number>(Constants.PostControllerBackendURL + '/notifications/count',  { withCredentials: true }).toPromise();
  }

  makeAllPostsAsRead() : Promise<ResponseMessage> {
    return this.http.get<ResponseMessage>(Constants.PostControllerBackendURL + '/notifications/reset',  { withCredentials: true }).toPromise();
  }
}
