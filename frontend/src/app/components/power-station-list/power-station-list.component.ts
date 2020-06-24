import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {PowerStationListDataSource} from './power-station-list-datasource';
import {PowerStationService} from '../../services/power-station.service';
import {PowerStation} from '../../models/powerStations';
import {KeycloakService} from 'keycloak-angular';
import {OpenCreatePowerStationComponent} from './open-create-power-station/open-create-power-station.component';

@Component({
    selector: 'app-power-station-list',
    templateUrl: './power-station-list.component.html',
    styleUrls: ['./power-station-list.component.css']
})
export class PowerStationListComponent implements AfterViewInit, OnInit {
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild('child1') createModel: OpenCreatePowerStationComponent;
    @ViewChild('child2') saveModel: OpenCreatePowerStationComponent;
    dataSource: PowerStationListDataSource;
    data: MatTableDataSource<PowerStation>;
    displayedColumns = [];
    isAdmin = false;

    constructor(private service: PowerStationService, private keycloakService: KeycloakService) {
        this.checkIfHeIsAdmin();
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

    checkIfHeIsAdmin() {
        this.isAdmin = this.keycloakService.isUserInRole('admin');
        if (this.isAdmin) {
            this.displayedColumns = ['id', 'name', 'power', 'button'];
        } else {
            this.displayedColumns = ['id', 'name', 'power'];
        }
    }

    async refresh() {
        await delay(300);
        this.dataSource.loadTasks();
    }
}

function delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
