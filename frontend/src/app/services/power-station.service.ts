import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateTask} from '../models/task';
import {PowerStation} from '../models/powerStations';


const headers = new HttpHeaders().set('Content-Type', 'application/json');
const apiUrl = 'http://localhost:8085/power/api' + '/powerstations';

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

    update(response, id): Observable<PowerStation> {
        return this.http.patch<PowerStation>(apiUrl + '/' + id, response, {headers});
    }

    save(response): Observable<PowerStation> {
        return this.http.post<PowerStation>(apiUrl, response, {headers});
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
