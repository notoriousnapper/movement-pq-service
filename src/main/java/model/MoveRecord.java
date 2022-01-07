package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveRecord {

    Move move;

    @JsonProperty("moveId")
    Integer moveId;

    @JsonProperty("recordValue")
    String recordValue; //m, min, sec, s,
    @JsonProperty("dateTime")
    String datetime; // TODO: Figure out best practices!
}
