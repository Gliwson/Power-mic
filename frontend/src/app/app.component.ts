import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CustomerStore} from './stores/customer.store';
import {KeycloakService} from 'keycloak-angular';
import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    isLogged: boolean;
    username: string;

    constructor(
        private router: Router,
        private customerStore: CustomerStore, private keycloakService: KeycloakService, private http: HttpClient) {
        this.customerStore.init();
    }

    ngOnInit() {
        this.keycloakService.isLoggedIn().then(isLogged => this.isLogged = isLogged);
        this.username = this.keycloakService.getUsername();
    }

    doLogin(): void {
        this.customerStore.login();
    }


    async doLogout() {
        this.keycloakService.clearToken();
        await this.router.navigate(['/']);
        await this.customerStore.logout();
    }

    doRegister() {
        this.customerStore.register();
    }
}
