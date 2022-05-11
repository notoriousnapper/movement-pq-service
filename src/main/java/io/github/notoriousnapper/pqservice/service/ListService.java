package io.github.notoriousnapper.pqservice.service;


import io.github.notoriousnapper.pqservice.model.list.ListItem;
import io.github.notoriousnapper.pqservice.model.list.SongListItem;
import io.github.notoriousnapper.pqservice.repository.IListRepo;
import io.github.notoriousnapper.pqservice.repository.ISongListRepo;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class ListService {

  @Autowired
  ISongListRepo songListRepo;

  @Autowired
  IListRepo listRepo;

  public List<ListItem> getAllListItems() {
    return listRepo.findAll();
  }

  public List<ListItem> getAllListItemsByType(String type) {
    return listRepo.findAllByType(type);
  }

  public void addListItem(ListItem listItem){ listRepo.save(listItem); }

  // Song Items
  public void addSongListItem(SongListItem song){
    songListRepo.save(song);
  }

  public SongListItem getSongListItem(long id){
    return songListRepo.findById(id).get();
  }

  public List<SongListItem> getAllSongListItems(){
    return songListRepo.findAll();
  }

  public void deleteSongListItem(String id) {
    SongListItem songListItem = new SongListItem();
    songListItem.setId(Long.valueOf(id));
    songListRepo.delete(songListItem);
  }
}
