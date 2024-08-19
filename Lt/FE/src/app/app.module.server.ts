import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppModule } from './app.module';
import { AppComponent } from './app.component';
import { PageModule } from './page/page.module';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    PageModule
  ],
  bootstrap: [AppComponent],
})
export class AppServerModule {}
