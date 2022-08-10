package org.example.todolist.repositories.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.appform.dropwizard.sharding.dao.LookupDao;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.storage.DataStore;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;
import org.example.todolist.repositories.TodoListRepository;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.*;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class SimpleTodoListRepositoryImpl implements TodoListRepository {
    private final DataStore dataStore;
    private LookupDao<StoredTask> daoTask;
    private LookupDao<StoredTodoList> daoTodoList;


    @SneakyThrows
    @Override
    public StoredTodoList createTodoList(PutTodoListRequest request)  {

        String todoListId = "todo" + dataStore.todoListCount++;
        StoredTodoList storedTodoList = StoredTodoList.builder()
                .todolistId(todoListId)
                .title(request.getTitle())
                .build();
        daoTodoList.save(storedTodoList);
        return storedTodoList;
    }


    @SneakyThrows
    @Override
    public Map<StoredTodoList, List<StoredTask>> getTodoList(String todoListId) {
        Map<StoredTodoList, List<StoredTask>> completeTodoList = new HashMap<>();
        var storedTodoListOptional = daoTodoList.get(todoListId);
        StoredTodoList storedTodoList;
        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        criteria.add(Restrictions.eq("todoListId", todoListId));
        List<StoredTask> tasks = daoTask.scatterGather(criteria);
        if(storedTodoListOptional.isPresent()) {
            storedTodoList = storedTodoListOptional.get();
            completeTodoList.put(storedTodoList, tasks);
        }
        return completeTodoList;
    }

    @SneakyThrows
    @Override
    public StoredTodoList updateTodoList(String todoListId, PostTodoListRequest request) {
        StoredTodoList storedTodoList = StoredTodoList.builder()
                .todolistId(todoListId)
                .title(request.getTitle())
                .build();
        daoTodoList.delete(todoListId);
        daoTodoList.save(storedTodoList);
        return storedTodoList;
    }

    @Override
    public boolean deleteTodoList(String todoListId) {
        daoTodoList.delete(todoListId);
        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        criteria.add(Restrictions.eq("todoListId", todoListId));
        List<StoredTask> tasks = daoTask.scatterGather(criteria);
        for (StoredTask task : tasks) {
            daoTask.delete(task.getTaskId());
        }
        return true;
    }
}
