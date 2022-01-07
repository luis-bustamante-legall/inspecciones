import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Marca } from 'src/app/models/marca';
import { InspeccionService } from 'src/app/services/inspeccion.service';
import { FirebaseService } from 'src/app/services/firebase.service';
import { MatDialog } from '@angular/material/dialog';
import { ModalFotosComponent } from './modal-fotos/modal-fotos.component';
import { COLLECTION_INSPECTION, COLLECTION_PHOTOS, COLLECTION_VIDEOS, COMPLETE, DB_COMPLETE, FORMATO_FECHA_NOTIFICACION, MSJ_FOTO_RECHAZADA, SIZE_ELEMENTS } from 'src/app/utils/constantes';
import { InspeccionFiltro } from 'src/app/models/inspeccion-filtro';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-inspeccion-editar',
  templateUrl: './inspeccion-editar.component.html',
  styleUrls: ['./inspeccion-editar.component.css']
})
export class InspeccionEditarComponent implements OnInit {

  form: FormGroup;
  status: string;
  edicion: boolean;
  placa: string;
  plate: string;
  asegurado: string;
  contratante: string;
  contacto: string;
  direccion: string;
  inspectionsId: string;
  nombreMarca: string;
  nombreModelo: string;
  correo: string;
  marca$: Marca[];
  modelo$: any[];
  modelos: any[];
  dataInspections: any;
  dataPhotos: any[];
  dataVideos: any[];
  selectMarca: any;
  selectModelo: any;
  marcaFiltrada: any;
  modeloFiltrado: any;
  dataInspeccion: any[];

  constructor(private inspeccionService: InspeccionService, private router: Router,
    private firebaseService: FirebaseService, private route: ActivatedRoute,
    private dialog: MatDialog) {
    this.capturarRuta();
  }

  ngOnInit(): void {
    this.cargarMarca();
  }

  capturarRuta(): void {
    this.route.params.subscribe((data: Params) => {
      this.placa = data.placa;
      this.getByPlate();
      this.obtenerInspeccion(this.placa);
    });
  }

  obtenerInspeccion(pla: any) {
    const insp = new InspeccionFiltro;
    insp.placa = pla;
    this.inspeccionService.listar(insp, 0, SIZE_ELEMENTS).subscribe(data => {
      this.dataInspeccion = data.list;
    });
  }

  getByPlate() {
    this.firebaseService.col$(COLLECTION_INSPECTION, ref => ref.where('plate', '==', this.placa)).
      subscribe(resp => {
        this.dataInspections = resp[0];
        this.plate = this.dataInspections.plate;
        this.asegurado = this.dataInspections.insured_name;
        this.contratante = this.dataInspections.insured_name;
        this.contacto = this.dataInspections.contractor_name;
        this.direccion = this.dataInspections.contact_address;
        this.correo = this.dataInspections.contact_email;
        this.inspectionsId = this.dataInspections.id;
        this.nombreMarca = this.dataInspections.brand_name;
        this.nombreModelo = this.dataInspections.model_name;
        this.getPhotos(this.inspectionsId);
        this.getVideos(this.inspectionsId);
        this.buscarNombreMarca(this.nombreMarca, this.nombreModelo);
      });
  }

  updateCollectionInspection() {
    this.dataInspections.contact_address = this.direccion;
    this.dataInspections.contact_email = this.correo;
    this.firebaseService.updateCollection(this.dataInspections);
  }

  getPhotos(inspectionId: string) {
    this.firebaseService.col$(COLLECTION_PHOTOS, ref => ref.where('inspection_id', '==', inspectionId)).
      subscribe(resp => {
        this.dataPhotos = resp;
      });
  }

  getVideos(inspectionId: string) {
    this.firebaseService.col$(COLLECTION_VIDEOS, ref => ref.where('inspection_id', '==', inspectionId)).
      subscribe(resp => {
        this.dataVideos = resp;
      });
  }

  buscarNombreMarca(nombre: string, modelo: any) {
    setTimeout(() => {
      this.marcaFiltrada = this.marca$.filter(x => x.marca == nombre);
      this.selectMarca = this.marcaFiltrada[0].idMarca;
      this.buscarNombreModelo(this.selectMarca, modelo);
    }, 1000);
  }

  buscarNombreModelo(id: any, modelo: any) {
    this.inspeccionService.buscarModeloPorMarca(id).subscribe(data => {
      this.modelo$ = data.filter(x => x.modelo == modelo);
      this.selectModelo = this.modelo$[0].idModelo;
    });
  }

  openImage(url: any) {
    this.dialog.open(ModalFotosComponent, {
      data: url,
      width:'1px'
    })
  }

  cargarMarca(): void {
    this.inspeccionService.cargarMarca().subscribe(data => {
      this.marca$ = data.list;
    });
  }

  aprobarFoto(id: any) {
    this.firebaseService.approvedPhoto(id);
  }

  rechazarFoto(id: any) {
    this.firebaseService.rejectedFoto(id);
  }

  aprobarVideo(id: any) {
    this.firebaseService.approvedVideo(id);
  }

  rechazarVideo(id: any) {
    this.firebaseService.rejectedVideo(id);
  }

  filtrarModelo(id: any) {
    this.inspeccionService.buscarModeloPorMarca(id).subscribe(x => {
      this.modelo$ = x;
    });
  }

  finalizarInspeccion() {
    Swal.fire({
      title: 'Desea Finalizar la inspeccion?',
      text: "Confirmar y guardar los cambios",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Si, Finalizar',
      width: '380px'
    }).then((result) => {
      if (result.isConfirmed) {
        let data: any = {
          idInspeccion: this.dataInspeccion[0].idInspeccion,
          direccion: this.direccion,
          idVehiculo: this.dataInspeccion[0].idVehiculoAsegurado,
          idModelo: this.selectModelo,
          idAsegurado: this.dataInspeccion[0].idAsegurado,
          correo: this.correo,
          estado: DB_COMPLETE,
          latitude: this.dataInspections.location.w_,
          longitude: this.dataInspections.location.E_,
          usuarioModificacion: sessionStorage.getItem('token_id'),
        }
        this.finalizarInspeccionFirebase();
        console.log('DATA QUE SE ENVIA AL FINALIZAR:', data)
        this.inspeccionService.finalizarInspeccion(data).subscribe(() => {
          Swal.fire({
            icon: 'success',
            title: 'Hecho',
            text: 'La inspeccion ha finalizado con exito',
            showConfirmButton: false,
            timer: 1900
          })
          const insp = new InspeccionFiltro;
          this.inspeccionService.listar(insp, 0, SIZE_ELEMENTS).subscribe(data => {
            this.inspeccionService.dataUpdate.next(data);
          });
          this.router.navigate(['inspeccion']);
        });
      }
    })
  }

  finalizarInspeccionFirebase() {
    this.dataInspections.status = COMPLETE;
    this.firebaseService.updateCollection(this.dataInspections);
  }

  getStatus(): string {
      return this.dataInspections.status;
  }

  cargarNotificaciones() {
    this.firebaseService.loadChangesInspections(this.dataInspections.token).subscribe(x => {
      this.loadNotification();
    });
  }

  loadNotification() {
    this.inspeccionService.openNotification(`El asegurado acaba de finalizar la inspeccion`, `OK`);
  }

}
