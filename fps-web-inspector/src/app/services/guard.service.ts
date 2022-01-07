import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { TOKEN_NAME } from '../utils/constantes';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate {

  constructor(private loginService: LoginService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    // Verificar si esta logueado
    let rpta = this.loginService.estaLogueado();
    if (!rpta) {
      this.loginService.cerrarSesion();
      return false;
    }
    else {
      const helper = new JwtHelperService();
      let token = sessionStorage.getItem(TOKEN_NAME);
      if (!helper.isTokenExpired(token)) {
        return true;
      } else {
        this.loginService.cerrarSesion();
        return false;
      }
    }

  }

}
