package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryBookingStorage implements BookingStorage {

  Map<UUID, Booking> store = new HashMap<>();

  @Override
  public void storeBooking(Booking booking) {
    store.put(booking.getId(), booking);
  }

  @Override
  public void deleteBooking(Booking booking) {
    store.remove(booking.getId());
  }

  @Override
  public Collection<Booking> getAllBookings() {
    return store.values();
  }
}
