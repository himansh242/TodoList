package org.example.todolist.models.storage;

import io.appform.dropwizard.sharding.sharding.LookupKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todolists")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoredTodoList {

    @Id
    @LookupKey
    @Column(name = "id")
    private String todolistId;

    @Column(name = "title")
    private String title;

}
