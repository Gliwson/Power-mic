export interface TodoListResponse {
    content: Task[];
    totalElements: number;
}

export interface Task {
    id: number;
    namePowerStation: string;
    taskType: string;
    powerLoss: number;
    startDate: Date;
    endDate: Date;
}
