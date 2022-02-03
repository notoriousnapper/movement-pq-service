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

import static model.MoveEnumSorting.*;

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
        for (String f : filters){
            moves = applyFilter(f, moves);
        }

        return moves;
    }

    private List<Move> applyFilter(String f, List<Move> moves) {


        if (f == null){ return moves; }
        // TODO: Make filter into array & parse - filter 1, 2, into below
            // TODO: Add in clause for nothing, empty string, might be mistake
        else if (f.equals(PRIORITY.toString())){
                moves = moves.stream().filter(
                        item -> item.getPriority() <= 1 // 0 is "NO QUESTIONS"
                ).collect(Collectors.toList());
                Collections.sort(moves);
        }
        else if (f != null){
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
            @RequestBody Map<String, Object> payload){
//            @RequestBody Move move){

        // Have best way to deal with nulls here*
        // Need new Record Move
        Move move = new Move();
        move.setId(( Integer) payload.get("id"));
        move.setName((String) payload.get("name"));
        move.setRecordType((String) payload.get("type"));
        move.setType((String) payload.get("recordType"));


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        move.setDateLastDone(String.valueOf(java.time.LocalDate.now()));


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
