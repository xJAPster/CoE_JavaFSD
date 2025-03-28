import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DataService } from '../../services/data.service';

@Component({
  selector: 'app-template-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './template-form.component.html',
  styleUrls: ['./template-form.component.css']
})
export class TemplateFormComponent {
  model = { title: '', body: '' };

  constructor(private dataService: DataService) {}

  onSubmit(form: any) {
    // Pass a copy of the model so resetting the form doesn't clear the stored data.
    this.dataService.addPost({ ...this.model });
    alert('Post submitted!');
    form.reset();
  }
}
