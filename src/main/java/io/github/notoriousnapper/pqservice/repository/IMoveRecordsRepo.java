package io.github.notoriousnapper.pqservice.repository;

import io.github.notoriousnapper.pqservice.model.MoveRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoveRecordsRepo extends JpaRepository<MoveRecord, Long> {

  public List<MoveRecord> findAllByMoveId(Long id);



}
