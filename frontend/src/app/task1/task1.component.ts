import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TaskDatasource} from '../task.datasource';
import {MatPaginator} from '@angular/material/paginator';
import {TaskService} from '../task.service';
import {tap} from 'rxjs/operators';

@Component({
    selector: 'app-task1',
    templateUrl: './task1.component.html',
    styleUrls: ['./task1.component.css']
})
export class Task1Component implements OnInit, AfterViewInit {

    displayedColumns = ['id', 'namePowerStation', 'powerLoss', 'startDate', 'endDate'];
    todoDatasource: TaskDatasource;

    @ViewChild(MatPaginator) paginator: MatPaginator;

    constructor(private todoService: TaskService) {
    }

    ngOnInit() {
        this.todoDatasource = new TaskDatasource(this.todoService);
        this.todoDatasource.loadTask();
    }

    ngAfterViewInit() {
        this.todoDatasource.counter$
            .pipe(
                tap((count) => {
                    this.paginator.length = count;
                })
            )
            .subscribe();

        this.paginator.page
            .pipe(
                tap(() => this.loadTasks())
            )
            .subscribe();
    }

    loadTasks() {
        this.todoDatasource.loadTask(this.paginator.pageIndex, this.paginator.pageSize);
    }

}
