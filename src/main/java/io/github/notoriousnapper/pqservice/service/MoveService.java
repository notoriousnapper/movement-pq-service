package io.github.notoriousnapper.pqservice.service;

import com.opencsv.CSVWriter;
import io.github.notoriousnapper.pqservice.model.Move;
import io.github.notoriousnapper.pqservice.model.MoveRecord;
import io.github.notoriousnapper.pqservice.model.MoveTypeEnum;
import io.github.notoriousnapper.pqservice.util.CSVParser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
@Data
public class MoveService {


    private Map<Integer, Move> moveMap;
    private Map<String, Integer> typeMapBySize; // 1 is largest atomsize

    public MoveService() throws Exception {

        List<Move> moves = this.getAllMoves();
        moveMap = new HashMap<>();
        for (Move move : moves){
            moveMap.put(move.getId(), move);
        }
        typeMapBySize = new HashMap<>();

        int i = 0;
        for (MoveTypeEnum m : MoveTypeEnum.values()){
            typeMapBySize.put(m.toString(), i);
            i++;
        }
    }

    public void addRecord(Move move, String recordValue) throws IOException, URISyntaxException {
            // first create file object for file placed at location
            // specified by filepath

        // TODO: Doesn't work in jar? // SHOULD be in target?
            try {
                CSVWriter w = new CSVWriter(new FileWriter(
                        ClassLoader.getSystemResource("csv/moverecords.csv").toURI().getPath(),
                        true // Means write to end of file
                        ));

                // Move ID, MoveName, RecordType, Value, Date
                StringBuilder sb = new StringBuilder();
                sb.append(move.getId()).append(",")
                        .append(move.getName()).append(",")
                        .append(move.getRecordType()).append(",")
                        .append(move.getRecordValue()).append(",")
                        .append(move.getDateLastDone()).append(",")
                        .append(recordValue); // value to record
                ;

                //Create record
                String [] record = sb.toString().split(","); // TODO - this is the tricky addition! any commas in first name will mess up
                //Write the record to file
                w.writeNext(record);

                //close the writer
                w.close();


            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // Get all Move Records
    // TODO: Improve*
    public List<MoveRecord> getAllMoveRecords() throws Exception {


        List<MoveRecord> moveRecords = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(
            "csv/moverecords.csv").toURI()));
        List<String[]> csvData  = CSVParser.readAll(reader);

            for(String[] row : csvData){
                Move move = new Move();

                if (row[0]==null){
                    // THROW exception? What to do when?
                }
                else {
//                    move.setId(Integer.valueOf(row[0]));
                    System.out.println(row[0] + "  values");
                    move = this.getMoveMap().get(Integer.valueOf(row[0]));
                }

                MoveRecord record = new MoveRecord();
                record.setMove(move);
                record.setMoveId(Integer.valueOf(row[0]));
                record.setRecordValue(row[5]);

                if (row[4] != null && "".equals(row[4])){
                    record.setDatetime(new Date().toString());
                }
                    record.setDatetime(row[4]);
                // TODO: its setting NEW, with every GET!
                // -> Its setting NEW
                moveRecords.add(record);


            }
//        }
//        else {
//            System.out.println("!\nERROR!\nCheck in CSV formatting or data. Check if fields are up-to-date");
//        }

        return moveRecords;



    }
    // TODO: Increase performance with better algorithms* // or overall rewrite
    public List<MoveRecord> getAllMoveRecordsById(Integer id) throws Exception {

        // TODO: get only the one by ID*
        List<MoveRecord> moveRecords = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(
            "csv/moverecords.csv").toURI()));
        List<String[]> csvData  = CSVParser.readAll(reader);

        for(String[] row : csvData){

            if (row[0].equals(String.valueOf(id))){ // Match of String

                Move move = new Move();
                MoveRecord record = new MoveRecord();
                record.setMove(move);
                record.setMoveId(Integer.valueOf(row[0]));
                record.setRecordValue(row[5]);
                // TODO: Important
                if (row[4] != null && "".equals(row[4])){
                    record.setDatetime(new Date().toString());
                }
                record.setDatetime(row[4]);
                // TODO: its setting NEW, with every GET!
                // -> Its setting NEW
                moveRecords.add(record);
            }




        }
        return moveRecords;

    }

    public List<Move> getAllMoves() throws Exception {

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(
            "csv/moves.csv").toURI()));
        List<String[]> csvData  = CSVParser.readAll(reader);
        List<Move> moves = new ArrayList<>();

        // Skip first part
        if (testMovesFormat(csvData.get(0))) {
            csvData.remove(0); // Remove column titles

            for(String[] row : csvData){
                Move move = new Move();

                if (row[0]==null){
                    // THROW exception? What to do when?
                }
                else {
                    move.setId(Integer.valueOf(row[0]));
                }

                if (row[4]==null){
                    // THROW exception? What to do when?
                }
                else {
                    move.setPriority(Integer.valueOf(row[4]));
                }


                move.setName(row[1]);
                move.setType(row[2]);
                move.setRecordType(row[3]);
                move.setDateLastDone(row[5]);
                move.setImageURL(row[6]);
                move.setDescription(row[7]);
                move.setTags(row[8].split(","));

                moves.add(move);
            }
        }
        else {
            System.out.println("!\nERROR!\nCheck in CSV formatting or data. Check if fields are up-to-date");
        }
        return moves;
    }



    public boolean testMovesFormat(String[] strings) {

        return strings[0].equals("Id")
                &&	strings[1].equals("Move")
                && strings[2].equals("Type")
                && strings[3].equals("RecordType")
                && strings[4].equals("Priority")
                && strings[5].equals("DateLastDone");
    }

    public Map<Integer, Move> getMoveMap() {
        return moveMap;
    }
}
