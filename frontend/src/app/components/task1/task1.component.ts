import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TaskDatasource} from '../../task.datasource';
import {TaskService} from '../../services/task.service';
import {tap} from 'rxjs/operators';
import {Task} from '../../models/task';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../dialog/dialog.component';


@Component({
    selector: 'app-task1',
    templateUrl: './task1.component.html',
    styleUrls: ['./task1.component.css']
})
export class Task1Component implements OnInit, AfterViewInit {

    displayedColumns = ['id', 'namePowerStation', 'powerLoss', 'startDate', 'endDate', 'actions'];
    todoDatasource: TaskDatasource;
    data: MatTableDataSource<Task>;
    text = '';

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;

    constructor(private todoService: TaskService, private dialog: MatDialog) {
    }

    openDialog(id: number): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '250px',
            data: {id: id}
        });
        dialogRef.afterClosed().subscribe(() => {
            this.loadTasks();
        });
    }

    ngOnInit() {
        this.todoDatasource = new TaskDatasource(this.todoService);
        this.todoDatasource.loadTask();
        this.todoDatasource.getTasks().pipe(tap(task => {
            console.log(task.length);
        })).subscribe(value => {
            this.data = new MatTableDataSource(value);
            this.data.sort = this.sort;
        });
    }

    ngAfterViewInit() {
        this.todoDatasource.counter$
            .pipe(
                tap((count) => {
                    this.paginator.length = count;
                    this.applyFilter();
                })
            )
            .subscribe();
        this.paginator.page
            .pipe(
                tap(() => {
                    this.loadTasks();
                })
            )
            .subscribe();
    }

    loadTasks() {
        this.todoDatasource.loadTask(this.paginator.pageIndex, this.paginator.pageSize);
    }

    applyFilter() {
        this.data.filter = this.text.trim().toLowerCase();
        if (this.data.paginator) {
            this.data.paginator.firstPage();
        }
    }

    clickActive(id: number) {
        console.log('Click!!!' + id);
    }
}
