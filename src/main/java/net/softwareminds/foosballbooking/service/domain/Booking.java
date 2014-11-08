package net.softwareminds.foosballbooking.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.softwareminds.foosballbooking.service.util.JsonLocalDateTimeDeserializer;
import net.softwareminds.foosballbooking.service.util.JsonLocalDateTimeSerializer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.hateoas.Identifiable;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {

  @JsonIgnore
  private String id = null;

  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  private LocalDateTime begin = null;

  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  private LocalDateTime end = null;

  private String user = null;
  private String comment = null;

  public Booking() {
  }

  public Booking(LocalDateTime begin, LocalDateTime end, String user, String comment) {
    this(null, begin, end, user, comment);
  }

  public Booking(String id, LocalDateTime begin, LocalDateTime end, String user, String comment) {
    this.id = id;
    this.begin = begin;
    this.end = end;
    this.user = user;
    this.comment = comment;
  }

  public String getId() {
    return id;
  }

  public LocalDateTime getBegin() {
    return begin;
  }

  public LocalDateTime getEnd() {
    return end;
  }

  public String getUser() {
    return user;
  }

  public String getComment() {
    return comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Booking booking = (Booking) o;

    return new EqualsBuilder().append(this.begin, booking.begin)
                              .append(this.end, booking.end)
                              .append(this.comment, booking.comment)
                              .append(this.user, booking.user)
                              .build();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(begin).append(end).append(user).append(comment).hashCode();
  }
}
