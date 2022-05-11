package io.github.notoriousnapper.pqservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SleepSummary {

//    SleepWrapper sleep;
    Sleep[] sleep;
}
