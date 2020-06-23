import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ListComponent} from './components/list/list.component';
import {PublicComponent} from './components/public/public.component';
import {ProtectedComponent} from './components/protected/protected.component';
import {AppAuthGuard} from './app-auth.guard';
import {EditTaskComponent} from './components/list/edit-task/edit-task.component';
import {PowerStationListComponent} from './components/power-station-list/power-station-list.component';

const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: ''},
    {path: 'list', component: ListComponent, canActivate: [AppAuthGuard], data: {roles: ['user']}},
    {path: 'editTask/:id', component: EditTaskComponent, canActivate: [AppAuthGuard], data: {roles: ['user']}},
    {path: 'powerStation', component: PowerStationListComponent, canActivate: [AppAuthGuard], data: {roles: ['user']}},
    {path: 'public', component: PublicComponent},
    {path: 'protected', component: ProtectedComponent, canActivate: [AppAuthGuard]},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
