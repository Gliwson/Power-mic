import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

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

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + '/tasks' + '/' + id, {headers});
    }
}
