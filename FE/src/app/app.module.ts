import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { StoreModule } from '@ngrx/store';
import { reducers } from './store/app.state';
import { FooterComponent } from './layout/footer/footer.component';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { HomeComponent } from './page/home/home.component';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    FormsModule,
    FooterComponent,
    HomeComponent,
    StoreModule.forRoot(reducers),
    FontAwesomeModule
  ],
  providers: [provideAnimationsAsync(), provideHttpClient(withFetch())],
  bootstrap: [AppComponent],
  schemas: [],
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    // Add biểu tượng vào IconLibrary
    library.addIcons(faPlus);
  }
}
