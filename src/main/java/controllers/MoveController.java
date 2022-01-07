package controllers;

import model.Move;
import model.MoveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static model.MoveEnum.PRIORITY;

@RestController
public class MoveController {


    @Autowired
    MoveService moveService;

    @GetMapping("/move")
    @ResponseBody
    public List<Move> getAllMoves(
            @RequestParam(required = false) String filter
    ) {

        List<Move> moves = new ArrayList<>();
        try {
            moves = moveService.getAllMoves();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (filter != (null)){
            // TODO: Add in clause for nothing, empty string, might be mistake
            if (filter.equals(PRIORITY.toString())){
                moves = moves.stream().filter(
                        item -> item.getPriority() <= 1 // 0 is "NO QUESTIONS"
                ).collect(Collectors.toList());
                Collections.sort(moves);
            }
        }

        return moves;
    }

    @GetMapping("/moverecords")
    @ResponseBody
    public List<MoveRecord> getAllMoveRecords(
    ) {

        List<MoveRecord> moves = new ArrayList<>();
        try {
            moves = moveService.getAllMoveRecords();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return moves;
    }

    // Adding records of moves, exercises
    @PostMapping("/move")
    @ResponseBody
    @CrossOrigin(origins = {"http://localhost:3000" })
    public Move postMove(
            @RequestBody Map<String, String> payload){
//            @RequestBody Move move){

        // Have best way to deal with nulls here*
        // Need new Record Move
        Move move = new Move();
        move.setId(( Integer.valueOf(payload.get("id"))));
        move.setName(payload.get("name"));
        move.setRecordType(payload.get("type"));
        move.setType(payload.get("recordType"));


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        move.setDateLastDone(String.valueOf(java.time.LocalDate.now()));


        // tODO: Do test to make sure sets right things


        try {
            moveService.addRecord(move, (String) payload.get("recordValue"));

            System.out.println(payload);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return move;
    }
}
