package io.github.notoriousnapper.pqservice.controllers;

import io.github.notoriousnapper.pqservice.service.MoveService;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MoveControllerTest {

  /* With Two Filters*/
  MoveController moveController;
  MoveService moveService = EasyMock.createMock(MoveService.class);


  @BeforeTest
  public void beforeTest(){
    try {
      moveController = new MoveController(moveService);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Unable to construct new Move Controller.");
    }
  }

  @Test
  public void testGetAllMoves() {

  }
}