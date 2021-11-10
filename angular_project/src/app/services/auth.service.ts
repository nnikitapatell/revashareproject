import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot,RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor(
    private router: Router,
    private userService: UserService
  ) {

   }
   
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    var subject = new Subject<boolean>();
    this.userService.getCurrentUser().then(
      result => {
        if (result == null)
        {
          this.router.navigate(['../login']);     
          subject.next(false);
        }
        else {
          subject.next(true);
        } 
      });
      return subject.asObservable();
  }
}