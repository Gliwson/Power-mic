package pl.power.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.power.services.impl.TaskServiceSoapImpl;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;

@Endpoint
public class TaskEnd {

    private final TaskServiceSoapImpl serviceSoap;

    public TaskEnd(TaskServiceSoapImpl serviceSoap) {
        this.serviceSoap = serviceSoap;
    }


    @PayloadRoot(namespace = "http://s0314/gettask", localPart = "getAllTasksRequest")
    @ResponsePayload
    public GetAllTasksResponse getStudentById(@RequestPayload GetAllTasksRequest getAllTasksRequest) {
        GetAllTasksResponse getResponse = new GetAllTasksResponse();
        getResponse.getTasks().addAll(serviceSoap.getAll());
        return getResponse;
    }

}
