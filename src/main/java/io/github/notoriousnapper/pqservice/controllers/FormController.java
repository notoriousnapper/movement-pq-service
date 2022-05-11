package io.github.notoriousnapper.pqservice.controllers;

import io.github.notoriousnapper.pqservice.model.FormData;
import io.github.notoriousnapper.pqservice.model.list.ListItem;
import io.github.notoriousnapper.pqservice.service.FormDataService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormController {

  @Autowired
  private FormDataService formDataService;

  @GetMapping("/form/{fileType}")
  @ResponseBody
  public FormData getFormDataForGivenFileType(@PathVariable String fileType) {
    List<ListItem> listItems = new ArrayList<>();
    try {
      return formDataService.getFormData(fileType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
