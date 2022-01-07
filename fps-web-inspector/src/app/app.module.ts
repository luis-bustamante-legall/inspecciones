import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularFireModule } from '@angular/fire';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InspeccionComponent } from './views/inspeccion/inspeccion.component';
import { InspeccionEditarComponent } from './views/inspeccion/inspeccion-editar/inspeccion-editar.component';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { environment } from 'src/environments/environment';
import { NotificacionComponent } from './views/notificacion/notificacion.component';
import { ChatComponent } from './views/chat/chat.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { ModalFotosComponent } from './views/inspeccion/inspeccion-editar/modal-fotos/modal-fotos.component';
import { ModalVideosComponent } from './views/inspeccion/inspeccion-editar/modal-videos/modal-videos.component';
import { RecursoUrlComponent } from './views/configuracion/recurso-url/recurso-url.component';
import { LinkComponent } from './views/configuracion/recurso-url/link/link.component';
import { InspeccionReprogramarComponent } from './views/inspeccion/inspeccion-reprogramar/inspeccion-reprogramar.component';
import { LoginComponent } from './views/login/login.component';
import { JwtModule } from "@auth0/angular-jwt";
import { TOKEN_NAME } from './utils/constantes';
import { MatIconModule } from '@angular/material/icon';
import { RedireccionComponent } from './views/configuracion/redireccion/redireccion.component';

import { PdfMakeWrapper } from 'pdfmake-wrapper';
import pdfFonts from "pdfmake/build/vfs_fonts";
import { InspeccionVerComponent } from './views/inspeccion/inspeccion-ver/inspeccion-ver.component'; 

PdfMakeWrapper.setFonts(pdfFonts);

export function tokenGetter() {
  return sessionStorage.getItem(TOKEN_NAME);
}

@NgModule({
  declarations: [
    AppComponent,
    InspeccionComponent,
    InspeccionEditarComponent,
    NotificacionComponent,
    ChatComponent,
    ModalFotosComponent,
    ModalVideosComponent,
    RecursoUrlComponent,
    LinkComponent,
    InspeccionReprogramarComponent,
    LoginComponent,
    RedireccionComponent,
    InspeccionVerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MaterialModule,
    MatIconModule,
    ReactiveFormsModule,
    FormsModule,
    FlexLayoutModule,
    FontAwesomeModule,
    AngularFirestoreModule,
    AngularFireModule.initializeApp(environment.firebase),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: [environment.DOMAIN_TOKEN],
        disallowedRoutes: ["http://example.com/examplebadroute/"],
      },
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
