package io.github.notoriousnapper.pqservice.controllers;

import io.github.notoriousnapper.pqservice.model.list.FileItem;
import io.github.notoriousnapper.pqservice.service.FileService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {


  @Autowired
  FileService fileService;


  @GetMapping("/file")
  @ResponseBody
  public List<FileItem> getAllFileItems(
  ) {
    List<FileItem> fileItems = new ArrayList<>();
    try {
      return fileService.getAllFileItems();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileItems;
  }


  @PostMapping("/file")
  @ResponseBody
  @CrossOrigin(origins = {"http://localhost:3000"})
  public FileItem postFileItem(
      @RequestBody Map<String, Object> payload) throws ParseException {
    FileItem fileItem = conformPayLoadToListItem(payload);
    try {
      fileService.addFileItem(fileItem);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileItem;
  }

  private FileItem conformPayLoadToListItem(Map<String, Object> payload) {


    FileItem fileItem = new FileItem();
    fileItem.setName((String) payload.get("name"));
    fileItem.setType((String) payload.get("type"));
    fileItem.setDescription((String) payload.get("description"));
    fileItem.setFileURL((String) payload.get("fileURL"));
    fileItem.setThumbnailURL((String) payload.get("thumbnailURL"));
    fileItem.setTags((String) payload.get("tags"));
    fileItem.setTypeMetaData((String) payload.get("typeMetaData"));
    return fileItem;
  }
}
