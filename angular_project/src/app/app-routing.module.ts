import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginHostComponent } from './components/login-host/login-host.component';
import { SocialFeedComponent } from './components/social-feed/social-feed.component';
import { AuthGuardService } from './services/auth.service';
import * as Constants from './classes/constants';
import { CreateAccountHostComponent } from './components/create-account-host/create-account-host.component';
import { ForgotPasswordHostComponent } from './components/forgot-password-host/forgot-password-host.component';



const routes: Routes = [
  { path: Constants.PATH_LoginURL, component: LoginHostComponent},
  { path: Constants.PATH_CreateAccountURL, component: CreateAccountHostComponent},
  { path: Constants.PATH_ForgotPasswordURL, component: ForgotPasswordHostComponent },
  { path: Constants.PATH_Notifications, component: SocialFeedComponent, canActivate : [AuthGuardService] },
  { path: Constants.PATH_GlobalFeedURL, component: SocialFeedComponent, canActivate : [AuthGuardService]},
  { path: Constants.PATH_MyProfileURL, component: SocialFeedComponent, canActivate : [AuthGuardService]},
  { path: Constants.PATH_ProfileURL + '/:id', component: SocialFeedComponent, canActivate : [AuthGuardService]},
  { path: '', redirectTo: Constants.PATH_LoginURL, pathMatch:'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
