import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FirebaseService } from 'src/app/services/firebase.service';
import { COLLECTION_INSPECTION, LINK_BASE } from 'src/app/utils/constantes'

@Component({
  selector: 'app-link',
  templateUrl: './link.component.html',
  styleUrls: ['./link.component.css']
})
export class LinkComponent implements OnInit {

  hashCode: any;
  insuredName: any;
  outputPath: string;

  constructor( private dialogRef: MatDialogRef<LinkComponent>,
    private firebaseService: FirebaseService,
    @Inject(MAT_DIALOG_DATA) public data: string) { }

  ngOnInit(): void {
    this.obtenerCodigoHash();
    this.outputPath = 'Cargando.... '
    setTimeout(() => {
      this.outputPath=`${LINK_BASE}${this.hashCode}`;
      }, 1000);
  }

  limpiarEspaciosBlanco(texto: string): string{
    return texto.replace(/ /g,"_");
  }

  obtenerCodigoHash(){
    this.firebaseService.col$(COLLECTION_INSPECTION, ref => ref.where('informe_id', '==', this.data)).
    subscribe(resp => {
      console.log(this.data)
      this.hashCode = resp[0].id;
      this.insuredName = resp[0].insured_name
    });  
  }

  copyInputMessage(inputElement){
    inputElement.select();
    document.execCommand('copy');
    inputElement.setSelectionRange(0, 0);
    this,this.dialogRef.close();
  }

}
