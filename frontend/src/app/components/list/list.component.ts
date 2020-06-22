import {Component} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';


@Component({
    selector: 'app-list',
    templateUrl: './list.component.html',
    styleUrls: ['./list.component.css']
})
export class ListComponent {

    displayedColumns = [];
    isAdmin = false;

    constructor(private keycloakService: KeycloakService) {
        this.checkIfHeIsAdmin();
    }

    checkIfHeIsAdmin() {
        this.isAdmin = this.keycloakService.isUserInRole('admin');
        if (this.isAdmin) {
            this.displayedColumns = ['id', 'namePowerStation', 'powerLoss', 'taskType', 'startDate', 'endDate', 'actions'];
        } else {
            this.displayedColumns = ['id', 'namePowerStation', 'powerLoss', 'taskType', 'startDate', 'endDate'];
        }
    }

}
