package io.github.notoriousnapper.pqservice.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregateRequest {

    List<String> idList;
    String aggregateType;
    Boolean showAllAggregates;
}
