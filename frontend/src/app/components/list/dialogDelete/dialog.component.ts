import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {TaskService} from '../../../services/task.service';

export interface DialogData {
    id: number;
}

@Component({
    selector: 'app-dialog',
    templateUrl: './dialog.component.html',
    styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

    constructor(
        public dialogRef: MatDialogRef<DialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DialogData, private todoService: TaskService) {
    }

    onNoClick(): void {
        this.todoService.deleteTaskById(this.data.id).subscribe();
        this.dialogRef.close();
    }

    ngOnInit(): void {
    }

}
