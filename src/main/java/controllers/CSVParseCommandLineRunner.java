package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVParseCommandLineRunner implements CommandLineRunner {
//

    @Autowired
    MoveService moveService;

    // TODO: write unit Tests for self (TDD style - practice!
    // TODO: tDd you got this!
    @Override
    public void run(String... args) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("csv/moves-old.csv").toURI())); // Requires '/" for some reason
        List<String[]> csvData  = CSVParser.readAll(reader);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        Move addMove = new Move();
        addMove.setId(1);
        addMove.setName("pull lat");
        addMove.setRecordType("Duration");
//        addMove.setRecordValue("30s");
        addMove.setDateLastDone(dtf.format(now));

        moveService.addRecord(addMove, "30s");

        // Skip first part
        if (moveService.testMovesFormat(csvData.get(0))) {
            csvData.remove(0); // Remove column titles

            List<Move> moves = new ArrayList<>();
            for(String[] row : csvData){
                Move move = new Move();
                move.setId(Integer.valueOf(row[0]));
                move.setName(row[1]);
                move.setType(row[2]);
                move.setRecordType(row[3]);
                System.out.println(move.toString());

                moves.add(move);
            }
        }
        else {
            System.out.println("!\nERROR!\nCheck in CSV formatting or data. Check if fields are up-to-date");
        }



        return;
    }


}
