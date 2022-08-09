package org.example.todolist.models.response;

import lombok.Data;

import java.util.List;

@Data
public class TodoListResponse {

    private String id;

    private String title;

    List<TaskResponse> tasks;
}
