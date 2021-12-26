package controllers;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class MoveService {

    public void addRecord(Move move, String recordValue) throws IOException, URISyntaxException {
            // first create file object for file placed at location
            // specified by filepath

        // TODO: Doesn't work in jar? // SHOULD be in target?
        String filePath = "/Users/renly/Development/Java/life-dashboard-service/src/main/resources/csv/moverecords.csv";
        URL resource = getClass().getClassLoader().getResource(filePath);
        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);


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
                String [] record = sb.toString().split(",");
//                        "4,David,Miller,Australia,30".split(",");
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

        List<Move> moves = getAllMoves();
        Map<Integer, Move> moveMap = new HashMap<>();
        for (Move move : moves){
           moveMap.put(move.getId(), move);
        }

        List<MoveRecord> moveRecords = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("csv/moverecords.csv").toURI()));
        List<String[]> csvData  = CSVParser.readAll(reader);

        // Skip first part
//        if (testMovesFormat(csvData.get(0))) {
            // TODO: check if column tiles exist

            for(String[] row : csvData){
                Move move = new Move();

                if (row[0]==null){
                    // THROW exception? What to do when?
                }
                else {
//                    move.setId(Integer.valueOf(row[0]));
                    System.out.println(row[0] + "  values");
                    move = moveMap.get(Integer.valueOf(row[0]));
                }

                MoveRecord record = new MoveRecord();
                record.setMove(move);
                record.setMoveId(Integer.valueOf(row[0]));
                record.setRecordValue(row[5]);
                record.setDatetime(new Date().toString());
                moveRecords.add(record);


            }
//        }
//        else {
//            System.out.println("!\nERROR!\nCheck in CSV formatting or data. Check if fields are up-to-date");
//        }

        return moveRecords;



    }

    public List<Move> getAllMoves() throws Exception {


        String filePath = "/Users/renly/Development/Java/life-dashboard-service/src/main/resources/csv/moverecords.csv";
        URL resource = getClass().getClassLoader().getResource(filePath);


        // WORK
        System.out.println("\ntriccks: " +  ClassLoader.getSystemResource("csv/moves-old.csv").toURI());
        System.out.println( ClassLoader.getSystemResource("").toURI());
//        System.out.println(resource.toURI());


        Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("csv/moves-old.csv").toURI()));
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


                System.out.println(move.toString());

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

}
