package io.github.notoriousnapper.pqservice.controllers;

import io.github.notoriousnapper.pqservice.model.SleepSummary;
import io.github.notoriousnapper.pqservice.service.SleepSummaryService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SleepSummaryController {


    @Autowired
    SleepSummaryService sleepSummaryService;

    @GetMapping("/sleep")
    @ResponseBody
    public SleepSummary provideSleepSummary(
            @RequestParam(name="startDate", required=false, defaultValue="2021-09-04")
          String  startDate,
            @RequestParam(name="endDate", required=false, defaultValue="2021-09-25")
                    String  endDate
            ){

        SleepSummary sleepSummary = new SleepSummary();
        try {
            sleepSummary = sleepSummaryService.getDefaultSleepSummary();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sleepSummary = sleepSummaryService.getSleepSummary(startDate, endDate);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return sleepSummary;
    }

}
