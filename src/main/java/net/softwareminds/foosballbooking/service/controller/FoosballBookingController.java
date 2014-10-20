package net.softwareminds.foosballbooking.service.controller;

import net.softwareminds.foosballbooking.service.domain.Booking;
import net.softwareminds.foosballbooking.service.repository.BookingStorage;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/bookings")
public class FoosballBookingController {

  private final BookingStorage storage;

  public FoosballBookingController(BookingStorage storage) {
    this.storage = storage;
    this.storage.storeBooking(new Booking(LocalDateTime.of(2014, 10, 8, 14, 15), LocalDateTime.of(2014, 10, 8, 14, 45), "Peter", "I need to foosball so badly!"));
    this.storage.storeBooking(new Booking(LocalDateTime.of(2014, 10, 8, 14, 45), LocalDateTime.of(2014, 10, 8, 15, 15), "Anna", "Foosball!"));
  }

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  public Collection<Booking> getBookings() {
    return storage.getAllBookings();
  }

  @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
  public void addBooking(@RequestBody Booking booking) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    booking.setUser(name);
    storage.storeBooking(booking);
  }
}
