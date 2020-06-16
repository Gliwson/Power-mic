import {Component, OnInit} from '@angular/core';
import {TaskService} from '../../services/task.service';
import {Task} from '../../models/task';
import {PageEvent} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

@Component({
    selector: 'app-task2',
    templateUrl: './task2.component.html',
    styleUrls: ['./task2.component.css']
})
export class Task2Component implements OnInit {

    totalElements: number = 0;
    tasks: Task[] = [];
    loading: boolean;

    constructor(private todoService: TaskService) {
    }

    ngOnInit(): void {
        this.getTodos({page: '0', size: '10'});
    }

    private getTodos(request) {
        this.loading = true;
        this.todoService.listTodos(request)
            .subscribe(data => {
                this.tasks = data['content'];
                this.totalElements = data['totalElements'];
                this.loading = false;
            }, error => {
                this.loading = false;
            });
    }

    nextPage(event: PageEvent) {
        const request = {};
        request['page'] = event.pageIndex.toString();
        request['size'] = event.pageSize.toString();
        this.getTodos(request);
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        const dataSource = new MatTableDataSource(this.tasks);
        dataSource.filter = filterValue.trim().toLowerCase();
    }
}
