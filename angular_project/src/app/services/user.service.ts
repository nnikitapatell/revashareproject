import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { ResponseMessage } from '../classes/response-message';
import { User } from '../classes/user';
import * as Constants from 'src/app/classes/constants';


@Injectable({
  providedIn: 'root'
})
export class UserService {


  constructor(private http: HttpClient) {

  }

  public login(username: string, password: string) : Promise<ResponseMessage> {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      withCredentials: true

    };
    var body = [ username, password ];
    return this.http.post<ResponseMessage>(Constants.UserControllerBackendURL + '/login', body, httpOptions).toPromise();
  }

  public createAccount(username: string, firstName: string, lastName: string, emailAddress: string, password: string) : Promise<User> {
    var newAccount = new User(username, firstName, lastName, emailAddress, password, '', '');
    return this.http.post<User>(Constants.UserControllerBackendURL, newAccount, {withCredentials: true}).toPromise();
  }

  public resetPassword(emailAddress: string) : Promise<ResponseMessage> {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      withCredentials: true
    };
    return this.http.post<ResponseMessage>(Constants.UserControllerBackendURL + '/lostpwd', emailAddress, httpOptions).toPromise();
  }

  public getCurrentUser() : Promise<User> {
    return this.http.get<User>(Constants.UserControllerBackendURL + '/current', {withCredentials: true}).toPromise();
  }

  public getUser(username: string) : Promise<User> {
    let params = new HttpParams().set('username', username);
    return this.http.get<User>(Constants.UserControllerBackendURL,  { params: params, withCredentials: true }).toPromise();
  }

  public updateAccount(data: User): Promise<ResponseMessage> {
    var httpOptions = {
      headers: new HttpHeaders({'Content-Type':  'application/json'}),
      withCredentials: true
    };
    return this.http.put<ResponseMessage>(Constants.UserControllerBackendURL, data, httpOptions).toPromise();

  }

  public logoff() : Promise<ResponseMessage> {
    return this.http.post<ResponseMessage>(Constants.UserControllerBackendURL + '/logout', '', {withCredentials: true}).toPromise();
  }
}
