import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {Task2Component} from './task2/task2.component';
import {Task1Component} from './task1/task1.component';
import {HomeComponent} from './home.component';


const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'task1'},
    {path: 'home', component: HomeComponent},
    {path: 'todos1', component: Task1Component},
    {path: 'todos2', component: Task2Component},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
