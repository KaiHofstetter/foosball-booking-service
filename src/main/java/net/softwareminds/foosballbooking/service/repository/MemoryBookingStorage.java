package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class MemoryBookingStorage implements BookingStorage {

  Map<String, Booking> store = new TreeMap<>();

  @Override
  public Booking storeBooking(Booking booking) {
    String id = UUID.randomUUID().toString();
    booking.setId(id);
    store.put(id, booking);
    return booking;
  }

  @Override
  public void deleteBooking(String bookingId) {
    store.remove(bookingId);
  }

  @Override
  public Booking getBooking(String bookingId) {
    return store.get(bookingId);
  }

  @Override
  public List<Booking> getAllBookings() {
    return new ArrayList(store.values());
  }
}
