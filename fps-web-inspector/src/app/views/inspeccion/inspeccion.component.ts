import { Component, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { PdfMakeWrapper, Table, Txt } from 'pdfmake-wrapper';
import { interval, Subscription } from 'rxjs';
import { InspeccionFiltro } from 'src/app/models/inspeccion-filtro';
import { FirebaseService } from 'src/app/services/firebase.service';
import { InspeccionService } from 'src/app/services/inspeccion.service';
import {
  COLLECTION_BRANDS, COLLECTION_INSPECTION, DB_AVAILABLE, FORMATO_FECHA_NOTIFICACION, MSJ_ACTIVAR_INSP_ESPERA,
  MSJ_ACTIVAR_INSP_OK, NUEVO_REGISTRO, SIZE_ELEMENTS, TXT_NOTIF_OK
} from 'src/app/utils/constantes';
import Swal from 'sweetalert2';
import { LinkComponent } from '../configuracion/recurso-url/link/link.component';
import { InspeccionReprogramarComponent } from './inspeccion-reprogramar/inspeccion-reprogramar.component';



@Component({
  selector: 'app-inspeccion',
  templateUrl: './inspeccion.component.html',
  styleUrls: ['./inspeccion.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class InspeccionComponent implements OnInit, OnDestroy {

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataSource: MatTableDataSource<any>;

  columns: string[] = ['numeroTramite', 'codigoInspeccion', 'placa', 'contactoInspeccion', 'asegurado',
    "telefono", "fechaHora", "estado", "opciones"];

  anulado = true;
  pendiente = false;
  placa: string;
  codigoInspeccionLegall: string;
  estadoSeleccionado: string;
  fechaDesde: string;
  fechaHasta: string;

  cantidad: number = 0;

  dataConsultaInspeccion: any;
  dataInspeccion: any[];
  dataMarca: any[];
  dataModelo = [];
  modelos: any[];

  collectionInspections: any[];
  collectionBrands: any[];
  coleccionInspeccionFiltrada: any[];
  coleccionMarcaFiltrada: any[];

  suscripcionContador: Subscription;

  estadoInspeccion: boolean = true;

  inspeccionCaducada = false;

  dataApi: any;

  estado: { label: string, value: any }[];

  constructor(private inspeccionService: InspeccionService, public route: ActivatedRoute,
    private firebaseService: FirebaseService, private dialog: MatDialog) {
    this.cargarNotificaciones();
  }

  ngOnInit(): void {
    this.fechaDesde = null;
    this.obtenerColeccionInspeccion();
    this.obtenerColeccionMarca();
    this.listarModelo();
    setTimeout(() => {
      this.actualizarLista();
      this.cargarMarca();
      this.listarInspeccion();
      this.cargarEstado();
    }, 1000);
    this.validarFechaCaducada();
  }

  cargarNotificaciones() {
    this.firebaseService.loadNotification().subscribe(x => {
      if (moment(x.current_date).isSame(moment(new Date()).format(FORMATO_FECHA_NOTIFICACION))) {
        if (x.insured_name === NUEVO_REGISTRO)
          this.cargarNotificacionNuevoRegistro();
        else 
          this.cargarNotificacionReprogramar(x.insured_name, x.plate);
      }
      setTimeout(() => {
        this.filtrarLista();
      }, 1000);
    });
  }

  listarInspeccion(): void {
    const insp = new InspeccionFiltro;
    this.inspeccionService.listar(insp, 0, SIZE_ELEMENTS).subscribe(data => {
      this.cantidad = data.total;
      this.dataSource = new MatTableDataSource(data.list);
      //this.dataSource.paginator = this.paginator;
      this.dataInspeccion = data.list;
      setTimeout(() => {
        this.obtenerPlaca();
      }, 1000);
    });
  }

  cargarMarca(): void {
    this.inspeccionService.cargarMarca().subscribe(data => {
      this.dataMarca = data.list;
      setTimeout(() => {
        this.obtenerIdMarca();
      }, 1000);
    });
  }

  obtenerIdMarca() {
    for (let index = 0; index < this.dataMarca.length; index++) {
      let idMarca = this.dataMarca[index].idMarca;
      this.cargarModelo(index, idMarca, this.dataMarca[index]);
    }
  }

  listarModelo() {
    this.inspeccionService.cargarModelo().subscribe(data => {
      this.dataModelo = data.list;
    });
  }

  cargarModelo(index: any, idMarca: any, dataMarca: any): void {
    this.modelos = this.dataModelo.filter(x => x.idMarca == idMarca).map(x => {
      return x.modelo;
    });
    //this.sincronizarMarca(idMarca, dataMarca, this.modelos);
  }

  sincronizarMarca(idMarca: any, dataMarca?: any, data?: any) {
    if (this.collectionBrands.filter(x => x.marca_id == idMarca).length != 0) {
      this.coleccionMarcaFiltrada = this.collectionBrands.filter(x => x.marca_id == idMarca);
      this.firebaseService.syncBrands(this.coleccionMarcaFiltrada[0].id, dataMarca, data);
    } else {
      this.firebaseService.syncNewBrands(dataMarca, data);
    }
  }

  obtenerColeccionInspeccion() {
    this.firebaseService.col$(COLLECTION_INSPECTION).subscribe(data => {
      this.collectionInspections = data;
    });
  }

  obtenerColeccionMarca() {
    this.firebaseService.col$(COLLECTION_BRANDS).subscribe(data => {
      this.collectionBrands = data;
    });
  }

  cargarNotificacionReprogramar(asegurado: string, placa: string) {
    this.inspeccionService.openNotification(`El asegurado ${asegurado}, acaba de reprogramar la inspección`, `Placa: ${placa}`);
  }
  cargarNotificacionNuevoRegistro() {
    this.inspeccionService.openNotification(`Se acaba de registrar una nueva inspeccion`, 'OK');
  }

  obtenerPlaca() {
    for (let index = 0; index < this.dataInspeccion.length; index++) {
      let placa = this.dataInspeccion[index].placa;
      let marca = this.dataInspeccion[index].marca;
      this.sincronizarInspeccion(marca, placa, this.dataInspeccion[index]);
    }
  }

  sincronizarInspeccion(marca: string, placa: string, dataInspeccion?: any) {
    if (this.collectionInspections.filter(x => x.plate == placa).length != 0) {
      this.coleccionInspeccionFiltrada = this.collectionInspections.filter(x => x.plate == placa);
      let brandFilter: any[] = this.collectionBrands.filter(x => x.brand_name == marca);
      this.firebaseService.updateSyncInspections(brandFilter[0].id, this.coleccionInspeccionFiltrada[0].id,
        dataInspeccion);
    }
    else {
      //En caso la fecha de programacion venga vacia
      if (dataInspeccion.fechaProgramada != undefined) {
        this.firebaseService.syncNewInspections(dataInspeccion);
      } else {
        this.firebaseService.syncNewInspectionsDateEmpty(dataInspeccion);
      }
    }
  }

  filtrarLista() {
    this.listarInspeccion();
    const insp = new InspeccionFiltro();
    insp.placa = this.placa;
    insp.codigoInspeccionLegall = this.codigoInspeccionLegall;
    insp.estado = this.estadoSeleccionado;
    if (this.fechaDesde !== null && this.fechaHasta == null) {
      insp.fechaDesde = moment(this.fechaDesde).format("YYYY-MM-DDTHH:mm:ss");
      insp.fechaHasta = null;
    }
    else if (this.fechaDesde !== null && this.fechaHasta !== null) {
      insp.fechaDesde = moment(this.fechaDesde).format("YYYY-MM-DDTHH:mm:ss");
      insp.fechaHasta = moment(this.fechaHasta).format("YYYY-MM-DDTHH:mm:ss");
    }
    else {
      insp.fechaDesde = null;
      insp.fechaHasta = null;
    }
    this.inspeccionService.listar(insp, 0, SIZE_ELEMENTS).subscribe(data => {
      this.inspeccionService.dataUpdate.next(data);
    })
  }

  actualizarLista() {
    this.inspeccionService.dataUpdate.subscribe(data => {
      this.dataConsultaInspeccion = data;
      this.dataSource = new MatTableDataSource(this.dataConsultaInspeccion.list);
    });
  }

  limpiarCampos() {
    this.placa = "";
    this.codigoInspeccionLegall = "";
    this.fechaDesde = null;
    this.fechaHasta = null
    this.estadoSeleccionado = null;
    this.filtrarLista();
  }


  actualizarEstado(p) {
    this.sincronizarEstado(p);
    Swal.fire({
      title: 'Desea habilitar la inspeccion?',
      text: "Confirme para habilitar la inspeccion",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      cancelButtonText: 'Cancelar',
      confirmButtonText: 'Si, Activar',
      width: '380px'
    }).then((result) => {
      if (result.isConfirmed) {
        this.inspeccionService.openNotification(`${MSJ_ACTIVAR_INSP_ESPERA}`, TXT_NOTIF_OK);
        this.inspeccionService.actualizarEstadoInspeccion(p.idInspeccion, DB_AVAILABLE).subscribe(() => {
          this.ngOnInit();
          this.inspeccionService.openNotification(`${MSJ_ACTIVAR_INSP_OK}${p.nombreApellido}`, TXT_NOTIF_OK);
          setTimeout(() => {
            this.updateCollectionInspection();
          }, 1700);
        })
      }
    })
  }
  sincronizarEstado(p) {
    this.firebaseService.col$(COLLECTION_INSPECTION, ref => ref.where('plate', '==', p.placa)).
      subscribe(resp => {
        this.dataApi = resp[0];
      });
  }

  updateCollectionInspection() {
    this.firebaseService.updateActivarInspeccion(this.dataApi);
  }

  openDialog(data: any) {
    this.isLargeScreen(data);
  }

  isLargeScreen(data: any) {
    const width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (width > 820) {
      this.dialog.open(InspeccionReprogramarComponent, {
        data: data,
        width: '30%',
        height: '310px'
      });
    } else {
      this.dialog.open(InspeccionReprogramarComponent, {
        data: data,
        width: 'auto',
        height: '310px'
      });
    }
  }

  cargarEstado(): void {
    this.estado = [
      { label: "Todos --", value: "" },
      { label: "Pendiente", value: "Pendiente" },
      { label: "Disponible", value: "Digitado" },
      { label: "Terminado", value: "Terminado" }]
  }

  paginaSiguiente(e: any) {
    const insp = new InspeccionFiltro;
    this.inspeccionService.listar(insp, e.pageIndex, e.pageSize).subscribe(data => {
      this.cantidad = data.total;
      this.dataSource = new MatTableDataSource(data.list);
      this.dataInspeccion = data.list;
      setTimeout(() => {
        this.obtenerPlaca();
      }, 1000);
    });
  }

  generarPdf(p: any) {
    const pdf = new PdfMakeWrapper();
    pdf.pageSize('A4');
    pdf.pageMargins([70, 170]);
    pdf.info({
      title: 'Reporte Inspeccion',
      author: 'Legall',
      subject: '2021 - Todos los derechos reservados',
    });

    pdf.styles({
      title: {
        fontSize: 22,
        background: '#F2F4F4',
        bold: true,
        color: '#1F618D',
        alignment: 'center',
        decoration: 'underline',
      },
      content: {
        fontSize: 14,
        color: '#515A5A',
        margin: 8
      }
    });

    pdf.add(
      new Txt('Detalle de la inspeccion').style('title').end
    );
    pdf.add(' ');
    pdf.add([
      new Txt('Asegurado:  ' + p.nombreApellido).style('content').end,
    ]
    );
    pdf.add(' ');
    pdf.add(
      new Table([
        ['Codigo Inspeccion', '- ' + p.codigoInspeccion],
        ['Codigo Inspeccion Legall', '- ' + p.codigoInspeccionLegall],
        ['Numero Tramite', '- ' + p.numeroTramite],
        ['Contacto', '- ' + p.contacto],
        ['Contratante', '- ' + p.contratanteRazonSocial],
        ['Correo', '- ' + p.correo],
        ['Direccion', '- ' + p.direccion],
        ['Estado', '- Terminado'],
        ['Codigo estado', '- ' + p.idEstado],
        ['ID-Informe', '- ' + p.id],
        ['ID-Distrito', '- ' + p.idDistrito],
        ['ID-Inspeccion', '- ' + p.idInspeccion],
        ['Marca Vehiculo', '- ' + p.marca],
        ['Modelo Vehiculo', '- ' + p.modelo],
        ['Placa', '- ' + p.placa],
        ['Fecha Programada', '- ' + p.fechaProgramada],
        ['Telefono', '- ' + p.telefono],
        ['Usuario', '- ' + p.usuarioCreacion],
      ]).widths([108, '*']).end
    )

    // new Img('http://www.tratamientodeaire.pe/img/clientes/legall.png').build().then( img => {
    //   pdf.header(img) 
    // });

    pdf.create().open();
  }


  validarFechaCaducada() {
    const contador = interval(2000);
    this.suscripcionContador = contador.subscribe((n) => {
      //moment([2021, 0, 29]).toNow();     // in 4 years
      this.dataSource.filteredData.forEach(x => {
        var exp = moment(moment(x.fechaProgramada).format("DD.MM.YYYY HH:mm:ss"), 'DD.MM.YYYY HH:mm:ss');
        if (moment().diff(exp, 'minutes') > 0 && x.estado != 'complete') {
          x.estado = 'caducado'
          // console.log('Tiene una inspeccion caducada hace: ', moment().diff(exp, 'hours'));
        }
      });
    });
  }

  ngOnDestroy() {
    this.suscripcionContador.unsubscribe();
  }

  compartirLink(url: any) {
    this.dialog.open(LinkComponent, {
      data: url,
      width: '40%',
      height: '270px'
    });
  }

}
