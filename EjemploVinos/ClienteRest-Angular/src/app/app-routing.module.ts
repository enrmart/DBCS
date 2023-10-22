import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import{ VinoListarComponent } from './vino-listar/vino-listar.component';
import{ EditarVinoComponent } from './editar-vino/editar-vino.component';

const routes: Routes = [
  {path: 'vinos', component:VinoListarComponent},
  {path:'vinos/:id/editar', component:EditarVinoComponent},
  {path:'vinos/nuevo', component:EditarVinoComponent},
  {path:'**', redirectTo:'vinos', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
