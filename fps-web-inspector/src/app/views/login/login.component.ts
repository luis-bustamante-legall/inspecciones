import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { InspeccionService } from 'src/app/services/inspeccion.service';
import { LoginService } from 'src/app/services/login.service';
import { FirebaseService } from 'src/app/services/firebase.service'
import {
  MSJ_BIENVENIDA, TXT_NOTIF_OK, TOKEN_NAME, TOKEN_ID,
  ERROR_LOGIN_DESCRIPCION, ERROR_LOGIN_TITULO, ERROR_500_TITULO,
  ERROR_500_DESCRIPCION
} from 'src/app/utils/constantes';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  usuario: string;
  contrasenia: string;
  mensaje: string;
  error: string;

  constructor(private firebase: FirebaseService,  private loginService: LoginService, private inspeccionService: InspeccionService,
    private router: Router) { }

  iniciarSesion() {
    this.loginService.login(this.usuario, this.contrasenia).subscribe(data => {
      sessionStorage.setItem(TOKEN_NAME, data.token);
      const helper = new JwtHelperService();
      let decodedToken = helper.decodeToken(data.token);
      sessionStorage.setItem(TOKEN_ID, decodedToken.sub);
      sessionStorage.setItem('profile', decodedToken.groups[0]);
      this.router.navigate(['inspeccion']);
      this.firebase.loginAnonimo();
      this.inspeccionService.openNotification(`${MSJ_BIENVENIDA}${decodedToken.sub}`, TXT_NOTIF_OK);
    }, err => {
      if (err.status === 401) {
        this.mensajeUsuario(ERROR_LOGIN_TITULO, ERROR_LOGIN_DESCRIPCION);
      }
      if (err.status === 500) {
        this.mensajeUsuario(ERROR_500_TITULO, ERROR_500_DESCRIPCION);
      }
    });
  }

  mensajeUsuario(titulo: string, descripcion: string) {
    Swal.fire({ title: titulo, text: descripcion, icon: 'error' });
  }


}
