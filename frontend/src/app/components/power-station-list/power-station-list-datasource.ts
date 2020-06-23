import {DataSource} from '@angular/cdk/collections';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {catchError, finalize, tap} from 'rxjs/operators';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {PowerStationService} from '../../services/power-station.service';
import {MatTableDataSource} from '@angular/material/table';
import {ListPowerStation, PowerStation} from '../../models/powerStations';

export class PowerStationListDataSource extends DataSource<PowerStation> {

    private todoSubject = new BehaviorSubject<PowerStation[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private countSubject = new BehaviorSubject<number>(0);
    public counter$ = this.countSubject.asObservable();
    data: MatTableDataSource<PowerStation>;
    paginator: MatPaginator;
    sort: MatSort;
    text = '';

    constructor(private service: PowerStationService) {
        super();
    }

    connect(): Observable<PowerStation[]> {
        return this.todoSubject.asObservable();
    }

    getPowerStationTable(): MatTableDataSource<PowerStation> {
        this.connect().pipe(tap(task => {
            console.log(task.length);
        })).subscribe(value => {
            this.data = new MatTableDataSource(value);
            this.data.sort = this.sort;
        });
        return this.data;
    }

    disconnect() {
        this.todoSubject.complete();
        this.loadingSubject.complete();
        this.countSubject.complete();
    }

    afterViewInit() {
        this.counter$
            .pipe(
                tap((count) => {
                    this.paginator.length = count;
                    this.applyFilter();
                })
            )
            .subscribe();
        this.paginator.page
            .pipe(
                tap(() => {
                    this.loadTasks();
                })
            )
            .subscribe();
    }

    loadTasks() {
        this.loadPowerStations(this.paginator.pageIndex, this.paginator.pageSize);
    }

    loadPowerStations(pageNumber = 0, pageSize = 20) {
        this.loadingSubject.next(true);
        this.service.getAllPowerStation({page: pageNumber, size: pageSize})
            .pipe(
                catchError(() => of([])),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe((result: ListPowerStation) => {
                    this.todoSubject.next(result.content);
                    this.countSubject.next(result.totalElements);
                }
            );
    }

    applyFilter() {
        this.data.filter = this.text.trim().toLowerCase();
        if (this.data.paginator) {
            this.data.paginator.firstPage();
        }
    }
}
