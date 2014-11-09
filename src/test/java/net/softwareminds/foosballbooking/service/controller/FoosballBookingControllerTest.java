package net.softwareminds.foosballbooking.service.controller;

import net.softwareminds.foosballbooking.service.domain.Booking;
import net.softwareminds.foosballbooking.service.repository.BookingStorage;
import net.softwareminds.foosballbooking.service.resources.BookingResource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Resources;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FoosballBookingControllerTest {

  private FoosballBookingController classUnderTest;

  @Mock
  private BookingStorage mockedBookingStorage;

  private Booking testBooking1;
  private Booking testBooking2;

  @Before
  public void setUp() throws Exception {
    classUnderTest = new FoosballBookingController(mockedBookingStorage);

    testBooking1 = new Booking(LocalDateTime.of(2014, 10, 8, 14, 15), LocalDateTime.of(2014, 10, 8, 14, 45), "Peter", "I need a break!");
    testBooking2 = new Booking(LocalDateTime.of(2014,10,8, 14, 45), LocalDateTime.of(2014, 10, 8, 15, 15), "Michael", "Let's play foosball!");
  }

  @Test
  public void testGetBookings_success() {
    when(mockedBookingStorage.getAllBookings()).thenReturn(Arrays.asList(testBooking1, testBooking2));

    Resources<BookingResource> actualBookings = classUnderTest.getBookings();

    assertThat(actualBookings, contains(testBooking1, testBooking2));
  }

  @Test
  public void testAddBooking_success() {
    classUnderTest.addBooking(testBooking1);

    verify(mockedBookingStorage).storeBooking(testBooking1);
  }
}