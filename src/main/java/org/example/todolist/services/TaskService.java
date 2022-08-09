package org.example.todolist.services;

import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.models.response.TodoListResponse;

public interface TaskService {

    TaskResponse createTask(String todoListId, PutTaskRequest request);

    TaskResponse getTask(String todoListId, String taskId) throws Exception;

    TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request);

    TaskResponse deleteTask(String todoListId, String taskId);
}
