package org.example.todolist.models.storage;

import io.appform.dropwizard.sharding.sharding.LookupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.example.todolist.models.response.TaskStatus;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoredTask {

    @Id
    @LookupKey
    @Column(name = "taskId")
    private String taskId;

    @Column(name = "todoListId")
    private String todoListId;

    @Column(name = "task")
    private String task;

    @Column(name = "deadline")
    private Long deadline;

    @Column(name = "status")
    private TaskStatus status;
}
