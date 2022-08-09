package org.example.todolist.models.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {

    private String id;

    private String task;

    private Long deadline;

    private TaskStatus status;
}
