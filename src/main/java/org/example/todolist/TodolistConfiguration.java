package org.example.todolist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.appform.dropwizard.sharding.config.ShardedHibernateFactory;
import io.dropwizard.Configuration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class TodolistConfiguration extends Configuration {

    @Valid
    @NotNull
    private ShardedHibernateFactory shardConfig;
}
