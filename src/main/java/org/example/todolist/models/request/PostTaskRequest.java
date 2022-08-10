package org.example.todolist.models.request;

import lombok.Data;
import org.example.todolist.models.response.TaskStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class PostTaskRequest {

    private String task;
    private Long deadline;
    private TaskStatus status;

}
