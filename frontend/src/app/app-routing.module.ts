import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {Task2Component} from './components/task2/task2.component';
import {Task1Component} from './components/task1/task1.component';
import {HeaderComponent} from './components/header/header.component';
import {PublicComponent} from './components/public/public.component';
import {ProtectedComponent} from './components/protected/protected.component';
import {AppAuthGuard} from './app-auth.guard';

const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: ''},
    {
        path: 'todos1',
        component: Task1Component,
        canActivate: [AppAuthGuard]
    }
    ,
    {
        path: 'todos2',
        component: Task2Component,
        canActivate: [AppAuthGuard]
    },
    {
        path: 'header',
        component: HeaderComponent,
        // canActivate: [AppAuthGuard]
    },
    {
        path: 'public',
        component: PublicComponent
    },
    {
        path: 'protected',
        component: ProtectedComponent,
        canActivate: [AppAuthGuard]
    }
    ,
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
