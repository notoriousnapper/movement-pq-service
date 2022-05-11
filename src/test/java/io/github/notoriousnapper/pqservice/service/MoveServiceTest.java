package io.github.notoriousnapper.pqservice.service;

import io.github.notoriousnapper.pqservice.model.Move;
import io.github.notoriousnapper.pqservice.model.MoveRecord;
import io.github.notoriousnapper.pqservice.repository.IMoveRecordsRepo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/*
 * Parallel: https://howtodoinjava.com/testng/testng-executing-parallel-tests/
 */
public class MoveServiceTest {


  MoveService moveService;
  IMoveRecordsRepo iMoveRecordsRepo = EasyMock.createMock(IMoveRecordsRepo.class);

  @BeforeTest
  public void beforeTest(){
    try {
      moveService = new MoveService(iMoveRecordsRepo);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Unable to construct new Move Service.");
    }
  }

  @AfterTest
  public void afterTest(){
    System.out.println("After Test");
  }

  @Test
  public void testService(){

  }

  @Test
  public void testAddMoveRecord() throws IOException, URISyntaxException {
    Capture<MoveRecord> capturedArgument = new Capture();

    MoveRecord moveRecord = new MoveRecord();
    Move move = new Move();
    String recordValue = "10s";
    Date trueRecordDate = new Date();
    moveRecord.setRecordValue(recordValue);
    moveRecord.setTrueRecordDate(trueRecordDate);


    EasyMock.expect(
        iMoveRecordsRepo.save(EasyMock.capture(capturedArgument))).andReturn(moveRecord);
    EasyMock.replay(iMoveRecordsRepo); // Usually people do replayAll
    moveService.addRecord(move, recordValue, trueRecordDate);
    EasyMock.verify(iMoveRecordsRepo);
    Assert.assertEquals(capturedArgument.getValue().getMoveId(), moveRecord.getMoveId());
    Assert.assertEquals(capturedArgument.getValue().getRecordValue(), moveRecord.getRecordValue());
    Assert.assertEquals(capturedArgument.getValue().getTrueRecordDate(), moveRecord.getTrueRecordDate());
  }

  @Test
  public void testAddMoveRecordWithNullDate() throws IOException, URISyntaxException {
    Capture<MoveRecord> capturedArgument = new Capture();

    MoveRecord moveRecord = new MoveRecord();
    Move move = new Move();
    String recordValue = "10s";
    moveRecord.setRecordValue(recordValue);

    EasyMock.expect(
        iMoveRecordsRepo.save(EasyMock.capture(capturedArgument))).andReturn(moveRecord);
    EasyMock.replay(iMoveRecordsRepo); // Usually people do replayAll
    moveService.addRecord(move, recordValue, null);
    EasyMock.verify(iMoveRecordsRepo);
    Assert.assertEquals(capturedArgument.getValue().getMoveId(), moveRecord.getMoveId());
    Assert.assertEquals(capturedArgument.getValue().getRecordValue(), moveRecord.getRecordValue());
    Assert.assertNotNull(capturedArgument.getValue().getTrueRecordDate());
  }
}