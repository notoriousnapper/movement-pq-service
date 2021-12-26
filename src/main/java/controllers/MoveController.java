package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/move")
//@Controller

public class MoveController {


    @Autowired
    MoveService moveService;

    @GetMapping("/move")
    @ResponseBody
    public List<Move> getAllMoves(
    ) {

        List<Move> moves = new ArrayList<>();
        try {
            moves = moveService.getAllMoves();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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

    @PostMapping("/move")
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000") // TODO: Delete once figure out
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
