package io.github.notoriousnapper.pqservice.commandlinerunner;

import io.github.notoriousnapper.pqservice.model.Customer;
import io.github.notoriousnapper.pqservice.repository.ICustomerRepo;
import io.github.notoriousnapper.pqservice.service.MoveService;
import io.github.notoriousnapper.pqservice.util.CSVParser;
import io.github.notoriousnapper.pqservice.model.Move;
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

    @Autowired
    ICustomerRepo customerRepo;

    // TODO: write unit Tests for self (TDD style - practice!
    // TODO: tDd you got this!
    @Override
    public void run(String... args) throws Exception {

      Customer customer = new Customer();
      customer.setName("jesse");
      customerRepo.save(customer);
      System.out.println("customer saved");





        Reader reader = Files.newBufferedReader(Paths.get(
                ClassLoader.getSystemResource("csv/moves.csv").toURI())); // Requires '/" for some reason
        List<String[]> csvData  = CSVParser.readAll(reader);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));


        // SUBSITUTE FOR TEST ATM
        List<Move> movesList = moveService.getAllMoves();
        for (Move m : movesList) {
            if (!moveService.getTypeMapBySize().containsKey(m.getRecordType())) {
                throw new Exception("CSV parsing failed - Type of move not recognized for " + m.toString());
            }
        }
        //

        Move addMove = new Move();
        addMove.setId(1);
        addMove.setName("pull lat");
        addMove.setRecordType("Duration");
        addMove.setDateLastDone(dtf.format(now));

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
