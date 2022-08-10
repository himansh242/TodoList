package org.example.todolist;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.dao.LookupDao;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;
import org.example.todolist.repositories.TaskRepository;
import org.example.todolist.repositories.TodoListRepository;
import org.example.todolist.repositories.impl.SimpleTaskRepositoryImpl;
import org.example.todolist.repositories.impl.SimpleTodoListRepositoryImpl;
import org.example.todolist.services.TaskService;
import org.example.todolist.services.TodoListService;
import org.example.todolist.services.impl.TaskServiceImpl;
import org.example.todolist.services.impl.TodoListServiceImpl;

import javax.ws.rs.Produces;

public class TodolistModule extends AbstractModule {

    private final DBShardingBundle<TodolistConfiguration> dbShardingBundle;

    public TodolistModule(DBShardingBundle<TodolistConfiguration> dbShardingBundle) {
        this.dbShardingBundle = dbShardingBundle;
    }

    public void configure() {

        bind(TodoListService.class).to(TodoListServiceImpl.class).in(Singleton.class);

        bind(TodoListRepository.class).to(SimpleTodoListRepositoryImpl.class).in(Singleton.class);

        bind(TaskService.class).to(TaskServiceImpl.class).in(Singleton.class);

        bind(TaskRepository.class).to(SimpleTaskRepositoryImpl.class).in(Singleton.class);
    }


    @Provides
    public LookupDao<StoredTask> providesTask() {
        return dbShardingBundle.createParentObjectDao(StoredTask.class);
    }

    @Provides
    public LookupDao<StoredTodoList> providesTodoListDao() {
        return dbShardingBundle.createParentObjectDao(StoredTodoList.class);
    }
}
