import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { FirebaseService } from 'src/app/services/firebase.service';
import { LinkComponent } from './link/link.component';

@Component({
  selector: 'app-recurso-url',
  templateUrl: './recurso-url.component.html',
  styleUrls: ['./recurso-url.component.css']
})
export class RecursoUrlComponent implements OnInit {

  input: string;
  columns: string[] = ['asegurado', 'email', "telefono", "placa", "opciones"];
  
  dataSource: MatTableDataSource<any>;
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private firebaseService: FirebaseService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.firebaseService.col$('inspections').subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort; 
      this.dataSource.paginator = this.paginator;
    });
  }

  filtrar(input: string): void{
    this.dataSource.filter = input.trim().toLowerCase();
  }

  limpiar(){
    this.input = '';
    this.filtrar(this.input);
  }

  openDialog(url: any){
    this.dialog.open(LinkComponent, {
      data: url,
      width: '55%',
      height: '300px'
      });
  }
}
