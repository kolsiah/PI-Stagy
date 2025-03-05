import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListDocumentComponent } from './components/list-document/list-document.component';
import { AddDocumentComponent } from './components/add-document/add-document.component';
import { UpdateDocumentComponent } from './components/update-document/update-document.component';

const routes: Routes = [
  {path:'',redirectTo:'list',pathMatch:'full'},
  {path:'list',component:ListDocumentComponent},
  {path:'add',component:AddDocumentComponent},
  {path:'update/:idDocument',component:UpdateDocumentComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentRoutingModule { }
