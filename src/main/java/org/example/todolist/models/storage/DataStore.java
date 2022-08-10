package org.example.todolist.models.storage;

import com.google.inject.Singleton;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Singleton
public class DataStore {

    public Integer todoListCount = 1;
    public Integer taskCount = 1;
}
