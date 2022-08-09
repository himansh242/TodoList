package org.example.todolist.repositories;

import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.response.TaskResponse;

public interface TaskRepository {

    TaskResponse createTask(String todoListId, PutTaskRequest request);

    TaskResponse getTask(String todoListId, String taskId) throws Exception;

    TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request);

    boolean deleteTask(String todoListId, String taskId);
}
