package io.github.notoriousnapper.pqservice.model;

import java.util.List;
import lombok.Data;

@Data
public class FormData {
  String formDataTitle;
  List<String> requiredFormProperties;
  List<String> extraFormProperties;
}
