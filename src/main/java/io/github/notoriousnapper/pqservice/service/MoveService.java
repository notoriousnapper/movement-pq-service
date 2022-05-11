package io.github.notoriousnapper.pqservice.service;


import io.github.notoriousnapper.pqservice.model.Move;
import io.github.notoriousnapper.pqservice.model.MoveRecord;
import io.github.notoriousnapper.pqservice.model.MoveTypeEnum;
import io.github.notoriousnapper.pqservice.repository.IMoveRecordsRepo;
import io.github.notoriousnapper.pqservice.util.CSVParser;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MoveService {

  IMoveRecordsRepo moveRecordsRepo;

  private Map<Long, Move> moveMap;
  private Map<String, Integer> typeMapBySize; // 1 is largest atomsize

  public MoveService(IMoveRecordsRepo moveRecordsRepo) throws Exception {

    this.moveRecordsRepo = moveRecordsRepo;
    List<Move> moves = this.getAllMoves();
    moveMap = new HashMap<>();
    for (Move move : moves) {
      moveMap.put(move.getId(), move);
    }
    typeMapBySize = new HashMap<>();

    int i = 0;
    for (MoveTypeEnum m : MoveTypeEnum.values()) {
      typeMapBySize.put(m.toString(), i);
      i++;
    }
  }

  public void addRecord(Move move, String recordValue) throws IOException, URISyntaxException {
    this.addRecord(move, recordValue, Date.from(Instant.now()));
  }

  public void addRecord(Move move, String recordValue, Date trueRecordDate) throws IOException, URISyntaxException {
      MoveRecord moveRecord = new MoveRecord();
      moveRecord.setMove(move);
      moveRecord.setMoveId(move.getId());
      moveRecord.setRecordValue(recordValue);
      if (trueRecordDate == null){
        moveRecord.setTrueRecordDate(Date.from(Instant.now()));
      } else {
        moveRecord.setTrueRecordDate(trueRecordDate);
      }
      moveRecordsRepo.save(moveRecord);
  }


  public void deleteMoveRecord(MoveRecord moveRecord){
        moveRecordsRepo.delete(moveRecord);
  }


  // Get all Move Records
  // TODO: Improve*
  public List<MoveRecord> getAllMoveRecords() throws Exception {
    return moveRecordsRepo.findAll();
  }

  // TODO: Increase performance with better algorithms* // or overall rewrite
  public List<MoveRecord> getAllMoveRecordsById(Long id) throws Exception {

      return moveRecordsRepo.findAllByMoveId(id);
  }

  public List<Move> getAllMoves() throws Exception {

    Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(
        "csv/moves.csv").toURI()));
    List<String[]> csvData = CSVParser.readAll(reader);
    List<Move> moves = new ArrayList<>();

    // Skip first part
    if (testMovesFormat(csvData.get(0))) {
      csvData.remove(0); // Remove column titles

      for (String[] row : csvData) {
        Move move = new Move();

        if (row[0] == null) {
          // THROW exception? What to do when?
        } else {
          move.setId(Long.valueOf(row[0]));
        }

        if (row[4] == null) {
          // THROW exception? What to do when?
        } else {
          move.setPriority(Integer.valueOf(row[4]));
        }

        move.setName(row[1]);
        move.setType(row[2]);
        move.setRecordType(row[3]);
        move.setDateLastDone(row[5]);
        move.setImageURL(row[6]);
        move.setDescription(row[7]);
        move.setTags(row[8].split(","));
        move.setMultiSelect(row[9]);

        moves.add(move);
      }
    } else {
      System.out
          .println("!\nERROR!\nCheck in CSV formatting or data. Check if fields are up-to-date");
    }
    return moves;
  }


  public boolean testMovesFormat(String[] strings) {

    return strings[0].equals("Id")
        && strings[1].equals("Move")
        && strings[2].equals("Type")
        && strings[3].equals("RecordType")
        && strings[4].equals("Priority")
        && strings[5].equals("DateLastDone");
  }

  public Map<Long, Move> getMoveMap() {
    return moveMap;
  }
}
