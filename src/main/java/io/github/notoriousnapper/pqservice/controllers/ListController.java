package io.github.notoriousnapper.pqservice.controllers;

import io.github.notoriousnapper.pqservice.model.list.ListItem;
import io.github.notoriousnapper.pqservice.model.list.SongListItem;
import io.github.notoriousnapper.pqservice.service.ListService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListController {


  @Autowired
  ListService listService;


  @GetMapping("/list")
  @ResponseBody
  public List<ListItem> getAllListItems(
  ) {
    List<ListItem> listItems = new ArrayList<>();
    try {
      return listService.getAllListItems();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listItems;
  }

  @GetMapping("/list/{type}")
  @ResponseBody
  public List<ListItem> getAllListItemsByType(@PathVariable String type) {
    List<ListItem> listItems = new ArrayList<>();
    try {
      return listService.getAllListItemsByType(type);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listItems;
  }

  @PostMapping("/list")
  @ResponseBody
  @CrossOrigin(origins = {"http://localhost:3000"})
  public ListItem postListItem(
      @RequestBody Map<String, Object> payload) throws ParseException {
    // given type -> conform
    ListItem listItem = conformPayLoadToListItem(payload);
    try {
      listService.addListItem(listItem);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return listItem;
  }

  private ListItem conformPayLoadToListItem(Map<String, Object> payload) {

    ListItem listItem = new ListItem();
    listItem.setName((String) payload.get("name"));
    listItem.setType((String) payload.get("type"));
    listItem.setDescription((String) payload.get("description"));
    listItem.setTypeMetaData((String) payload.get("typeMetaData"));
    return listItem;
  }


  /* SongList */
  @GetMapping("/listsong")
  @ResponseBody
  public List<SongListItem> getAllMoves(
  ) {

    List<SongListItem> songs = new ArrayList<>();
    try {
      return listService.getAllSongListItems();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return songs;
  }

  @PostMapping("/listsong")
  @ResponseBody
  @CrossOrigin(origins = {"http://localhost:3000"})
  public SongListItem postSongListItem(
      @RequestBody Map<String, Object> payload) throws ParseException {
    SongListItem song = conformPayLoadToSong(payload);

    try {
      listService.addSongListItem(song);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return song;
  }


  @DeleteMapping("/listsong")
  @ResponseBody
  @CrossOrigin(origins = {"http://localhost:3000"})
  public void deleteListItem(
      @RequestBody Map<String, Object> payload) throws ParseException {
    try {
      listService.deleteSongListItem((String) payload.get("id"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private SongListItem conformPayLoadToSong(Map<String, Object> payload) {
    SongListItem songListItem = new SongListItem();
    if (payload.get("id") != null) {
      songListItem.setId(((Number) payload.get("id")).longValue());
    }
    songListItem.setDescription((String) payload.get("description"));
    songListItem.setInstruments((String) payload.get("instruments"));
    songListItem.setName((String) payload.get("name"));
    songListItem.setAuthor((String) payload.get("author"));
    songListItem.setArtifacts((String) payload.get("artifacts"));
    songListItem.setReferenceLinks((String) payload.get("references"));
    return songListItem;
  }


}
