import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListDocumentComponent } from './list-document/list-document.component';
import { AddDocumentComponent } from './add-document/add-document.component';

const routes: Routes = [
  {path:'List',component:ListDocumentComponent},
  {path:'Add',component:AddDocumentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
