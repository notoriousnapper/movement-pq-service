package io.github.notoriousnapper.pqservice.controllers;

import static io.github.notoriousnapper.pqservice.model.MoveEnumSorting.ATOM_SIZE_DESCENDING;
import static io.github.notoriousnapper.pqservice.model.MoveEnumSorting.CURRENT_WEEK;
import static io.github.notoriousnapper.pqservice.model.MoveEnumSorting.PRIORITY;

import io.github.notoriousnapper.pqservice.model.Move;
import io.github.notoriousnapper.pqservice.model.MoveRecord;
import io.github.notoriousnapper.pqservice.service.MoveService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {


  @Autowired
  MoveService moveService;

  @GetMapping("/move")
  @ResponseBody
  public List<Move> getAllMoves(
      @RequestParam(required = false) String filter,
      @RequestParam(required = false) String filterTwo
  ) {

    List<Move> moves = new ArrayList<>();
    try {
      moves = moveService.getAllMoves();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<String> filters = Arrays.asList(filter, filterTwo);
    for (String f : filters) {
      moves = applyFilter(f, moves);
    }

    return moves;
  }

  private List<Move> applyFilter(String f, List<Move> moves) {

    if (f == null) {
      return moves;
    }
    // TODO: Make filter into array & parse - filter 1, 2, into below
    // TODO: Add in clause for nothing, empty string, might be mistake

    else if (f.equals(PRIORITY.toString())) {
      moves = moves.stream().filter(
          item -> item.getPriority() <= 1 // 0 is "NO QUESTIONS"
      ).collect(Collectors.toList());
      Collections.sort(moves);
    } else if (f != null) {
      if (f.equals(ATOM_SIZE_DESCENDING.toString())) {
        Map<String, List<Move>> typeMap = new HashMap<>();
        for (Move m : moves) {
          if (typeMap.containsKey(m.getType())) {
            typeMap.get(m.getType()).add(m);
          } else {
            List<Move> moveList = new ArrayList<>();
            moveList.add(m);
            typeMap.put(m.getType(), moveList);
          }
        }
        List<Move> reorderedMoveList = new ArrayList<>();

        List<String> typeOrderedListBySize = new ArrayList<>();
        typeOrderedListBySize.addAll(typeMap.keySet());
        Collections.sort(typeOrderedListBySize,
            Comparator.comparingInt(x -> moveService.getTypeMapBySize().get(x)));
        for (String type : typeOrderedListBySize) {
          reorderedMoveList.addAll(typeMap.get(type));
        }
        moves = reorderedMoveList;
      }
    }
    return moves;
  }

  @GetMapping("/moverecords")
  @ResponseBody
  public List<MoveRecord> getAllMoveRecords(
      @RequestParam(required = false) String weekFilter
  ) {

    List<MoveRecord> moveRecords = new ArrayList<>();
    try {
      moveRecords = moveService.getAllMoveRecords();
      Calendar c = Calendar.getInstance();
      c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
      Date monday = atStartOfDay(c.getTime());
      c.add(Calendar.DATE, 7);
      Date sunday = atEndOfDay(c.getTime());
//            Date firstDay = c.getFirstDayOfWeek()
      if (weekFilter != null && weekFilter != "" && weekFilter.equals(CURRENT_WEEK.toString())) {

        moveRecords = moveRecords.stream().filter(
            item -> {
              Date createdDate = item.getCreatedAt();
              System.out.println(
                  "created date vs this week" + createdDate + " vs. " + monday + " through "
                      + sunday);

              return createdDate.after(monday) && createdDate.before(sunday);
              // 0 is "NO QUESTIONS"
            }
        ).collect(Collectors.toList());
//                Collections.sort(moveRecords);

//            System.out.println("Date " + c.getTime());

      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return moveRecords;
  }

  // Adding records of moves, exercises
  @PostMapping("/move")
  @ResponseBody
  @CrossOrigin(origins = {"http://localhost:3000"})
  public Move postMoveRecord(
      @RequestBody Map<String, Object> payload) throws ParseException {
    // Have best way to deal with nulls here*
    // Need new Record Move
    Move move = new Move();
    move.setId(((Number) payload.get("id")).longValue());
    move.setName((String) payload.get("name"));
    move.setRecordType((String) payload.get("type"));
    move.setType((String) payload.get("recordType"));

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    move.setDateLastDone(String.valueOf(java.time.LocalDate.now()));

    String trueRecordDateKey = "trueRecordDate";
    Date trueRecordDate = null;
    if ((payload.get(trueRecordDateKey)) != null ){
      try {
        trueRecordDate = parseStringToDate(payload.get(trueRecordDateKey));
      } catch (ParseException e){
        e.printStackTrace();
      }
    }


    try {
      moveService.addRecord(move, (String) payload.get("recordValue"), trueRecordDate);

      System.out.println(payload);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return move;
  }

  // TODO: More to PARSE*
  private Date parseStringToDate(Object o) throws ParseException {
    if (o == null) {
      return null;
    }
    String stringDate = (String) o;
    Date date = new SimpleDateFormat("MM/dd/yyyy").parse(stringDate);
    return date;
  }

  // Helper Date Functions

  public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }


  public static Date atStartOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return localDateTimeToDate(startOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  private static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
