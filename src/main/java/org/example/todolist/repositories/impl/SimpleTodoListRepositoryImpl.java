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
        StoredTodoList storedTodoList = new StoredTodoList();
        storedTodoList.setTitle(request.getTitle());
        dataStore.todoListCount++;
        String todoListId = "todo" + String.valueOf(dataStore.todoListCount);
        storedTodoList.setTodolistId(todoListId);
        var todoListOptional = daoTodoList.save(storedTodoList);
        //dataStore.todoListIdToTitle.put(todoListId, storedTodoList);
        //dataStore.todoListIdToTaskList.put(todoListId, new ArrayList<>());
        return storedTodoList;
    }


    @SneakyThrows
    @Override
    public Map<StoredTodoList, List<StoredTask>> getTodoList(String todoListId) {
        Map<StoredTodoList, List<StoredTask>> completeTodoList = new HashMap<>();
        //StoredTodoList storedTodoList = new StoredTodoList();
        //storedTodoList.setTodolistId(todoListId);
        var storedTodoListOptional = daoTodoList.get( todoListId );
        StoredTodoList storedTodoList;
        //storedTodoList.setTitle(dataStore.todoListIdToTitle.get(todoListId).getTitle());
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
        StoredTodoList storedTodoList = new StoredTodoList();
        storedTodoList.setTodolistId(todoListId);
        storedTodoList.setTitle(request.getTitle());
        daoTodoList.delete(todoListId);
        // use update.
        var todoListOptional = daoTodoList.save(storedTodoList);
        return storedTodoList;
    }

    @Override
    public boolean deleteTodoList(String todoListId) {
        daoTodoList.delete(todoListId);
        DetachedCriteria criteria = DetachedCriteria.forClass(StoredTask.class);
        criteria.add(Restrictions.eq("todoListId", todoListId));
        List<StoredTask> tasks = daoTask.scatterGather(criteria);
        for(int i = 0; i < tasks.size(); i++) {
            daoTask.delete(tasks.get(i).getTaskId());
        }
        return true;
    }
}
