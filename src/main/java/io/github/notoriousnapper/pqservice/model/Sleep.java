package io.github.notoriousnapper.pqservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import java.math.BigDecimal;
import lombok.Data;

@JsonDeserialize(converter = SleepEntitySanitizer.class) // Invoked after class is deserialized
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Sleep {

  @JsonProperty("name")
  String name;

  @JsonProperty("type")
  String type;

  @JsonProperty("bedtime_start")
  String bedTimeStart;

  @JsonProperty("summary_date")
  String summaryDate;

  @JsonProperty("total")
  Long total;

  @JsonProperty("duration")
  Long duration; // in bed

}

class SleepEntitySanitizer extends StdConverter<Sleep, Sleep> {

  @Override
  public Sleep convert(Sleep sleepObject) {

    float floatSleepDuration = (float) sleepObject.getDuration();
    float sleepAmountInHours = (floatSleepDuration / (60) / (60));
    float sleepPrecisionTwo = BigDecimal.valueOf(sleepAmountInHours)
        .setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

    // Test case
    // "TotalTimeAsleep: "
    sleepObject.setName(sleepPrecisionTwo + " h");
    sleepObject.setType("sleep");
    return sleepObject;
  }
}
