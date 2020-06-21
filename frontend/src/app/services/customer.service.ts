import {Injectable} from '@angular/core';
import {from} from 'rxjs';

import {KeycloakService} from 'keycloak-angular';

@Injectable({
    providedIn: 'root'
})
export class Customer {
    constructor(
        private keycloakService: KeycloakService) {
    }

    init = (): void => {
        if (this.keycloakService.isLoggedIn()) {
            from(this.keycloakService.loadUserProfile()).pipe().subscribe();
        }
    };

    logout = async (): Promise<void> => {
        await this.keycloakService.logout();
    };

    login(): void {
        this.keycloakService.login();
        this.init();
    }

    register(): void {
        this.keycloakService.register();
        this.init();
    }
}
