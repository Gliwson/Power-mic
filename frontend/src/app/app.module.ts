import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {MatPaginatorModule} from '@angular/material/paginator';
import {Task2Component} from './task2/task2.component';
import {Task1Component} from './task1/task1.component';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatNativeDateModule} from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatSortModule} from '@angular/material/sort';
import {HomeComponent} from './home.component';
import {FooComponent} from './foo.component';
import {ToolbarComponent} from './toolbar/toolbar.component';
import {ProtectedComponent} from './protected/protected.component';
import {PublicComponent} from './public/public.component';
import {HeaderComponent} from './header/header.component';
import {KeycloakAngularModule, KeycloakService} from 'keycloak-angular';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import {initializer} from '../utils/app-init';

@NgModule({
    declarations: [
        AppComponent,
        Task1Component,
        Task2Component,
        HomeComponent,
        FooComponent,
        ToolbarComponent,
        HeaderComponent,
        ProtectedComponent,
        PublicComponent
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
        KeycloakAngularModule
    ],
    providers: [{provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},
        {
            provide: APP_INITIALIZER,
            useFactory: initializer,
            deps: [KeycloakService],
            multi: true
        },
        {
            provide: LocationStrategy,
            useClass: HashLocationStrategy
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
