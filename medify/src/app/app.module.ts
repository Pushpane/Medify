import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {NgxWebstorageModule} from 'ngx-webstorage';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import {A11yModule} from '@angular/cdk/a11y';
import {ClipboardModule} from '@angular/cdk/clipboard';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {PortalModule} from '@angular/cdk/portal';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {CdkStepperModule} from '@angular/cdk/stepper';
import {CdkTableModule} from '@angular/cdk/table';
import {CdkTreeModule} from '@angular/cdk/tree';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatBadgeModule} from '@angular/material/badge';
import {MatBottomSheetModule} from '@angular/material/bottom-sheet';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatChipsModule} from '@angular/material/chips';
import {MatStepperModule} from '@angular/material/stepper';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDividerModule} from '@angular/material/divider';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MatMenuModule} from '@angular/material/menu';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatSliderModule} from '@angular/material/slider';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatTreeModule} from '@angular/material/tree';
import {OverlayModule} from '@angular/cdk/overlay';
import { ToastrModule } from 'ngx-toastr';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { SignupComponent } from './auth/signup.component';
import { LoginComponent } from './auth/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OwnerSignupComponent } from './auth/owner-signup.component';
import { OwnerLoginComponent } from './auth/owner-login.component';
import { AdminSignupComponent } from './auth/admin-signup.component';
import { AdminLoginComponent } from './auth/admin-login.component';
import { ForgotPasswordComponent } from './auth/forgot-password.component';
import { UpdatePasswordComponent } from './auth/update-password.component';
import { OwnerDashboardComponent } from './dashboard/owner-dashboard.component';
import { FooterComponent } from './header/footer.component';
import { CommonModule } from '@angular/common';
import { TokenInterceptor } from './auth/token-interceptor';
import { AddMedicinesComponent } from './dashboard/add-medicines.component';
import { UserDashboardComponent } from './dashboard/user-dashboard.component';
import { CartComponent } from './dashboard/cart.component';
import { UserCheckerGuard } from './dashboard/checker.guard';
import { OwnerCheckerGuard } from './dashboard/owner-checker.guard';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SignupComponent,
    LoginComponent,
    OwnerSignupComponent,
    OwnerLoginComponent,
    AdminSignupComponent,
    AdminLoginComponent,
    ForgotPasswordComponent,
    UpdatePasswordComponent,
    OwnerDashboardComponent,
    FooterComponent,
    AddMedicinesComponent,
    UserDashboardComponent,
    CartComponent,
  ],
  imports: [
    BrowserModule,
    CommonModule,
    FormsModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    HttpClientModule,
    A11yModule,
    ClipboardModule,
    CdkStepperModule,
    CdkTableModule,
    CdkTreeModule,
    DragDropModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    OverlayModule,
    PortalModule,
    ScrollingModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot(),
    ToastrModule.forRoot(),
    RouterModule.forRoot([
      { path: '', canActivate: [UserCheckerGuard],component:UserDashboardComponent },
      { path: 'Dashboard', canActivate: [UserCheckerGuard],component:UserDashboardComponent },
      { path: 'Cart',canActivate: [UserCheckerGuard], component:CartComponent },
      { path: 'AddMedicines', canActivate: [OwnerCheckerGuard],component:AddMedicinesComponent },
      { path: 'OwnerDashboard', canActivate: [OwnerCheckerGuard],component:OwnerDashboardComponent },
      { path: 'Signup', component: SignupComponent },
      { path: 'OwnerSignup', component: OwnerSignupComponent},
      { path: 'OwnerLogin', component: OwnerLoginComponent },
      { path: 'AdminSignup', component: AdminSignupComponent},
      { path: 'AdminLogin', component: AdminLoginComponent },
      { path: 'ForgotPassword', component: ForgotPasswordComponent},
      { path: 'UpdatePassword', component: UpdatePasswordComponent},
      { path: 'Login', component: LoginComponent },
      { path: '**', redirectTo: 'Signup', pathMatch: 'full' },
    ]),
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
