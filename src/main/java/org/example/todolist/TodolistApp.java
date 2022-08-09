package org.example.todolist;

import com.google.inject.Stage;
import io.appform.dropwizard.sharding.DBShardingBundle;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.example.todolist.models.storage.StoredTask;
import org.example.todolist.models.storage.StoredTodoList;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class TodolistApp extends Application<TodolistConfiguration> {

    private final static String APP_NAME = "todoList";

    public static void main(String[] args) throws Exception {
        new TodolistApp().run(args);
    }

    @Override
    public String getName() {
        return APP_NAME;
    }

    @Override
    public void initialize(Bootstrap<TodolistConfiguration> bootstrap) {

        var dbShardingBundle = new DBShardingBundle<TodolistConfiguration>(StoredTask.class, StoredTodoList.class) {
            @Override
            protected ShardedHibernateFactory getConfig(TodolistConfiguration config) {
                return config.getShardConfig();
            }
        };
        bootstrap.addBundle(dbShardingBundle);

        bootstrap.addBundle(
                GuiceBundle.builder()
                        .enableAutoConfig("org.example.todolist")
                        .modules(new TodolistModule(dbShardingBundle))
                        .printDiagnosticInfo()
                        .build(Stage.PRODUCTION)
        );
    }

    @Override
    public void run(TodolistConfiguration notepadConfig, Environment environment) {
    }
}
