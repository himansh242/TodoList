package org.example.todolist.resources;

import com.google.inject.Singleton;
import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.response.GenericResponse;
import org.example.todolist.models.response.TodoListResponse;
import org.example.todolist.services.TodoListService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/v1/todoList/")
public class TodoListResource {

    private final TodoListService todoListService;

    @Inject
    public TodoListResource(TodoListService todoListService) {
        this.todoListService = todoListService;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TodoListResponse> createTodoList(PutTodoListRequest request) {
        //System.out.println("Create new todolist = " + request );
        return GenericResponse.ok(todoListService.createTodoList(request));
    }

    @POST
    @Path("{todoListId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TodoListResponse> updateTodoList(@PathParam("todoListId") String todoListId,
                                                            PostTodoListRequest request) {
        //System.out.println("Update todolist request received. Request = " + request + " todoListId = " + todoListId);
        return GenericResponse.ok(todoListService.updateTodoList(todoListId, request));
    }

    @DELETE
    @Path("{todoListId}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TodoListResponse> deleteTodoList(@PathParam("todoListId") String todoListId) {
        //System.out.println("Delete todolist request received. todoListId = " + todoListId);
        return GenericResponse.ok(todoListService.deleteTodoList(todoListId));
    }

    @GET
    @Path("{todoListId}")
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<TodoListResponse> getTodoList(@PathParam("todoListId") String todoListId) {
        //System.out.println("Get todolist request received. todoListId = " + todoListId);
        return GenericResponse.ok(todoListService.getTodoList(todoListId));
    }
}
