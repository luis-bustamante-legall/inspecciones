import {MatPaginatorIntl } from '@angular/material/paginator';
import { Injectable } from '@angular/core';

@Injectable()
export class MatPaginatorImpl extends MatPaginatorIntl {

    constructor() {
        super();
        this.nextPageLabel = 'Siguiente';
        this.previousPageLabel = 'Anterior';
        this.itemsPerPageLabel = 'Items por página';
        this.firstPageLabel = 'Primera página;'
        this.lastPageLabel = 'Ultima página';
      }
}
