import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CustomerStore} from './stores/customer.store';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    isCollapsed = true;

    constructor(
        private router: Router,
        private customerStore: CustomerStore) {
        this.customerStore.init();
    }

    ngOnInit() {
    }

    doLogin(): void {
        this.customerStore.login();
    }


    async doLogout() {
        await this.router.navigate(['/']);
        await this.customerStore.logout();
    }
}
