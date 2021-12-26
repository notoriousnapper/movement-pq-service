package controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * Includes physical therapy stretches, movements,
 *  release techniques, motions in martial arts
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {

    // Order is HardCoded for now!
    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    String type;

    @JsonProperty("recordType")
    String recordType; // Duration, Reps, WeightedReps, etc.

    @JsonProperty("priority")
    Integer priority;

    @JsonProperty("recordValue")
    String recordValue; // Duration, Reps, WeightedReps, etc.

    @JsonProperty("dateLastDone")
    String  dateLastDone; // For PriorityQueue // Randomizer

    String[] tags;

    String imageURL; // Using Dropshare

    @Override
    public String toString(){
        return String.format("This move is called %s of type %s. " +
                "It is recorded using %s", name, type, recordType);
    }
}
