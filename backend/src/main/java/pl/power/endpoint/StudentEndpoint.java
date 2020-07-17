package pl.power.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.power.services.impl.TaskServiceSoap;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;

@Endpoint
public class StudentEndpoint {

    private final TaskServiceSoap serviceSoap;

    public StudentEndpoint(TaskServiceSoap serviceSoap) {
        this.serviceSoap = serviceSoap;
    }


    @PayloadRoot(namespace = "http://s0314/gettask", localPart = "getAllTasksRequest")
    @ResponsePayload
    public GetAllTasksResponse getStudentById(@RequestPayload GetAllTasksRequest getAllTasksRequest) {
        GetAllTasksResponse getResponse = new GetAllTasksResponse();
        getResponse.getTasks().addAll(serviceSoap.getAll());
        return getResponse;
    }

//    @PayloadRoot(namespace = "http://alla.com/getallevents", localPart = "getAllEventsRequest2")
//    @ResponsePayload
//    public GetAllEventsResponse getStudentById2(@RequestPayload GetAllEventsRequest getAllTasksRequest) {
//        GetAllEventsResponse getResponse = new GetAllEventsResponse();
//        getResponse.getMylist().addAll(null);
//        return getResponse;
//    }

}
