import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {PowerStationListDataSource} from './power-station-list-datasource';
import {PowerStationService} from '../../services/power-station.service';
import {PowerStation} from '../../models/powerStations';

@Component({
    selector: 'app-power-station-list',
    templateUrl: './power-station-list.component.html',
    styleUrls: ['./power-station-list.component.css']
})
export class PowerStationListComponent implements AfterViewInit, OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;
    dataSource: PowerStationListDataSource;
    data: MatTableDataSource<PowerStation>;

    displayedColumns = ['id', 'name', 'power'];

    constructor(private service: PowerStationService) {
    }

    ngOnInit() {
        this.dataSource = new PowerStationListDataSource(this.service);
        this.dataSource.loadPowerStations();
        this.dataSource.getPowerStationTable();
    }

    ngAfterViewInit() {
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.dataSource.afterViewInit();
    }
}
