package io.github.notoriousnapper.pqservice.repository;

import io.github.notoriousnapper.pqservice.model.list.SongListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongListRepo extends JpaRepository<SongListItem, Long> {

}
