package net.softwareminds.foosballbooking.service.resources;

import net.softwareminds.foosballbooking.service.controller.FoosballBookingController;
import net.softwareminds.foosballbooking.service.domain.Booking;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookingResourceAssembler extends ResourceAssemblerSupport<Booking, BookingResource> {

  public BookingResourceAssembler() {
    super(FoosballBookingController.class, BookingResource.class);
  }

  @Override
  public BookingResource toResource(Booking entity) {
    return createResourceWithId(entity.getId(), entity);
  }

  @Override
  protected BookingResource instantiateResource(Booking booking) {
    return new BookingResource(booking.getBegin(), booking.getEnd(), booking.getUser(), booking.getComment());
  }

}
