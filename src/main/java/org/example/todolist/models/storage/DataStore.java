package org.example.todolist.models.storage;

import com.google.inject.Singleton;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Singleton
public class DataStore {

    public Integer todoListCount = 0;

    public Integer taskCount = 0;
    public Map<String, StoredTodoList> todoListIdToTitle = new HashMap<>();
    public Map<String, List<StoredTask>> todoListIdToTaskList = new HashMap<>();
}
