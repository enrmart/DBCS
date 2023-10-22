import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { VinoListarComponent } from './vino-listar/vino-listar.component';
import { EditarVinoComponent } from './editar-vino/editar-vino.component';
import { ClienteApiRestService } from './shared/cliente-api-rest.service';
import { DataService } from './shared/data.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    VinoListarComponent,
    EditarVinoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ClienteApiRestService,
    DataService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
