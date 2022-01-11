package controllers;

import model.Aggregate;
import model.MoveRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

      // Add list of id vs. id
      // Improve runtime*
     */
    public List<Aggregate> getMoveAggregateByIds(
            @RequestParam(required = false) String allFilter,
            @RequestBody AggregateRequest aggregateRequest
            // TODO: if all flag, then returns all
            ) throws Exception {

        if (aggregateRequest.getAggregateType() == null || aggregateRequest.getAggregateType().equals("")){
            // DO sth to eject*

        }

        Map<Integer, List<MoveRecord>> moveRecords = new HashMap<>();
        List<Aggregate> aggregateList = new ArrayList<>();
        if (allFilter != null && aggregateRequest.getAllFilter() == true){
              moveService.getAllMoveRecords().forEach(
                      record -> {
                          if (moveRecords.containsKey(record.getMoveId())){
                              moveRecords.get(record.getMoveId()).add(record);
                          }
                          else {
                              ArrayList<MoveRecord> recordList = new ArrayList<>();
                              recordList.add(record);
                              moveRecords.put(record.getMoveId(), recordList);
                          }
                      }
              );
        }
        else {

            // Build map differently
            for (String id : aggregateRequest.idList){
                moveRecords.put(Integer.valueOf(id), (moveService.getAllMoveRecordsById(Integer.valueOf(id))));
            }

        }
        try {
            // ToDO: check if things are null - moveId shouldn't be
            // TODO: ENUM
            for (Integer id : moveRecords.keySet()){
                Aggregate aggregate = new Aggregate();

                // TODO: Sum of occurence vs. values
                if ("sum".equals(aggregateRequest.getAggregateType())){
                    aggregate.setAggregateValue(
                            moveRecords.get(id).stream().mapToInt((a)->
                                    {
                                        // Todo: Cleanup method
                                    String record = a.getRecordValue();
                                    char lastChar = record.charAt(record.length()-1);
                                    if ( Character.isLetter(lastChar)){
                                        StringBuilder sb = new StringBuilder();
                                        for (char c : record.toCharArray()){
                                            if (Character.isLetter(c)){
                                                break;
                                            }
                                            sb.append(c);
                                        }
                                        record = sb.toString();
                                    }
                                    return (a.getRecordValue() == null ||
                                            a.getRecordValue().equals("null") ||
                                            a.getRecordValue().equals(""))?
                                            1: Integer.valueOf(record);
                                    }

                                    ).sum()

                    );
                    aggregate.setMove(moveService.getMoveMap().get(Integer.valueOf(id)));
                }
                aggregate.setAggregateType(aggregateRequest.getAggregateType());
                aggregateList.add(aggregate);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Sketchy, need to record value separate without string, currently recording as
        // "40s" vs. 40 in value, and then other recordTypeMetric - is in "seconds'
        // Todo: logic for "nulls" means they are recordType "justdo" vs. seconds, minutes, etc.
        return aggregateList;
    }
}
