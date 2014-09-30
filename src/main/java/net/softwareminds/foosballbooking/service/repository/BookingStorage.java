package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import java.util.ArrayList;

public class BookingStorage {

  ArrayList<Booking> store = new ArrayList<Booking>();

  public void addBooking(Booking newBooking) {
    store.add(newBooking);
  }

  public ArrayList<Booking> getAllBookings() {
    return store;
  }
}
