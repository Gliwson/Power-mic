package pl.power.constant;

public enum TaskType {
    AWARIA, REMONT;

    public static TaskType mapStringToTaskType(String taskType) {
        switch (taskType) {
            case "AWARIA":
                return TaskType.AWARIA;
            case "REMONT":
                return TaskType.REMONT;
            default:
                throw new IllegalStateException("Unexpected value: " + taskType);
        }
    }
}
