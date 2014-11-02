package net.softwareminds.foosballbooking.service.controller;

import net.softwareminds.foosballbooking.service.domain.Booking;
import net.softwareminds.foosballbooking.service.repository.BookingStorage;
import net.softwareminds.foosballbooking.service.resources.BookingResource;
import net.softwareminds.foosballbooking.service.resources.BookingResourceAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/bookings")
public class FoosballBookingController {

  private final BookingStorage storage;

  @Autowired
  private BookingResourceAssembler bookingResourceAssembler;

  public FoosballBookingController(BookingStorage storage) {
    this.storage = storage;
    this.storage.storeBooking(new Booking(LocalDateTime.of(2014, 10, 8, 14, 15), LocalDateTime.of(2014, 10, 8, 14, 45), "Peter", "I need to foosball so badly!"));
    this.storage.storeBooking(new Booking(LocalDateTime.of(2014, 10, 8, 14, 45), LocalDateTime.of(2014, 10, 8, 15, 15), "Anna", "Foosball!"));
  }

  @RequestMapping(method = RequestMethod.GET, produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
  public Resources<BookingResource> getBookings() {
    List<Booking> bookingList = storage.getAllBookings();
    Resources<BookingResource> bookingListResource = new Resources<BookingResource>(bookingResourceAssembler.toResources(bookingList));
    bookingListResource.add(linkTo(methodOn(getClass()).getBookings()).withSelfRel());
    return bookingListResource;
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Void> addBooking(@RequestBody Booking booking) {
    setAuthenticatedUser(booking);

    storage.storeBooking(booking);

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(linkTo(methodOn(getClass()).getBooking(booking.getId().toString())).toUri());
    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
  }

  private void setAuthenticatedUser(Booking booking) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if(auth!=null) {
      String name = auth.getName();
      booking.setUser(name);
    }
  }

  @RequestMapping(value="/{id}", method = RequestMethod.GET, produces = {"application/hal+json", MediaType.APPLICATION_JSON_VALUE})
  public BookingResource getBooking(@PathVariable("id") String id) {
    return bookingResourceAssembler.toResource(storage.getBooking(id));
  }

  @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> deleteBooking(@PathVariable("id") String id) {
    storage.deleteBooking(id);

    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
  }
}
