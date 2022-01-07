import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';


@Component({
  selector: 'app-modal-fotos',
  templateUrl: './modal-fotos.component.html',
  styleUrls: ['./modal-fotos.component.css']
})
export class ModalFotosComponent implements OnInit {

  url: any;

  constructor( private dialogRef: MatDialogRef<ModalFotosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.url = this.data;
    console.log(this.url);
  }

}
