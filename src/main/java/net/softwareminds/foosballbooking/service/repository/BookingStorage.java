package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.Collection;

public interface BookingStorage {
  public void storeBooking(Booking booking);
  public void deleteBooking(Booking booking);
  public Collection<Booking> getAllBookings();
}
