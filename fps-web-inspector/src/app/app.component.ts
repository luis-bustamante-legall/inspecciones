import { Component, OnInit } from '@angular/core';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'fps-web-inspector';

  user: any;
  profile: any;
  openMenu = false;

  constructor(public loginService: LoginService) {
    this.user = sessionStorage.getItem('token_id');
    this.profile = sessionStorage.getItem('profile');
  }

  cerrarSesion() {
    this.loginService.cerrarSesion();
  }

  iniciarSession(): boolean {
    let rpta = this.loginService.estaLogueado();
    if (!rpta) {
      return false;
    } else {
      this.openMenu = true;
      return true;
    }
  }

  isLargeScreen() {
    const width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (width > 820) {
      return true;
    } else {
      return false;
    }
  }
}
