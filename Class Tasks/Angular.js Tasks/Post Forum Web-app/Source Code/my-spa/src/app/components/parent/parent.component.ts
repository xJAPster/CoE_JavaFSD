import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ChildComponent } from '../child/child.component';
import { CustomPipe } from '../../pipes/custom.pipe';
import { DataService } from '../../services/data.service';

@Component({
  selector: 'app-parent',
  standalone: true,
  imports: [CommonModule, RouterModule, ChildComponent, CustomPipe],
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.css']
})
export class ParentComponent implements OnInit {
  posts: any[] = [];
  selectedMessage: string = '';

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.dataService.getPosts().subscribe(data => {
      this.posts = data;
    });
  }

  onChildClick(postId: number) {
    this.selectedMessage = `Post with ID ${postId} was clicked!`;
  }
}
