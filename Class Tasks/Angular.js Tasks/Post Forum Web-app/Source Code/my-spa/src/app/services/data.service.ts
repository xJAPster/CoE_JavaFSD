import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Post {
  id?: number;
  title: string;
  body: string;
}

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private apiUrl = 'https://jsonplaceholder.typicode.com/posts';
  private addedPosts: Post[] = [];

  // Posts observable to track API and added posts
  private postsSubject = new BehaviorSubject<Post[]>([]);
  public posts$ = this.postsSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadPosts();
  }

  // Loads posts from API and adds any locally added posts.
  loadPosts(): void {
    this.http.get<Post[]>(this.apiUrl).subscribe((apiPosts: Post[]) => {
      const posts = apiPosts.slice(0, 5).concat(this.addedPosts);
      this.postsSubject.next(posts);
    });
  }

  // Expose posts as an observable.
  getPosts(): Observable<Post[]> {
    return this.posts$;
  }

  // Add a new post and refresh the list.
  addPost(post: Post): void {
    post.id = Date.now(); // Simple unique id
    this.addedPosts.push(post);
    this.loadPosts();
  }
}
