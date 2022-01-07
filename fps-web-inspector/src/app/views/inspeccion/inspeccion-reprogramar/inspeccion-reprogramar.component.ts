import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as moment from 'moment';
import { switchMap } from 'rxjs/operators';
import { InspeccionFiltro } from 'src/app/models/inspeccion-filtro';
import { ReprogramarInspeccion } from 'src/app/models/reprogramar-inspeccion';
import { FirebaseService } from 'src/app/services/firebase.service';
import { InspeccionService } from 'src/app/services/inspeccion.service';
import { COLLECTION_INSPECTION, MSJ_REPROGRAMAR_ESPERA, MSJ_REPROGRAMAR_OK, SIZE_ELEMENTS, TXT_NOTIF_OK } from 'src/app/utils/constantes';


@Component({
  selector: 'app-inspeccion-reprogramar',
  templateUrl: './inspeccion-reprogramar.component.html',
  styleUrls: ['./inspeccion-reprogramar.component.css']
})
export class InspeccionReprogramarComponent implements OnInit {

  idInspeccion: any;
  idEmpleado: any;
  idDistritoAtencion: any;
  direccionAtencion: any;
  usuarioCreacion:any;

  fechaCalendario: string;
  hora: any;
  fechaFinal: any;
  fechaFiltrada: any;
  collectionInspections: any[];
  fechaActual = new Date();
  estado: boolean;
  horaInicio: any;
  horaFinal: any;
  dataApi: any;


  constructor(private inspeccionService: InspeccionService, private firebaseService: FirebaseService,
    private dialogRef: MatDialogRef<InspeccionReprogramarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    this.fechaCalendario = this.data.fechaProgramada;
    this.hora = moment(this.data.fechaProgramada).format("HH:mm")
  }

  ngOnInit(): void {
    this.validarProgramacionFecha();
  }


  setTime(e): void {
    this.hora = e.target.value;
  }

  validarProgramacionFecha() {
    var time1 = this.hora;
    var time2 = moment(new Date()).format("HH:mm")
    var time1Date = moment(this.fechaCalendario).format("DD/MM/YYYY") + ' ' + time1;
    var time2Date = moment(new Date()).format("DD/MM/YYYY") + ' ' + time2;
    if (time1Date >= time2Date) {
      this.estado = false;
    } else {
      this.estado = true;
    }
  }

  getMask(): {
    mask: Array<string | RegExp>;
    keepCharPositions: boolean;
  } {
    return {
      mask: [/[0-2]/, this.hora && parseInt(this.hora[0]) > 1 ? /[0-3]/ : /\d/, ':', /[0-5]/, /\d/],
      keepCharPositions: true
    };
  }

  reprogramar() {
    this.sincronizarProgramacion();
    let request = new ReprogramarInspeccion();
    request.idInforme = this.data.id;
    request.usuarioCreacion = sessionStorage.getItem('token_id');
    this.fechaFiltrada = moment(this.fechaCalendario).format('YYYY-MM-DDT');
    this.fechaFinal = `${this.fechaFiltrada}${this.hora}`;
    request.fechaProgramada = moment(this.fechaFinal).format('YYYY-MM-DDTHH:mm:ss');
    this.inspeccionService.openNotification(MSJ_REPROGRAMAR_ESPERA, 'Ok');
    this.dialogRef.close();
    setTimeout(() => {
      this.guardarNuevaProgramacion(request);
    }, 1800);
  }

  sincronizarProgramacion() {
    this.firebaseService.col$(COLLECTION_INSPECTION, ref => ref.where('plate', '==', this.data.placa)).
      subscribe(resp => {
        this.dataApi = resp[0];
      });
  }

  cancelar() {
    this.dialogRef.close();
  }

  guardarNuevaProgramacion(request: ReprogramarInspeccion) {
    const insp = new InspeccionFiltro;
    this.inspeccionService.reprogramarInspeccion(request).pipe(switchMap(() => {
      return this.inspeccionService.listar(insp, 0, SIZE_ELEMENTS);
    })).subscribe(data => {
      this.inspeccionService.dataUpdate.next(data);
      this.inspeccionService.openNotification(`${MSJ_REPROGRAMAR_OK}${this.data.nombreApellido}`,
        TXT_NOTIF_OK);
    });
  }

}
