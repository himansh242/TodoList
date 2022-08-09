package org.example.todolist.resources;

import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.response.GenericResponse;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.services.TaskService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/todoList/{todoListId}/task/")
public class TaskResource {

    private final TaskService taskService;

    @Inject
    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TaskResponse> createTask(@PathParam("todoListId") String todoListId,
                                                    PutTaskRequest request) {
        //System.out.println("Create new todolist = " + request );
        return GenericResponse.ok(taskService.createTask(todoListId, request));
    }

    @POST
    @Path("{taskId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TaskResponse> updateTask(
            @PathParam("todoListId") String todoListId,
            @PathParam("taskId") String taskId,
            PostTaskRequest request
    ) {
        //System.out.println("Update todolist request received. Request = " + request + " todoListId = " + todoListId);
        return GenericResponse.ok(taskService.updateTask(todoListId, taskId, request));
    }

    @DELETE
    @Path("{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TaskResponse> deleteTask(@PathParam("todoListId") String todoListId,
                                                    @PathParam("taskId") String taskId) {
        return GenericResponse.ok(taskService.deleteTask(todoListId, taskId));
    }

    @GET
    @Path("{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TaskResponse> getTask(@PathParam("todoListId") String todoListId,
                                                 @PathParam("taskId") String taskId) throws Exception {
        return GenericResponse.ok(taskService.getTask(todoListId, taskId));
    }

}