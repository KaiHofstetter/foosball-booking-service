package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryBookingStorage implements BookingStorage {

  Map<UUID, Booking> store = new HashMap<>();

  @Override
  public Booking storeBooking(Booking booking) {
    UUID id = UUID.randomUUID();
    booking.setId(id);
    store.put(id, booking);
    return booking;
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
