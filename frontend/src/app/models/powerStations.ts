export interface ListPowerStation {
    content: PowerStation[];
    totalElements: number;
}

export interface PowerStation {
    id: number;
    name: string;
    power: number;
}
