package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aggregate {

    @JsonProperty("move")
    Move move;

    @JsonProperty("aggregateValue")
    Integer aggregateValue;

    @JsonProperty("aggregateType")
    String aggregateType;
}
