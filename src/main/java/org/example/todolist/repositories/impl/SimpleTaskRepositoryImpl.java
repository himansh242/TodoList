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
import java.util.List;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class SimpleTaskRepositoryImpl implements TaskRepository {


    private final DataStore dataStore;

    private static final String TASKID = "taskId";

    private static final String TODOLISTID = "todoListId";

    private LookupDao<StoredTask> daoTask;

    private LookupDao<StoredTodoList> daoTodoList;

    @SneakyThrows
    @Override
    public TaskResponse createTask(String todoListId, PutTaskRequest request) {

        StoredTask storedTask = new StoredTask();
        dataStore.taskCount++;
        storedTask.setTaskId("task"+String.valueOf(dataStore.taskCount));
        storedTask.setTask(request.getTask());
        storedTask.setDeadline(request.getDeadline());
        storedTask.setTodoListId(todoListId);
        storedTask.setStatus(TaskStatus.PENDING);
        TaskResponse taskResponse = TaskResponse.builder()
                .id(storedTask.getTaskId())
                .task(storedTask.getTaskId())
                .deadline(storedTask.getDeadline())
                .status(storedTask.getStatus())
                .build();
        var storedTodoListOptional = daoTodoList.get(todoListId);
        if( storedTodoListOptional.isPresent()) {
            daoTask.save(storedTask);
        }
        // use builder.
        //dataStore.todoListIdToTaskList.get(todoListId ).add(storedTask);
        return taskResponse;
    }

    @Override
    public TaskResponse getTask(String todoListId, String taskId) {

        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        //criteria.add(Restrictions.eq(TODOLISTID, todoListId))
        //        .add(Restrictions.eq(TASKID, taskId));
        criteria.add(Restrictions.eq(TASKID, taskId));
        List<StoredTask> tasks = daoTask.scatterGather(criteria);
        StoredTask storedTask = tasks.get(0);

        TaskResponse taskResponse = TaskResponse.builder().build();
        taskResponse.setId(storedTask.getTaskId());
        taskResponse.setTask(storedTask.getTask());
        taskResponse.setDeadline(storedTask.getDeadline());
        taskResponse.setStatus(storedTask.getStatus());
        return taskResponse;
    }

    @SneakyThrows
    @Override
    public TaskResponse updateTask(String todoListId, String taskId, PostTaskRequest request) {

        //List<StoredTask> tasks = dataStore.todoListIdToTaskList.get(todoListId);
        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        //criteria.add(Restrictions.eq(TODOLISTID, todoListId))
        //        .add(Restrictions.eq(TASKID, taskId));
        criteria.add(Restrictions.eq(TASKID, taskId));
        List<StoredTask> tasks = daoTask.scatterGather(criteria);
        StoredTask storedTask = tasks.get(0);
        daoTask.delete(taskId);
        storedTask.setTask(request.getTask());
        storedTask.setStatus(request.getStatus());
        storedTask.setDeadline(request.getDeadline());
        daoTask.save(storedTask);
        TaskResponse taskResponse = TaskResponse.builder().build();
        taskResponse.setId(storedTask.getTaskId());
        taskResponse.setTask(storedTask.getTask());
        taskResponse.setDeadline(storedTask.getDeadline());
        taskResponse.setStatus(storedTask.getStatus());

        /*for( int i=0; i<tasks.size(); i++) {
            if(tasks.get(i).getTaskId().equals(taskId)) {
                tasks.get(i).setTask(request.getTask());
                tasks.get(i).setStatus(request.getStatus());
                tasks.get(i).setDeadline(request.getDeadline());

                StoredTask storedTask = tasks.get(i);
                taskResponse.setId(storedTask.getTaskId());
                taskResponse.setTask(storedTask.getTask());
                taskResponse.setDeadline(storedTask.getDeadline());
                taskResponse.setStatus(storedTask.getStatus());
                break;
            }
        }*/
        return taskResponse;
    }

    @Override
    public boolean deleteTask(String todoListId, String taskId) {

        /*List<StoredTask> tasks = dataStore.todoListIdToTaskList.get(todoListId );
        for( int i=0; i<dataStore.todoListIdToTaskList.get(todoListId ).size(); i++) {
            if(dataStore.todoListIdToTaskList.get(todoListId ).get(i).getTaskId().equals(taskId)) {
                dataStore.todoListIdToTaskList.get(todoListId ).remove(i);
                break;
            }
        }*/
        //DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        //criteria.add(Restrictions.eq(TODOLISTID, todoListId))
        //        .add(Restrictions.eq(TASKID, taskId));
        //List<StoredTask> tasks = daoTask.scatterGather(criteria);
        //StoredTask storedTask = tasks.get(0);
        daoTask.delete(taskId);
        return true;
    }
}
