package org.example.todolist.services.impl;

import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.response.TaskResponse;
import org.example.todolist.models.response.TodoListResponse;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;
import org.example.todolist.repositories.TodoListRepository;
import org.example.todolist.services.TodoListService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TodoListServiceImpl implements TodoListService {

    private final TodoListRepository todoListRepository;

    @Inject
    public TodoListServiceImpl(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public TodoListResponse createTodoList(PutTodoListRequest request) {
        TodoListResponse todoListResponse = new TodoListResponse();
        StoredTodoList storedTodoList = todoListRepository.createTodoList(request);
        todoListResponse.setId(storedTodoList.getTodolistId());
        todoListResponse.setTitle(storedTodoList.getTitle());
        todoListResponse.setTasks(null);
        return todoListResponse;
    }

    @Override
    public TodoListResponse getTodoList(String todoListId) {
        TodoListResponse todoListResponse = new TodoListResponse();
        Map<StoredTodoList, List<StoredTask>> completeTodoList = todoListRepository.getTodoList(todoListId);
        todoListResponse.setId(todoListId);
        Map.Entry<StoredTodoList, List<StoredTask> > entry = completeTodoList.entrySet().iterator().next();
        StoredTodoList storedTodoList = entry.getKey();
        List<StoredTask> tasks = entry.getValue();
        todoListResponse.setTitle(storedTodoList.getTitle());
        List<TaskResponse> taskResponses = new ArrayList<>();
        if (tasks != null) {
            for (int i = 0; i < tasks.size(); i++) {
                TaskResponse taskResponse = TaskResponse.builder().build();
                taskResponse.setTask(tasks.get(i).getTask());
                taskResponse.setId(tasks.get(i).getTaskId());
                taskResponse.setDeadline(tasks.get(i).getDeadline());
                taskResponse.setStatus(tasks.get(i).getStatus());
                taskResponses.add(taskResponse);
            }
            todoListResponse.setTasks(taskResponses);
        } else {
            todoListResponse.setTasks(null);
        }
        return todoListResponse;
    }

    @Override
    public TodoListResponse updateTodoList(String todoListId, PostTodoListRequest request) {
        TodoListResponse todoListResponse = new TodoListResponse();
        StoredTodoList storedTodoList = todoListRepository.updateTodoList(todoListId, request);
        todoListResponse.setId(storedTodoList.getTodolistId());
        todoListResponse.setTitle(storedTodoList.getTitle());
        todoListResponse.setTasks(null);
        return todoListResponse;
    }

    @Override
    public TodoListResponse deleteTodoList(String todoListId) {
        TodoListResponse todoListResponse = new TodoListResponse();
        boolean success = todoListRepository.deleteTodoList(todoListId);
        if(success) {
            todoListResponse.setId(null);
            todoListResponse.setTasks(null);
            todoListResponse.setTitle(null);
        } else {
            assert false;
        }
        return todoListResponse;
    }
}
