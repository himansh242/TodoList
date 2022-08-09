package org.example.todolist.services;

import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.response.TodoListResponse;

public interface TodoListService {

    TodoListResponse createTodoList(PutTodoListRequest request);

    TodoListResponse getTodoList(String todoListId);

    TodoListResponse updateTodoList(String todoListId, PostTodoListRequest request);

    TodoListResponse deleteTodoList(String todoListId);
}
