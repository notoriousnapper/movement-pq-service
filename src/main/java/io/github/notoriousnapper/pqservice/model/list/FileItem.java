package io.github.notoriousnapper.pqservice.model.list;

/*
 * Deciding no to use ListItem as base because listitem might change
 * and I don't wan't file item to be coupled.
 * Also fileitem potentially will be just file data vs. link
 */

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
import lombok.Data;

@Table(name = "tbl_file_item")
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, name = "id")
  @JsonProperty("id")
  @IgnoredForFormData
  Long id;

  @Column(nullable = false, name = "name")
  @RequiredForFormData
  @JsonProperty("name")
  String name;

  @Column(name = "description", length = 1000)
  @JsonProperty("description")
  String description; // Using Dropshare

  @Column(name = "type", length = 1000)
  @JsonProperty("type")
  String type; // Using Dropshare

  @Column(name = "file_url", length = 1000) // FileTypes
  @JsonProperty("fileURL")
  String fileURL; // Using Dropshare

  @Column(name = "thumbnail_url", length = 1000) // FileTypes
  @JsonProperty("thumbnailURL")
  String thumbnailURL;

  @Column(name = "type_meta_data", length = 10000)
  @JsonProperty("typeMetaData")
  String typeMetaData;

  @Column(name = "tags", length = 10000)
  @JsonProperty("tags")
  String tags;

}

