package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.Collection;
import java.util.UUID;

public interface BookingStorage {
  public Booking storeBooking(Booking booking);
  public void deleteBooking(Booking booking);
  public Collection<Booking> getAllBookings();
}
