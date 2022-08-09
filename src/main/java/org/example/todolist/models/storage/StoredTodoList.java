package org.example.todolist.models.storage;

import io.appform.dropwizard.sharding.sharding.LookupKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "todolists")
public class StoredTodoList {

    @Id
    @LookupKey
    @Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String todolistId;

    @Column(name = "title")
    private String title;

}
