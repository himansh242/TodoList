package org.example.todolist.repositories;

import org.example.todolist.models.request.PostTodoListRequest;
import org.example.todolist.models.request.PutTodoListRequest;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TodoListRepository {

    StoredTodoList createTodoList(PutTodoListRequest request);

    Map<StoredTodoList, List<StoredTask>> getTodoList(String todoListId);

    StoredTodoList updateTodoList(String todoListId, PostTodoListRequest request);

    boolean deleteTodoList(String todoListId);

}
