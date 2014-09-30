package net.softwareminds.foosballbooking.service.controller;

import net.softwareminds.foosballbooking.service.domain.Booking;
import net.softwareminds.foosballbooking.service.repository.BookingStorage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/booking")
public class FoosballBookingController {

  private final BookingStorage storage;

  public FoosballBookingController(BookingStorage storage) {
    this.storage = storage;

    //Test data...
    storage.addBooking(new Booking(LocalDateTime.of(2014, 10, 23, 15, 15), LocalDateTime.of(2014, 10, 23, 15, 45), "Peter", "Comment"));
    storage.addBooking(new Booking(LocalDateTime.of(2014, 10, 23, 16, 00), LocalDateTime.of(2014, 10, 23, 16, 30), "Peter", "Comment"));
  }

  @RequestMapping(method = RequestMethod.GET)
  public ArrayList<Booking> getAppointments() {
    return storage.getAllBookings();
  }
}
