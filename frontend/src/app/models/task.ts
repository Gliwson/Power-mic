import {TaskType} from './task-type.enum';

export interface TodoListResponse {
    content: Task[];
    totalElements: number;
}

export interface Task {
    id: number;
    namePowerStation: string;
    taskType: TaskType;
    powerLoss: number;
    startDate: Date;
    endDate: Date;
}

export interface CreateTask {
    id: number;
    taskType: string;
    powerLoss: number;
    startDate: Date;
    endDate: Date;
}
