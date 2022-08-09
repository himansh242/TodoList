package org.example.todolist.repositories.impl;

import io.appform.dropwizard.sharding.dao.LookupDao;
import lombok.SneakyThrows;
import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.models.response.TaskStatus;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.repositories.TaskRepository;

import javax.inject.Inject;

public class TaskRepositoryImpl implements TaskRepository {

    private LookupDao<StoredTask> dao;

    @Inject
    public TaskRepositoryImpl(LookupDao<StoredTask> dao) {
        this.dao = dao;
    }

    @SneakyThrows
    @Override
    public TaskResponse createTask(String todoListId, PutTaskRequest request) {
        var task = StoredTask.builder()
                .task("something")
                .deadline(12231323L)
                .taskId("taskId")
                .status(TaskStatus.PENDING)
                .todoListId("todolistId")
                .build();
        var taskOptional = dao.save(task);

        return null;
    }

    @Override
    public TaskResponse getTask(String todoListId, String taskId)  {
        //dao.get(todoListId);
        return null;
    }

    @Override
    public TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request) {
        return null;
    }

    @Override
    public boolean deleteTask(String todoListId, String taskId) {
        return false;
    }
}
