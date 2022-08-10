package org.example.todolist.repositories.impl;

import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.dao.LookupDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.todolist.models.request.PostTaskRequest;
import org.example.todolist.models.request.PutTaskRequest;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.models.response.TaskStatus;
import org.example.todolist.models.storage.DataStore;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;
import org.example.todolist.repositories.TaskRepository;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import java.util.Optional;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class SimpleTaskRepositoryImpl implements TaskRepository {


    private final DataStore dataStore;

    private static final String TASK_ID = "taskId";

    private LookupDao<StoredTask> daoTask;

    private LookupDao<StoredTodoList> daoTodoList;

    @SneakyThrows
    @Override
    public TaskResponse createTask(String todoListId, PutTaskRequest request) {

        var storedTodoListOptional = daoTodoList.get(todoListId);

        if(storedTodoListOptional.isEmpty()) {
            return TaskResponse.builder()
                    .message("Corresponding TodoListId is not present")
                    .build();
        }

        StoredTask storedTask = new StoredTask();
        storedTask.setTaskId("task"+ dataStore.taskCount++);
        storedTask.setTask(request.getTask());
        storedTask.setDeadline(request.getDeadline());
        storedTask.setTodoListId(todoListId);
        storedTask.setStatus(TaskStatus.PENDING);

        daoTask.save(storedTask);

        return TaskResponse.builder()
                .id(storedTask.getTaskId())
                .task(storedTask.getTask())
                .deadline(storedTask.getDeadline())
                .status(storedTask.getStatus())
                .message("Saved Task Successfully")
                .build();
    }

    @Override
    public TaskResponse getTask(String todoListId, String taskId) throws Exception {

        Optional<StoredTodoList> storedTodoList = daoTodoList.get(todoListId);

        if(storedTodoList.isEmpty()) {
            return TaskResponse.builder()
                    .message("Given todoListId Does not exist")
                    .build();
        }

        Optional<StoredTask> tasks = daoTask.get(taskId);
        if(tasks.isEmpty()) {
            return TaskResponse.builder()
                    .message("Corresponding taskId is not present")
                    .build();
        }

        StoredTask storedTask = tasks.get();
        return TaskResponse.builder()
                .id(storedTask.getTaskId())
                .task(storedTask.getTask())
                .deadline(storedTask.getDeadline())
                .status(storedTask.getStatus())
                .message("Search successful")
                .build();
    }

    @SneakyThrows
    @Override
    public TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request) {

        Optional<StoredTodoList> storedTodoList = daoTodoList.get(todoListId);

        if(storedTodoList.isEmpty()) {
            return TaskResponse.builder()
                    .message("Given todoListId Does not exist")
                    .build();
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        criteria.add(Restrictions.eq(TASK_ID, taskId));
        Optional<StoredTask> task = daoTask.get(taskId);

        if(task.isEmpty()) {
            return TaskResponse.builder()
                    .message("Corresponding taskId is not present")
                    .build();
        }

        StoredTask storedTask = task.get();
        daoTask.delete(taskId);
        storedTask.setTask(request.getTask());
        storedTask.setStatus(request.getStatus());
        storedTask.setDeadline(request.getDeadline());
        daoTask.save(storedTask);
        return TaskResponse.builder()
                .id(storedTask.getTaskId())
                .task(storedTask.getTask())
                .deadline(storedTask.getDeadline())
                .status(storedTask.getStatus())
                .message("Successfully updated the given taskId")
                .build();
    }

    @Override
    public TaskResponse deleteTask(String todoListId, String taskId) throws Exception {
        Optional<StoredTodoList> storedTodoList = daoTodoList.get(todoListId);

        if(storedTodoList.isEmpty()) {
            return TaskResponse.builder()
                    .message("Given todoListId Does not exist")
                    .build();
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        criteria.add(Restrictions.eq(TASK_ID, taskId));
        Optional<StoredTask> task = daoTask.get(taskId);

        if(task.isEmpty()) {
            return TaskResponse.builder()
                    .message("Corresponding taskId is not present")
                    .build();
        }


        daoTask.delete(taskId);
        return TaskResponse
                .builder()
                .message("successfully delete taskId")
                .build();
    }
}
