import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { InspeccionFiltro } from '../models/inspeccion-filtro';
import { environment } from 'src/environments/environment';
import { Subject } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NotificacionComponent } from '../views/notificacion/notificacion.component';

@Injectable({
  providedIn: 'root'
})
export class InspeccionService {

  URL_BASE =  `${environment.BASE_ENDPOINT}/inspecciones`;
  dataUpdate = new Subject<any[]>();


  constructor(protected http: HttpClient, private snackBar: MatSnackBar) { }

  listar(request: InspeccionFiltro, pagina: number, size: number){
    return this.http.post<any>(`${this.URL_BASE}/pagina/${pagina}/size/${size}`, request);
  }

  find(idAsegurado: number) {
    return this.http.get<any>(`${this.URL_BASE}/${idAsegurado}`)
  }

  cargarMarca(){
    return this.http.get<any>(`${environment.BASE_ENDPOINT}/marcas`);
  }

  cargarModelo(){
    return this.http.get<any>(`${environment.BASE_ENDPOINT}/modelos`);
  }

  buscarMarca(idMarca: any) {
    return this.http.get<any>(`${environment.BASE_ENDPOINT}/marcas/${idMarca}`);
  }

  buscarModelo(idModelo: any) {
    return this.http.get<any>(`${environment.BASE_ENDPOINT}/modelos/${idModelo}`);
  }

  buscarModeloPorMarca(idMarca: any) {
    return this.http.get<any>(`${environment.BASE_ENDPOINT}/modelos/marca/${idMarca}`);
  }

  finalizarInspeccion(request: any){
    return this.http.put<any>(`${this.URL_BASE}/finalizar-inspeccion`, request);
  }

  actualizarEstadoInspeccion(id: any, estado: any){
    return this.http.put<any>(`${this.URL_BASE}/${id}/status/${estado}`, {});
  }

  reprogramarInspeccion(request: any){
    return this.http.put<any>(`${this.URL_BASE}/reprogramar`, request);
  }

  openNotification(message: string, buttonText: string){
    this.snackBar.openFromComponent(NotificacionComponent, {
      data: {
        message: message,
        buttonText: buttonText
      },
      duration: 7000,
      horizontalPosition: "end",
      verticalPosition: "top",
      panelClass: 'notification'
    })
  }
}
