import {AfterViewInit, Component, Input, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {Task} from '../../../models/task';
import {DialogComponent} from '../dialogDelete/dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {TaskService} from '../../../services/task.service';
import {tap} from 'rxjs/operators';
import {TaskDatasource} from '../task.datasource';

@Component({
    selector: 'app-table',
    templateUrl: './table.component.html',
    styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit, AfterViewInit {


    @Input()
    isAdmin: boolean;
    @Input()
    displayedColumns: [];
    text = '';
    todoDatasource: TaskDatasource;
    data: MatTableDataSource<Task>;
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort, {static: true}) sort: MatSort;

    constructor(private dialog: MatDialog, private todoService: TaskService) {
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

    deleteDialog(id: number): void {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '250px',
            data: {id: id}
        });
        dialogRef.afterClosed().subscribe(() => {
            this.loadTasks();
        });
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

}
