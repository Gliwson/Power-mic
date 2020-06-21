import {Component, OnInit} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map, shareReplay} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Customer} from '../services/customer.service';
import {KeycloakService} from 'keycloak-angular';
import {HttpClient} from '@angular/common/http';

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
    isLogged: boolean;
    username: string;

    isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
        .pipe(
            map(result => result.matches),
            shareReplay()
        );


    constructor(private breakpointObserver: BreakpointObserver,
                private router: Router,
                private customer: Customer, private keycloakService: KeycloakService, private http: HttpClient) {
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
}
