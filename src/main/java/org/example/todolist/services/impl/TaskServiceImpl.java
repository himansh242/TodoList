package org.example.todolist.services.impl;

import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.models.response.TaskStatus;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.repositories.TaskRepository;
import org.example.todolist.services.TaskService;

import javax.inject.Inject;
import java.util.UUID;

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Inject
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(String todoListId, PutTaskRequest request) {
        return taskRepository.createTask( todoListId, request );
    }

    @Override
    public TaskResponse getTask(String todoListId, String taskId) throws Exception {
        return taskRepository.getTask(todoListId, taskId);
    }

    @Override
    public TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request) {
        return taskRepository.updateTask(todoListId, taskId, request);
    }

    @Override
    public TaskResponse deleteTask(String todoListId, String taskId) throws Exception {
        return taskRepository.deleteTask(todoListId, taskId);

    }
}
