package io.github.notoriousnapper.pqservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.notoriousnapper.pqservice.model.SleepSummary;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SleepSummaryService {

  @Autowired
  RestTemplate restTemplate;
  URIBuilder baseOuraURI;

  public SleepSummaryService() throws URISyntaxException, MalformedURLException {
    String baseOuraUrlString = "https://api.ouraring.com";
    baseOuraURI = new URIBuilder(baseOuraUrlString);
    baseOuraURI.setPath("/v1/sleep");
    baseOuraURI.addParameter("start", "2021-09-01");
    baseOuraURI.addParameter("end", "2021-09-21");
    baseOuraURI.addParameter("access_token", "3JEBRXC3GYTA6UVHK2KR2LDKAEE3FQOV");

  }

  public SleepSummary getSleepSummary(String startDate, String endDate) throws IOException {

    SleepSummary sleepSummary = getDefaultSleepSummary();

    try {
      baseOuraURI.setParameter("start", startDate);
      baseOuraURI.setParameter("end", endDate);

      System.out.println("Start Date for query:" + startDate + " End Date for query " + endDate);

      sleepSummary = restTemplate.getForObject(
          baseOuraURI.build().toURL().toExternalForm(), SleepSummary.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sleepSummary;
  }

  public SleepSummary getSleepSummary() throws IOException {
    SleepSummary sleepSummary = getDefaultSleepSummary();

    try {
      sleepSummary = restTemplate.getForObject(
          baseOuraURI.build().toURL().toExternalForm(),
          SleepSummary.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sleepSummary;
  }

  public SleepSummary getDefaultSleepSummary() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream is;
    try {
          is = SleepSummary.class.getResourceAsStream("/sleep.json");

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    if (is == null){
      return null;
    }
    return mapper.readValue(is, SleepSummary.class);
  }
}
