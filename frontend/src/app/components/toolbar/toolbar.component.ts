import {Component, OnInit} from '@angular/core';
import {KeycloakService} from 'keycloak-angular';

@Component({
    selector: 'app-toolbar',
    template: `
        <button routerLink="/">Home</button>
        <button routerLink="/protected" routerLinkActive="active">Protected</button>
        <button routerLink="/header" *ngIf="isLogged">Header</button>
        <button routerLink="/public">Public</button>
        <button routerLink="/todos1" *ngIf="isLogged">List 1</button>
        <button routerLink="/todos2" *ngIf="isLogged">List 2</button>
        <button (click)="logout()" *ngIf="isLogged">Logout (frontend)</button>
    `
})
export class ToolbarComponent implements OnInit {
    isLogged: boolean;

    constructor(private keycloakService: KeycloakService) {
    }

    logout = async (): Promise<void> => await this.keycloakService.logout();

    ngOnInit(): void {
        this.keycloakService.isLoggedIn().then(isLogged => this.isLogged = isLogged);
    }
}
