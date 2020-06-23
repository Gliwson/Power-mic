import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateTask} from '../models/task';
import {environment} from '../../environments/environment';
import {PowerStation} from '../components/power-station-list/power-station-list-datasource';

const headers = new HttpHeaders().set('Content-Type', 'application/json');
const apiUrl = environment.apiUrl + '/powerstations';

@Injectable({
    providedIn: 'root'
})
export class PowerStationService {

    constructor(private http: HttpClient) {
    }

    getAllPowerStation(request) {
        const params = request;
        return this.http.get(apiUrl, {params, headers});
    }

    deleteTaskById(id: number): Observable<void> {
        return this.http.delete<void>(apiUrl + '/' + id, {headers});
    }

    updateTask(response: CreateTask): Observable<PowerStation> {
        console.log(response);
        return this.http.patch<PowerStation>(apiUrl + '/', response, {headers});
    }

    getTaskById(id: string): Observable<PowerStation> {
        console.log('Service start');
        return this.http.get<PowerStation>(apiUrl + '/' + id, {headers});
    }
}
