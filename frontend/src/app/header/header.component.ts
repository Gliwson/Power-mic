import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {KeycloakService} from 'keycloak-angular';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
    isLogged: boolean;

    constructor(private route: ActivatedRoute,
                private router: Router, private keycloakService: KeycloakService) {
    }

    ngOnInit() {
        this.keycloakService.isLoggedIn().then(isLogged => this.isLogged = isLogged);
    }

    logout() {
        this.keycloakService.logout();
    }
}
