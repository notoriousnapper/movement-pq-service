package io.github.notoriousnapper.pqservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.notoriousnapper.pqservice.util.HashMapConverter;
import java.io.IOException;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "tbl_moverecords")
@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoveRecord {

  // metadata - for now
  @Convert(converter = HashMapConverter.class)
  @Column(name = "move", length = 1337)
  Move move;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false, name = "id")
  @JsonProperty("id")
  Long id;


  @Column(nullable = false, name = "move_id")
  @JsonProperty("moveId")
  Long moveId;

  @Column(name = "record_value")
  @JsonProperty("recordValue")
  @Lob
  String recordValue; //m, min, sec, s,

  @JsonProperty("dateTime")
  String datetime; // TODO: Figure out best practices!

//  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

}
