import {Component, OnInit} from '@angular/core';
import {TaskService} from '../../../services/task.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {CreateTask, Task} from '../../../models/task';
import {Location} from '@angular/common';
import {TaskType} from '../../../models/task-type.enum';


@Component({
    selector: 'app-edit-task',
    templateUrl: './edit-task.component.html',
    styleUrls: ['./edit-task.component.css']
})
export class EditTaskComponent implements OnInit {
    task: Task = {id: 0, namePowerStation: 'name', taskType: TaskType.AWARIA, powerLoss: 0, endDate: new Date(), startDate: new Date()};
    keys = Object.keys;
    taskType = TaskType;
    selectTaskType = TaskType.AWARIA;
    startDate: string;
    endDate: string;
    id: number;

    constructor(private service: TaskService,
                private route: ActivatedRoute,
                private router: Router, private location: Location) {
    }

    ngOnInit(): void {
        this.route.paramMap
            .pipe(switchMap((params: ParamMap) => this.service
                .getTaskById(params.get('id')))).subscribe(value => {
            this.task = value;
            this.id = value.id;
            this.selectTaskType = value.taskType;
            this.startDate = new Date(value.startDate).toISOString().slice(0, 16);
            this.endDate = new Date(value.endDate).toISOString().slice(0, 16);
        });
    }

    goToList() {
        this.location.back();
    }

    save() {
        const createTask: CreateTask = {
            id: this.id,
            powerLoss: this.task.powerLoss,
            taskType: this.selectTaskType,
            startDate: this.changeStringToDate(this.startDate),
            endDate: this.changeStringToDate(this.endDate)
        };
        this.service.updateTask(createTask).subscribe();
        this.location.back();
    }

    changeStringToDate(stringDate: string): Date {
        const date = stringDate;
        const actualParsedDate = date ? new Date(date) : new Date();
        return new Date(actualParsedDate.getTime());
    }
}
