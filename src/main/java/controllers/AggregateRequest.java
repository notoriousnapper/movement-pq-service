package controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateRequest {

    List<String> idList;
    String aggregateType;
    Boolean showAllAggregates;
}
