import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AttributeDirective } from '../../directives/attribute.directive';
import { CustomPipe } from '../../pipes/custom.pipe';

@Component({
  selector: 'app-child',
  standalone: true,
  imports: [CommonModule, AttributeDirective, CustomPipe],
  templateUrl: './child.component.html',
  styleUrls: ['./child.component.css']
})
export class ChildComponent {
  @Input() post: any;
  @Output() childClicked = new EventEmitter<number>();

  handleClick() {
    this.childClicked.emit(this.post.id);
  }
}
