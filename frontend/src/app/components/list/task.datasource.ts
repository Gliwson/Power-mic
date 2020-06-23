import {DataSource} from '@angular/cdk/table';
import {Task, TodoListResponse} from '../../models/task';
import {CollectionViewer} from '@angular/cdk/collections';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {TaskService} from '../../services/task.service';
import {catchError, finalize} from 'rxjs/operators';

export class TaskDatasource implements DataSource<Task> {

    private todoSubject = new BehaviorSubject<Task[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);
    public counter$ = this.countSubject.asObservable();

    constructor(private todoService: TaskService) {
    }

    connect(collectionViewer: CollectionViewer): Observable<Task[]> {
        return this.todoSubject.asObservable();
    }

    getTasks(): Observable<Task[]> {
        return this.todoSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.todoSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }

    loadTask(pageNumber = 0, pageSize = 20) {
        this.loadingSubject.next(true);
        this.todoService.listTodos({page: pageNumber, size: pageSize})
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((result: TodoListResponse) => {
                    this.todoSubject.next(result.content);
                    this.countSubject.next(result.totalElements);
                }
            );
    }

}
