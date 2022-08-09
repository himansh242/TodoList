package org.example.todolist.models.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Data
public class PutTaskRequest {

    private String task;

    //@JsonProperty("deadline")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    //@JsonSerialize(using = LocalDateTimeSerializer.class)
//    private LocalDateTime timeline;

    private Long deadline;

//    public static void main(String[] args) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//
//        /*
//         {
//            "task": "some task",
//            "deadline": "2022-08-01T00:00:05"
//         }
//         */
//
//        var json = "{\n" +
//                "            \"task\": \"some task\",\n" +
//                "            \"timeline\": \"2022-08-01T00:00:05\"\n" +
//                "         }";
//
//
//        var obj = mapper.readValue(json, PutTaskRequest.class);
//        log.info("obj: {}", obj);
//    }
}
