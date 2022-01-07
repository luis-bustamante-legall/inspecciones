import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TOKEN_NAME } from '../utils/constantes';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  dataUpdate = new Subject<any[]>();
  endpoint = `${environment.AUTH_ENDPOINT}/login`;

  constructor(protected http: HttpClient, private router: Router) { }

  login(usuario: string, contrasenia: string) {
    const body = `username=${encodeURIComponent(usuario)}&password=${encodeURIComponent(contrasenia)}`;
    return this.http.post<any>(this.endpoint, body, {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
    });
  }

  estaLogueado() {
    let token = sessionStorage.getItem(TOKEN_NAME);
    return token != null;
  }

  cerrarSesion(){
    sessionStorage.clear();
    this.router.navigate(['login']);
  }
}
