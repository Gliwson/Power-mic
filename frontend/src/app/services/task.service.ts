import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {CreateTask, Task} from '../models/task';

const headers = new HttpHeaders().set('Content-Type', 'application/json');
const apiUrl = environment.apiUrl + '/tasks';

@Injectable({
    providedIn: 'root'
})
export class TaskService {

    constructor(private http: HttpClient) {
    }

    listTodos(request) {
        const params = request;
        return this.http.get(apiUrl, {params, headers});
    }

    deleteTaskById(id: number): Observable<void> {
        return this.http.delete<void>(apiUrl + '/' + id, {headers});
    }

    updateTask(task: CreateTask): Observable<Task> {
        return this.http.patch<Task>(apiUrl + '/', {task, headers});
    }

    getTaskById(id: string): Observable<Task> {
        console.log('Service start');
        return this.http.get<Task>(apiUrl + '/' + id, {headers});
    }
}
