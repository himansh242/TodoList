package org.example.todolist.models.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TodoListResponse {

    private String id;

    private String title;

    List<TaskResponse> tasks;

    String message;
}
