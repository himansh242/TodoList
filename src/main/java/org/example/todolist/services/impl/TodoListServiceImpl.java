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
        StoredTodoList storedTodoList = todoListRepository.createTodoList(request);
        return TodoListResponse.builder()
                .id(storedTodoList.getTodolistId())
                .title(storedTodoList.getTitle())
                .message("Todo list successfully created")
                .build();
    }

    @Override
    public TodoListResponse getTodoList(String todoListId) {
        Map<StoredTodoList, List<StoredTask>> completeTodoList = todoListRepository.getTodoList(todoListId);
        if (completeTodoList.isEmpty()) {
            return TodoListResponse.builder()
                    .message("TodoList id not present")
                    .build();
        }
        Map.Entry<StoredTodoList, List<StoredTask> > entry = completeTodoList.entrySet().iterator().next();
        StoredTodoList storedTodoList = entry.getKey();
        List<StoredTask> tasks = entry.getValue();

        TodoListResponse todoListResponse = TodoListResponse.builder()
                .id(todoListId)
                .title(storedTodoList.getTitle())
                .build();

        List<TaskResponse> taskResponses = new ArrayList<>();
        if (tasks != null) {
            for (StoredTask task : tasks) {
                TaskResponse taskResponse = TaskResponse.builder()
                        .id(task.getTaskId())
                        .task(task.getTask())
                        .deadline(task.getDeadline())
                        .status(task.getStatus())
                        .build();
                taskResponses.add(taskResponse);
            }
            todoListResponse.setMessage("Here is the list of todos for corresponding todoListId");
            todoListResponse.setTasks(taskResponses);
        } else {
            todoListResponse.setMessage("No tasks present for the corresponding todoListId");
        }
        return todoListResponse;
    }

    @Override
    public TodoListResponse updateTodoList(String todoListId, PostTodoListRequest request) {
        StoredTodoList storedTodoList = todoListRepository.updateTodoList(todoListId, request);
        return TodoListResponse
                .builder()
                .id(storedTodoList.getTodolistId())
                .title(storedTodoList.getTitle())
                .tasks(null)
                .message("Successfully updated corresponding todo list")
                .build();
    }

    @Override
    public TodoListResponse deleteTodoList(String todoListId) {

        boolean success = todoListRepository.deleteTodoList(todoListId);
        if(success) {
            return TodoListResponse.builder()
                    .message("Successfully deleted corresponding todo list")
                    .build();
        }
        return TodoListResponse.builder()
                .message("Some error occurred while deletion of corresponding todo list")
                .build();
    }
}
