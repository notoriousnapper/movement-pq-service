package io.github.notoriousnapper.pqservice.model.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * Includes songs that one sings, plays with instruments, or even self-made songs and remixes.
 * For remixes but in associated remixer.
 */

@Table(name = "tbl_song_list")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongListItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, name = "id")
  @JsonProperty("id")
  Long id;

  @Column(nullable = false, name = "name")
  @JsonProperty("name")
  String name;

  @Column(nullable = false, name = "author")
  @JsonProperty("author")
  String author;

  @Column(name = "description", length = 1000)
  @JsonProperty("description")
  String description; // Using Dropshare

  @Column(name = "instruments")
  @JsonProperty("instruments")
  String instruments; // Abbreviated with comma

  @Column(name = "reference_links",  length = 1000) // How to play the song from youtube usually
  @JsonProperty("referenceLinks")
  String referenceLinks;

  @Column(name = "artifacts",  length = 1000) // guitar videos links and files
  @JsonProperty("artifacts")
  String artifacts;

  @Override
  public String toString() {
    return String.format("This song is called %s.", name, author);
  }

}

