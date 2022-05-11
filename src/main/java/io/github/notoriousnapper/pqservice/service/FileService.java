package io.github.notoriousnapper.pqservice.service;


import io.github.notoriousnapper.pqservice.model.list.FileItem;
import io.github.notoriousnapper.pqservice.repository.IFileRepo;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileService {


  @Autowired
  IFileRepo fileRepo;

  public List<FileItem> getAllFileItems() {
    return fileRepo.findAll();
  }

  public void addFileItem(FileItem fileItem){ fileRepo.save(fileItem); }
}
