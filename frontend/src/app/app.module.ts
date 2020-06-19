import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatPaginatorModule} from '@angular/material/paginator';
import {Task1Component} from './components/task1/task1.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';

import {ToolbarComponent} from './components/toolbar/toolbar.component';
import {ProtectedComponent} from './components/protected/protected.component';
import {PublicComponent} from './components/public/public.component';
import {HeaderComponent} from './components/header/header.component';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {DatePipe, registerLocaleData} from '@angular/common';
import {environment} from '../environments/environment';

import {TwoDigitDecimalNumberDirective} from './directives/two-digit-decimal-number.directive';
import {AlertComponent} from './components/alert/alert.component';

import {CollapseModule} from 'ngx-bootstrap/collapse';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {CustomerStore} from './stores/customer.store';
import {HttpErrorInterceptor} from './interceptor/http-error.interceptor';
import localePl from '@angular/common/locales/pl';

registerLocaleData(localePl);

export function kcInitializer(keycloak: KeycloakService): () => Promise<any> {
    return (): Promise<any> => {
        return new Promise(async (resolve, reject) => {
            try {
                await keycloak.init(environment.keycloakOptions);
                console.log('Keycloak is initialized');
                resolve();
            } catch (error) {
                console.log('Error thrown in init ' + error);
                reject(error);
            }
        });
    };
}

@NgModule({
    declarations: [
        AppComponent,
        Task1Component,
        ToolbarComponent,
        HeaderComponent,
        ProtectedComponent,
        PublicComponent,
        TwoDigitDecimalNumberDirective,
        AlertComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        MatNativeDateModule,
        ReactiveFormsModule,
        MatInputModule,
        MatSortModule,
        KeycloakAngularModule,
        CollapseModule.forRoot(),
        TooltipModule.forRoot(),
        ModalModule.forRoot(),
        BsDropdownModule.forRoot()
    ],
    providers: [
        DatePipe,
        CustomerStore,
        {provide: APP_INITIALIZER, useFactory: kcInitializer, multi: true, deps: [KeycloakService]},
        {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
