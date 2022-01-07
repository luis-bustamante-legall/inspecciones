import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection, AngularFirestoreDocument }
  from '@angular/fire/firestore';
import { Chats } from '../models/collections/chats';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import firebase from "firebase/app";
import "firebase/firestore";
import "firebase/auth";
import * as moment from 'moment';
import { APPROVED, AVAILABLE, COLLECTION_BRANDS, COLLECTION_CHATS, COLLECTION_INSPECTION, COLLECTION_NOTIFICATION, ORD_ASC, REJECTED, SOURCE_INSPECTOR }
  from 'src/app/utils/constantes';

type CollentionPredicate<T> = string | AngularFirestoreCollection;
type DocumentPredicate<T> = string | AngularFirestoreDocument;

@Injectable({
  providedIn: 'root'
})

export class FirebaseService {

  datoInspe: any;
  listaInsp: any[];

  constructor(private firestore: AngularFirestore) { }

  loginAnonimo() {
    return firebase.auth().signInAnonymously();
  }

  loadNotification(): any {
    return this.firestore.collection(COLLECTION_NOTIFICATION)
      .doc('txW3eNPKZee8qMxQxpgz')
      .valueChanges();
  }

  loadChangesInspections(id: any): any {
    return this.firestore.collection(COLLECTION_INSPECTION)
      .doc(id).valueChanges();
  }

  loadChats(id: any) {
    return this.firestore.collection(COLLECTION_CHATS, ref =>
      ref.where('inspection_id', '==', id).
        orderBy('datetime', ORD_ASC)).valueChanges();
  }

  sendMessage(message: string, id: any) {
    let chat: Chats = {
      body: message, datetime: new Date(), read: false,
      source: SOURCE_INSPECTOR, inspection_id: id
    }
    return this.firestore.collection(COLLECTION_CHATS).add(chat);
  }

  /* Sincronizar postgres a firestore */
  syncNewInspections(dataApi: any) {
    let inspection: any = {
      brand_name: dataApi.marca,
      contact_address: dataApi.direccion,
      contact_email: dataApi.correo,
      contact_phone: '',
      contractor_name: dataApi.contacto,
      insured_name: dataApi.nombreApellido,
      model_name: dataApi.modelo,
      plate: dataApi.placa,
      informe_id: dataApi.id,
      status: dataApi.estado,
      schedule: [{
        datetime: new Date(dataApi.fechaProgramada),
        type: 'scheduled'
      }]
    }
    return this.firestore.collection(COLLECTION_INSPECTION).add(inspection);
  }

  syncNewInspectionsDateEmpty(dataApi: any) {
    let inspection: any = {
      brand_name: dataApi.marca,
      contact_address: dataApi.direccion,
      contact_email: dataApi.correo,
      contact_phone: '',
      contractor_name: dataApi.contacto,
      insured_name: dataApi.nombreApellido,
      model_name: dataApi.modelo,
      plate: dataApi.placa,
      informe_id: dataApi.id,
      status: dataApi.estado,
      schedule: [{
        datetime: new Date(),
        type: 'scheduled'
      }]
    }
    return this.firestore.collection(COLLECTION_INSPECTION).add(inspection);
  }


  private col<T>(ref: CollentionPredicate<T>, queryFn?): AngularFirestoreCollection {
    return typeof ref === "string" ? this.firestore.collection(ref, queryFn) : ref;
  }

  col$<T>(ref: CollentionPredicate<T>, queryFn?): Observable<any[]> {
    return this.col(ref, queryFn).snapshotChanges().pipe(
      map(docs => {
        return docs.map(d => {
          const data = d.payload.doc.data();
          const id = d.payload.doc.id;
          return { id, ...data }
        })
      })
    )
  }

  approvedPhoto(id: any) {
    this.firestore.collection('photos').doc(id).update({
      status: APPROVED
    });
  }

  rejectedFoto(id: any) {
    this.firestore.collection('photos').doc(id).update({
      status: REJECTED
    });
  }

  approvedVideo(id: any) {
    this.firestore.collection('videos').doc(id).update({
      status: APPROVED
    });
  }

  rejectedVideo(id: any) {
    this.firestore.collection('videos').doc(id).update({
      status: REJECTED
    });
  }

  updateSyncInspections(id_brand: any, id: any, dataApi: any) {
    if (dataApi.fechaProgramada == null) {
      this.firestore.collection('inspections').doc(id).update({
        brand_id: id_brand,
        brand_name: dataApi.marca,
        informe_id: dataApi.id,
        contact_address: dataApi.direccion,
        contact_email: dataApi.correo,
        contact_phone: '',
        contractor_name: dataApi.contacto,
        insured_name: dataApi.nombreApellido,
        model_name: dataApi.modelo,
        plate: dataApi.placa,
        status: dataApi.estado,
        token: id,
        schedule: [{
          datetime: new Date(),
          type: 'scheduled'
        }]
      });
    }
    else {
      this.firestore.collection('inspections').doc(id).update({
        brand_id: id_brand,
        brand_name: dataApi.marca,
        informe_id: dataApi.id,
        contact_address: dataApi.direccion,
        contact_email: dataApi.correo,
        contact_phone: '',
        contractor_name: dataApi.contacto,
        insured_name: dataApi.nombreApellido,
        model_name: dataApi.modelo,
        plate: dataApi.placa,
        status: dataApi.estado,
        token: id,
        schedule: [{
          datetime: new Date(dataApi.fechaProgramada),
          type: 'scheduled'
        }]
      });
    }
  }

  syncBrands(id: any, dataMarca: any, dataModelo?: any) {
    if (id != null) {
      this.firestore.collection(COLLECTION_BRANDS).doc(id).update({
        brand_name: dataMarca.marca,
        marca_id: dataMarca.idMarca,
        models: dataModelo
      })
    }
  }

  syncNewBrands(dataMarca: any, dataModelo?: any) {
    let brands: any = {
      brand_name: dataMarca.marca,
      marca_id: dataMarca.idMarca,
      models: ["a", "b"]
    }
    return this.firestore.collection(COLLECTION_BRANDS).add(brands);
  }

  updateCollection(dataApi: any) {
    this.firestore.collection('inspections').doc(dataApi.id).update({
      //brand_id: dataApi.brand_id,
      brand_name: dataApi.brand_name,
      contact_address: dataApi.contact_address,
      contact_email: dataApi.contact_email,
      contact_phone: dataApi.contact_phone,
      contractor_name: dataApi.contractor_name,
      insured_name: dataApi.insured_name,
      model_name: dataApi.model_name,
      informe_id: dataApi.informe_id,
      plate: dataApi.plate,
      status: dataApi.status,
      token: dataApi.id,
      schedule: dataApi.schedule
    });
  }

  updateActivarInspeccion(dataApi: any) {
    this.firestore.collection('inspections').doc(dataApi.id).update({
      //brand_id: dataApi.brand_id,
      brand_name: dataApi.brand_name,
      contact_address: dataApi.contact_address,
      contact_email: dataApi.contact_email,
      contact_phone: dataApi.contact_phone,
      contractor_name: dataApi.contractor_name,
      insured_name: dataApi.insured_name,
      model_name: dataApi.model_name,
      plate: dataApi.plate,
      informe_id: dataApi.informe_id,
      status: AVAILABLE,
      token: dataApi.id,
      schedule: dataApi.schedule
    });
  }

  deleteDocumentInspections(reference: any) {
    this.firestore.collection('inspections').doc(reference).delete();
  }
}
