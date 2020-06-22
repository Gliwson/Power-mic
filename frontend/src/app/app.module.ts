import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ListComponent} from './components/list/list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


import {ProtectedComponent} from './components/protected/protected.component';
import {PublicComponent} from './components/public/public.component';
import {HeaderComponent} from './components/header/header.component';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {DatePipe, registerLocaleData} from '@angular/common';
import {environment} from '../environments/environment';

import {AlertComponent} from './components/alert/alert.component';

import {CollapseModule} from 'ngx-bootstrap/collapse';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {ModalModule} from 'ngx-bootstrap/modal';
import {BsDropdownModule} from 'ngx-bootstrap/dropdown';
import {Customer} from './services/customer.service';
import {HttpErrorInterceptor} from './interceptor/http-error.interceptor';
import localePl from '@angular/common/locales/pl';
import {DemoMaterialModule} from './material-module';
import {NavigationComponent} from './navigation/navigation.component';
import {LayoutModule} from '@angular/cdk/layout';
import {DialogComponent} from './components/list/dialogDelete/dialog.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {TableComponent} from './components/list/table/table.component';
import {EditTaskComponent} from './components/list/edit-task/edit-task.component';

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
        ListComponent,
        HeaderComponent,
        ProtectedComponent,
        PublicComponent,
        AlertComponent,
        NavigationComponent,
        DialogComponent,
        TableComponent,
        EditTaskComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
        KeycloakAngularModule,
        CollapseModule.forRoot(),
        TooltipModule.forRoot(),
        ModalModule.forRoot(),
        BsDropdownModule.forRoot(),
        DemoMaterialModule,
        LayoutModule
    ],
    providers: [
        DatePipe,
        Customer,
        {provide: APP_INITIALIZER, useFactory: kcInitializer, multi: true, deps: [KeycloakService]},
        {provide: HTTP_INTERCEPTORS, useClass: HttpErrorInterceptor, multi: true},
        {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
