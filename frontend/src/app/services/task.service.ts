import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateTask, Task} from '../models/task';

const headers = new HttpHeaders().set('Content-Type', 'application/json');
const apiUrl = 'http://localhost:8085/power/api' + '/tasks';

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

    updateTask(response: CreateTask): Observable<Task> {
        console.log(response);
        return this.http.patch<Task>(apiUrl + '/', response, {headers});
    }

    getTaskById(id: string): Observable<Task> {
        console.log('Service start');
        return this.http.get<Task>(apiUrl + '/' + id, {headers});
    }
}
