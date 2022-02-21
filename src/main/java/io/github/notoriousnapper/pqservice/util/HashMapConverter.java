package io.github.notoriousnapper.pqservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.notoriousnapper.pqservice.model.Move;
import java.io.IOException;
import java.util.Map;
import javax.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class HashMapConverter implements AttributeConverter<Move, String> {

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(Move customerInfo) {

    String customerInfoJson = null;
    try {
      customerInfoJson = objectMapper.writeValueAsString(customerInfo);
    } catch (final JsonProcessingException e) {
//      logger.error("JSON writing error", e);
    }

    return customerInfoJson;
  }

  @Override
  public Move convertToEntityAttribute(String customerInfoJSON) {

    Move customerInfo = null;
    try {
      customerInfo = objectMapper.readValue(customerInfoJSON, Move.class);
    } catch (final IOException e) {
//      logger.error("JSON reading error", e);
    }

    return customerInfo;
  }

}
