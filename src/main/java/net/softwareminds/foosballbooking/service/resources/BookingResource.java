package net.softwareminds.foosballbooking.service.resources;

import net.softwareminds.foosballbooking.service.domain.Booking;

import org.springframework.hateoas.Resource;

public class BookingResource extends Resource<Booking> {

  public BookingResource(Booking entity){
    super(entity);
  }

}
