package controllers;

import model.MoveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AggregateController {


    @Autowired
    MoveService moveService;

    @GetMapping("/aggregates")
    @ResponseBody
    /*
      Return aggregate object, with name, move integrated, and
      the aggregate as needed!
      for now, its just the aggregate value for total (sum)
     */
    public Integer getMoveAggregate(
            @RequestParam(required = false) String id
    ) {

        List<MoveRecord> moveRecords = new ArrayList<>();
        try {
            // ToDO: check if things are null - moveId shouldn't be
            moveRecords = moveService.getAllMoveRecordsById(Integer.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Sketchy, need to record value separate without string, currently recording as
        // "40s" vs. 40 in value, and then other recordTypeMetric - is in "seconds'
        // Todo: logic for "nulls" means theyy are recordType "justdo" vs. seconds, minutes, etc.
        return moveRecords.stream().mapToInt((a)->
                        (a.getRecordValue().equals("null") ||
                                a.getRecordValue() == null ||
                                a.getRecordValue().equals(""))?
                                1:
                Integer.valueOf(a.getRecordValue())).sum();
    }
}
