import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TaskService {

    constructor(private http: HttpClient) {
    }

    listTodos(request) {
        const endpoint = environment.apiUrl + '/tasks';
        const params = request;
        return this.http.get(endpoint, {params});
    }

    // listTodos(request) {
    //     const endpoint = 'http://localhost:8080/tasks?page=10&size=10';
    //     const params = request;
    //     var headers = new HttpHeaders({
    //         'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
    //         'Authorization': 'Bearer ' + Cookie.get('access_token')
    //     });
    //     return this.http.get(endpoint, {headers: headers})
    //         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    // }
}
