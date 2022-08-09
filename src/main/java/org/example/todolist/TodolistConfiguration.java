package org.example.todolist;

import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class TodolistConfiguration extends Configuration {

    @Valid
    @NotNull
    private ShardedHibernateFactory shardConfig;
}
