package io.github.notoriousnapper.pqservice.controllers;


import java.util.List;
import io.github.notoriousnapper.pqservice.model.Move;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.github.notoriousnapper.pqservice.service.MoveService;

class MoveServiceTest {

  MoveService moveService = new MoveService();

  MoveServiceTest() throws Exception {
  }

  @Test
  void getAllMoves() throws Exception {

    List<Move> moves = moveService.getAllMoves();
    for (Move m : moves) {
      if (!moveService.getTypeMapBySize().containsKey(m.getRecordType())) {
        Assert.fail("CSV parsing failed - Type of move not recognized for " + m.toString());
      }
    }

  }
}