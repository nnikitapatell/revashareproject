import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SocialPostComponent } from './components/social-post/social-post.component';
import { SocialProfileComponent } from './components/social-profile/social-profile.component';
import { SocialCommentEditorComponent } from './components/social-comment-editor/social-comment-editor.component';
import { SocialFeedComponent } from './components/social-feed/social-feed.component';
import { SocialCommentComponent } from './components/social-comment/social-comment.component';
import { MatIconModule } from '@angular/material/icon';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCommonModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { BackendService } from './services/backend.service';
import { HttpClientModule } from '@angular/common/http';
import { LoginHostComponent } from './components/login-host/login-host.component';
import { UserService } from './services/user.service';
import { AuthGuardService } from './services/auth.service';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireStorageModule } from '@angular/fire/compat/storage';
import { environment } from '../environments/environment';
import { CreateAccountHostComponent } from './components/create-account-host/create-account-host.component';
import { ForgotPasswordHostComponent } from './components/forgot-password-host/forgot-password-host.component';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ProfileEditorComponent } from './components/profile-editor/profile-editor.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatBadgeModule } from '@angular/material/badge';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';

import {
  BrowserAnimationsModule
} from '@angular/platform-browser/animations';




@NgModule({
  declarations: [
    AppComponent,
    SocialPostComponent,
    SocialProfileComponent,
    SocialCommentEditorComponent,
    SocialFeedComponent,
    SocialCommentComponent,
    LoginHostComponent,
    CreateAccountHostComponent,
    ForgotPasswordHostComponent,
    ProfileEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatIconModule,
    MatCommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCardModule,
    MatGridListModule,
    MatButtonModule,
    MatExpansionModule,
    MatInputModule,
    MatButtonToggleModule,
    MatListModule,
    MatToolbarModule,
    MatMenuModule,
    HttpClientModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireStorageModule,
    MatDialogModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    BrowserAnimationsModule,
    MatBadgeModule
  ],
  providers: [BackendService, UserService, AuthGuardService, {provide: LocationStrategy, useClass: HashLocationStrategy}],
  bootstrap: [AppComponent]
})
export class AppModule { }
