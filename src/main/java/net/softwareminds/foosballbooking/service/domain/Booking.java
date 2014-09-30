package net.softwareminds.foosballbooking.service.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.softwareminds.foosballbooking.service.util.JsonLocalDateTimeSerializer;

import java.time.LocalDateTime;

public class Booking {
  private LocalDateTime begin=null;
  private LocalDateTime end=null;

  private String user=null;
  private String comment=null;

  public Booking(){}

  public Booking(LocalDateTime begin, LocalDateTime end, String user, String comment) {
    this.begin = begin;
    this.end = end;
    this.user = user;
    this.comment = comment;
  }

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  public LocalDateTime getBegin() {
    return begin;
  }

  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
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

    Booking booking = (Booking) o;

    if (!begin.equals(booking.begin)) {
      return false;
    }
    if (!comment.equals(booking.comment)) {
      return false;
    }
    if (!end.equals(booking.end)) {
      return false;
    }
    if (!user.equals(booking.user)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = begin.hashCode();
    result = 31 * result + end.hashCode();
    result = 31 * result + user.hashCode();
    result = 31 * result + comment.hashCode();
    return result;
  }
}
