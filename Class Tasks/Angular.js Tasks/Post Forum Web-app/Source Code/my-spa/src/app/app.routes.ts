import { Routes } from '@angular/router';
import { ParentComponent } from './components/parent/parent.component';
import { TemplateFormComponent } from './forms/template-form/template-form.component';

export const routes: Routes = [
  { path: '', redirectTo: '/parent', pathMatch: 'full' },
  { path: 'parent', component: ParentComponent },
  { path: 'template-form', component: TemplateFormComponent }
];
