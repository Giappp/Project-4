import { NgModule } from '@angular/core';
import {
  BrowserModule,
  provideClientHydration,
} from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import {
  HTTP_INTERCEPTORS,
  HttpClientModule,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; // Ensure this is imported
import { StoreModule } from '@ngrx/store';
import { reducers } from './store/app.state';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';

import { CoreModule } from './core/core.module';
import { AuthModule } from './page/auth/auth.module';
import { AuthRoutingModule } from './page/auth/auth-routing.module';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { HomeLayoutComponent } from './layout/home-layout/home-layout.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { HeaderComponent } from './layout/header/header.component';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    HomeLayoutComponent,
    AuthLayoutComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule, // Explicitly import BrowserAnimationsModule
    AppRoutingModule,
    HttpClientModule,         // Ensure HttpClientModule is imported
    CoreModule,
    SharedModule,
    FormsModule,
    StoreModule.forRoot(reducers),
    AuthModule,
    AuthRoutingModule,
    HeaderComponent,
  ],
  providers: [
    provideClientHydration(), // Optional, only if using SSR
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
