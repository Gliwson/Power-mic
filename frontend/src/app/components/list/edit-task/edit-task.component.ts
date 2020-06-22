import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TaskService} from '../../../services/task.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {CreateTask, Task, TaskType} from '../../../models/task';
import {Location} from '@angular/common';


@Component({
    selector: 'app-edit-task',
    templateUrl: './edit-task.component.html',
    styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {

    task: Task;
    createTask: CreateTask;
    taskType: TaskType;
    startDate: string;
    endDate: string;

    @Output() dateChange: EventEmitter<Date>;
    @Input() date: Date;

    constructor(private service: TaskService,
                private route: ActivatedRoute,
                private router: Router, private location: Location) {
        this.date = new Date();
        this.dateChange = new EventEmitter();
        this.route.paramMap
            .pipe(switchMap((params: ParamMap) => this.service
                .getTaskById(params.get('id')))).subscribe(value => {
            this.task = value;
            this.startDate = new Date(value.startDate).toISOString().slice(0, 16);
            this.endDate = new Date(value.endDate).toISOString().slice(0, 16);
        });
    }


    private toDateString(date: Date): string {
        return (date.getFullYear().toString() + '-'
            + ('0' + (date.getMonth() + 1)).slice(-2) + '-'
            + ('0' + (date.getDate())).slice(-2))
            + 'T' + date.toTimeString().slice(0, 5);
    }

    ngOnInit(): void {

    }

    goToMovies() {
        this.location.back();
    }

    save() {

    }

    conLog(event: string) {
        console.log(event);
    }
}
