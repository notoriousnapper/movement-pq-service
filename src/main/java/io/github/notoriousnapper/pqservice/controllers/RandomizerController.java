package io.github.notoriousnapper.pqservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.github.notoriousnapper.pqservice.service.RandomizerService;

/*
 * Given a listkey, will return an item in the appropriate list at random,
 * or depending on LRU and other rules
 */
@RestController
public class RandomizerController {

  @Autowired
  RandomizerService randomizerService;

  @GetMapping("/randomize/{key}")
  public ResponseEntity<String> getRandomItem(@PathVariable String key) {
    try {
        return new ResponseEntity<String>(randomizerService.returnRandom(key), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
