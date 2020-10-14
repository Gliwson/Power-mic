import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BreakpointObserver, MediaMatcher} from '@angular/cdk/layout';
import {Router} from '@angular/router';
import {Customer} from '../services/customer.service';
import {KeycloakService} from 'keycloak-angular';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const headers = new HttpHeaders().set('Content-Type', 'application/json');

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    mobileQuery: MediaQueryList;
    isLogged: boolean;
    username: string;
    database: any;

    private _mobileQueryListener: () => void;

    constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private breakpointObserver: BreakpointObserver,
                private router: Router,
                private customer: Customer, private keycloakService: KeycloakService, private http: HttpClient) {
        this.mobileQuery = media.matchMedia('(max-width: 700px)');
        this._mobileQueryListener = () => changeDetectorRef.detectChanges();
        this.customer.init();
    }


    ngOnInit() {
        this.keycloakService.isLoggedIn().then(isLogged => this.isLogged = isLogged);
        this.username = this.keycloakService.getUsername();
    }

    doLogin(): void {
        this.customer.login();
    }


    async doLogout() {
        this.keycloakService.clearToken();
        await this.router.navigate(['/']);
        await this.customer.logout();
    }

    doRegister() {
        this.customer.register();
    }

    click() {
        this.http.get('http://localhost:8086/keycloak/profile', {headers})
            .subscribe(value => this.database = value);
    }
}




