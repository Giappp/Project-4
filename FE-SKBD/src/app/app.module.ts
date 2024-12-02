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
  provideHttpClient,
  withFetch,
  withInterceptorsFromDi,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { StoreModule } from '@ngrx/store';
import { reducers } from './app.state';
import { FooterComponent } from './layout/footer/footer.component';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { HomeComponent } from './page/home/home.component';
import { CoreModule } from './core/core.module';
import { AuthModule } from './auth/auth.module';
import { AuthInterceptor } from './core/interceptor/auth.interceptor';
import { HomeLayoutComponent } from './layout/home-layout/home-layout.component';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { HeaderComponent } from './layout/header/header.component';
import {
  BrowserAnimationsModule,
  provideAnimations,
} from '@angular/platform-browser/animations';
import { AuthRoutingModule } from './auth/auth-routing.module';

registerLocaleData(en);

@NgModule({
  declarations: [AppComponent, HomeLayoutComponent, AuthLayoutComponent],
  imports: [
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    FormsModule,
    FooterComponent,
    HomeComponent,
    AuthModule,
    AuthRoutingModule,
    StoreModule.forRoot(reducers),
    HeaderComponent,
  ],
  providers: [
    provideAnimationsAsync(),
    provideAnimations(),
    provideHttpClient(withFetch(), withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  schemas: [],
})
export class AppModule {}
