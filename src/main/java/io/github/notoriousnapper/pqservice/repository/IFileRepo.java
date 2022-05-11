package io.github.notoriousnapper.pqservice.repository;

import io.github.notoriousnapper.pqservice.model.list.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileRepo extends JpaRepository<FileItem, Long> {


}
