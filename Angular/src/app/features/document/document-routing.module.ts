import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListDocumentComponent } from './components/list-document/list-document.component';
import { AddDocumentComponent } from './components/add-document/add-document.component';
import { UpdateDocumentComponent } from './components/update-document/update-document.component';
import { AddValidationComponent } from './components/add-validation/add-validation.component';
import { ListValidationComponent } from './components/list-validation/list-validation.component';
import { UpdateValidationComponent } from './components/update-validation/update-validation.component';

const routes: Routes = [
  {path:'',redirectTo:'list',pathMatch:'full'},
  {path:'list',component:ListDocumentComponent},
  {path:'add',component:AddDocumentComponent},
  {path:'update/:idDocument',component:UpdateDocumentComponent},
  {path:'validate/:idDocument',component:AddValidationComponent},
  {path:'list-validation',component:ListValidationComponent},
  {path: 'update-validation/:idValidation', component:UpdateValidationComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DocumentRoutingModule { }
