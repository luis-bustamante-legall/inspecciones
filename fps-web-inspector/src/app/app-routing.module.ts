import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RecursoUrlComponent } from './views/configuracion/recurso-url/recurso-url.component';
import { InspeccionEditarComponent } from './views/inspeccion/inspeccion-editar/inspeccion-editar.component';
import { InspeccionComponent } from './views/inspeccion/inspeccion.component';
import { LoginComponent } from './views/login/login.component';
import { GuardService } from './services/guard.service';
import { RedireccionComponent } from './views/configuracion/redireccion/redireccion.component';
import { InspeccionVerComponent } from './views/inspeccion/inspeccion-ver/inspeccion-ver.component';


const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  {
    path: 'inspeccion', component: InspeccionComponent,
    children: [
      { path: 'editar/:placa', component: InspeccionEditarComponent },
      { path: 'ver/:placa', component: InspeccionVerComponent}
    ], canActivate: [GuardService]
  },
  { path: 'notificacion', component: RecursoUrlComponent, canActivate: [GuardService] },
  { path: 'virtual', component: RedireccionComponent, pathMatch: 'prefix' },
  { path: 'login', component: LoginComponent },
  { path: '**', pathMatch: 'full', redirectTo: 'inspeccion' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
