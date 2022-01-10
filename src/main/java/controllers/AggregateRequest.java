package controllers;

import lombok.Data;

import java.util.List;

@Data
public class AggregateRequest {
    List<String> idList;
    String aggregateType;
    Boolean allFilter;
}
