package net.softwareminds.foosballbooking.service.repository;

import net.softwareminds.foosballbooking.service.domain.Booking;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class MemoryBookingStorageTest {

  private MemoryBookingStorage classUnderTest;
  private Booking testBooking;

  @Before
  public void setUp() throws Exception {
    classUnderTest = new MemoryBookingStorage();
    testBooking = new Booking(LocalDateTime.of(2014, 10,8, 15, 15),LocalDateTime.of(2014, 10,8, 15, 45), "Peter", "I need to play foosball badly!");
  }

  @Test
  public void testStoreBookingAndGetAllBookings_success() {
    classUnderTest.storeBooking(testBooking);

    assertThat(classUnderTest.getAllBookings(), contains(testBooking));
  }

  @Test
  public void testDeleteBooking_success() {
    Booking storedBooking = classUnderTest.storeBooking(testBooking);
    classUnderTest.deleteBooking(storedBooking.getId());

    assertThat(classUnderTest.getAllBookings().size(), is(0));
  }
}