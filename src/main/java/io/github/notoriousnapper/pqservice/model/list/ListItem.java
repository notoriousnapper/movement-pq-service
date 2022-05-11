package io.github.notoriousnapper.pqservice.model.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.notoriousnapper.pqservice.meta.IgnoredForFormData;
import io.github.notoriousnapper.pqservice.meta.RequiredForFormData;
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
 *
 * /*
 * P.T. - (testing) - sections - (Roll over)
 * SongsListItem:
 * GratitudeListItem:
 * Physical Action Item:
  Long id;
  String name;
  String author; // creator/ attributer
//  String description; // Context or useful info
  String instruments; // Song Specific
  String referenceLinks; // Examples of how tto do song, pt move, etc.
  String artifacts; // Examples of you doing it or progress or nie things
 *
 *
 *
 * P.T. Actions: (Roll out TFL, Shimmy Over Knee from Shavasana, w/ reference videos!)
 * ListItems - self contained, contain inherent action from nature of list topic
 * - usually should have "artifacts"
 * - usually have an associated move! (key move)
 * - Make CSV list & Keep adding to list (for now w/ quick access into CSV)
 * - Have "CSV" upload?
 * - Inherently - assume default is 45-50s for actual effective at ...
 * - Drawings with links!!
 *
 * Food Items
 * // States are separate *
 *
 *
 * States (health especially with files)
 * - utilizes "core tags"
 * - health snapshots - especially after checkup
 * - Interview state and readiness
 * - life snapshot/ friends
 * -
 *
 * Core CS Problems
 * Features:
 * // Grab by "Name"
 * // Add index to search by
 *
 * Medications List (and be able to send)
 * // link to share (only w/ password) / etc.

 * Interesting People, Places, Events
 * - tag is "inspirating" - inspiration and/or interesting
 */

@Table(name = "tbl_list_item")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, name = "id")
  @JsonProperty("id")
  @IgnoredForFormData
  Long id;

  @Column(nullable = false, name = "name")
  @JsonProperty("name")
  @RequiredForFormData
  String name;


  @Column(name = "description", length = 1000)
  @JsonProperty("description")
  String description; // Using Dropshare

  @Column(name = "type", length = 1000)
  @JsonProperty("type")
  @RequiredForFormData
  String type; // Using Dropshare

  @Column(name = "type_meta_data", length = 10000)
  @JsonProperty("typeMetaData")
  String typeMetaData; // Using Dropshare

  @Column(name = "tags", length = 10000)
  @JsonProperty("tags")
  String tags; // Using Dropshare

}

