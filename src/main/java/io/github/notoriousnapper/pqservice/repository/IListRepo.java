package io.github.notoriousnapper.pqservice.repository;

import io.github.notoriousnapper.pqservice.model.list.ListItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IListRepo extends JpaRepository<ListItem, Long> {

  List<ListItem> findAllByType(String type);

}
