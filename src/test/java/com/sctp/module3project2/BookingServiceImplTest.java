package com.sctp.module3project2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.entity.Booking;
import com.sctp.module3project2.entity.BookingDateTime;
import com.sctp.module3project2.entity.ShippingRoute;
import com.sctp.module3project2.entity.Vessel;
import com.sctp.module3project2.repository.BookingRepository;
import com.sctp.module3project2.services.BookingServiceImpl;


// Joel

@SpringBootTest
public class BookingServiceImplTest {
    
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void createBookingTest() {
        BookingDateTime bookingDateTime = new BookingDateTime((long) 1, "2023-04-01", "12:00");

        Berth berth = new Berth((long)1, "berth1", "east", true);
        ShippingRoute shippingRoute1 = new ShippingRoute();
        shippingRoute1.setId(1);
        shippingRoute1.setPort("Singapore");
        shippingRoute1.setDate_of_arrival(LocalDate.of(2023,06,10));
        shippingRoute1.setPurpose_of_travel("visit");
        shippingRoute1.setTax_fees_port_expenses(0.0);
        ShippingRoute shippingRoute2 = new ShippingRoute();
        shippingRoute2.setId(2);
        shippingRoute2.setPort("Malaysia");
        shippingRoute2.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute2.setPurpose_of_travel("visit");
        shippingRoute2.setTax_fees_port_expenses(0.0);

        List<ShippingRoute> shippingRoutes = new ArrayList<>();
        shippingRoutes.add(shippingRoute1);
        shippingRoutes.add(shippingRoute2);
        Vessel vessel = new Vessel((long)1, "Titanic", "Large-class", shippingRoutes);

        Booking booking = new Booking((long)1, bookingDateTime, berth, vessel,  "activity", "remarks");

        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking savedBooking = bookingService.saveBooking(booking);

        verify(bookingRepository, times(1)).save(booking);
        assertEquals(booking, savedBooking);
    }

    @Test
    public void getBookingTest() {
        BookingDateTime bookingDateTime = new BookingDateTime((long) 1, "2023-04-01", "12:00");

        Berth berth = new Berth((long)1, "berth1", "east", true);

        ShippingRoute shippingRoute1 = new ShippingRoute();
        shippingRoute1.setId(1);
        shippingRoute1.setPort("Singapore");
        shippingRoute1.setDate_of_arrival(LocalDate.of(2023,06,10));
        shippingRoute1.setPurpose_of_travel("visit");
        shippingRoute1.setTax_fees_port_expenses(0.0);
        ShippingRoute shippingRoute2 = new ShippingRoute();
        shippingRoute2.setId(2);
        shippingRoute2.setPort("Malaysia");
        shippingRoute2.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute2.setPurpose_of_travel("visit");
        shippingRoute2.setTax_fees_port_expenses(0.0);

        List<ShippingRoute> shippingRoutes = new ArrayList<>();
        shippingRoutes.add(shippingRoute1);
        shippingRoutes.add(shippingRoute2);

        Vessel vessel = new Vessel((long)1, "Titanic", "Large-class", shippingRoutes);
        
        Booking booking = new Booking((long)1, bookingDateTime, berth, vessel,  "activity", "remarks");
        
        when(bookingRepository.findById((long) 1)).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.getBooking((long) 1);

        verify(bookingRepository, times(1)).findById((long) 1);
        assertEquals(booking, foundBooking);
    }

    @Test
    public void getAllBookingsTest() {
        BookingDateTime bookingDateTime = new BookingDateTime((long) 1, "2023-04-01", "12:00");

        Berth berth = new Berth((long)1, "berth1", "east", true);

        ShippingRoute shippingRoute1 = new ShippingRoute();
        shippingRoute1.setId(1);
        shippingRoute1.setPort("Singapore");
        shippingRoute1.setDate_of_arrival(LocalDate.of(2023,06,10));
        shippingRoute1.setPurpose_of_travel("visit");
        shippingRoute1.setTax_fees_port_expenses(0.0);
        ShippingRoute shippingRoute2 = new ShippingRoute();
        shippingRoute2.setId(2);
        shippingRoute2.setPort("Malaysia");
        shippingRoute2.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute2.setPurpose_of_travel("visit");
        shippingRoute2.setTax_fees_port_expenses(0.0);

        List<ShippingRoute> shippingRoutes = new ArrayList<>();
        shippingRoutes.add(shippingRoute1);
        shippingRoutes.add(shippingRoute2);

        Vessel vessel = new Vessel((long)1, "Titanic", "Large-class", shippingRoutes);
        
        Booking booking = new Booking((long)1, bookingDateTime, berth, vessel,  "activity", "remarks");
        

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findAll()).thenReturn(bookings);

       ArrayList<Booking> allBookings = bookingService.getAllBookings();

        verify(bookingRepository, times(1)).findAll();
        assertEquals(1, allBookings.size()); // change to 1
    }

    @Test
    public void updateBookingTest() {
        BookingDateTime bookingDateTime = new BookingDateTime((long) 1, "2023-04-01", "12:00");

        Berth berth = new Berth((long)1, "berth1", "east", true);

        ShippingRoute shippingRoute1 = new ShippingRoute();
        shippingRoute1.setId(1);
        shippingRoute1.setPort("Singapore");
        shippingRoute1.setDate_of_arrival(LocalDate.of(2023,06,10));
        shippingRoute1.setPurpose_of_travel("visit");
        shippingRoute1.setTax_fees_port_expenses(0.0);
        ShippingRoute shippingRoute2 = new ShippingRoute();
        shippingRoute2.setId(2);
        shippingRoute2.setPort("Malaysia");
        shippingRoute2.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute2.setPurpose_of_travel("visit");
        shippingRoute2.setTax_fees_port_expenses(0.0);

        List<ShippingRoute> shippingRoutes = new ArrayList<>();
        shippingRoutes.add(shippingRoute1);
        shippingRoutes.add(shippingRoute2);

        Vessel vessel = new Vessel((long)1, "Titanic", "Large-class", shippingRoutes);
        
        Booking booking = new Booking((long)1, bookingDateTime, berth, vessel,  "activity", "remarks");

        // test update booking
        when(bookingRepository.findById((long) 1)).thenReturn(Optional.of(booking));
        BookingDateTime bookingDateTime2 = new BookingDateTime((long) 1, "2023-04-01", "13:00");

        Berth berth2 = new Berth((long)1, "berth1", "east", true);

        // ShippingRoute shippingRoute3 = new ShippingRoute();
        shippingRoute1.setId(1);
        shippingRoute1.setPort("test");
        shippingRoute1.setDate_of_arrival(LocalDate.of(2023,06,10));
        shippingRoute1.setPurpose_of_travel("visit");
        shippingRoute1.setTax_fees_port_expenses(0.0);
        // ShippingRoute shippingRoute4 = new ShippingRoute();
        shippingRoute2.setId(2);
        shippingRoute2.setPort("Update");
        shippingRoute2.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute2.setPurpose_of_travel("visit");
        shippingRoute2.setTax_fees_port_expenses(0.0);
        ShippingRoute shippingRoute5 = new ShippingRoute();
        shippingRoute5.setId(3);
        shippingRoute5.setPort("Update");
        shippingRoute5.setDate_of_arrival(LocalDate.of(2023,06,11));
        shippingRoute5.setPurpose_of_travel("visit");
        shippingRoute5.setTax_fees_port_expenses(0.0);
        List<ShippingRoute> shippingRoutes2 = new ArrayList<>();
        shippingRoutes2.add(shippingRoute1);
        shippingRoutes2.add(shippingRoute2);
        shippingRoutes2.add(shippingRoute5);

        Vessel vessel2 = new Vessel((long)1, "Update", "Large-class", shippingRoutes2);

        booking.setBookingDateTime(bookingDateTime2);
        booking.setBerth(berth2);
        booking.setVessel(vessel2);
        booking.setActivity("Update");
        booking.setRemarks("Update");
        System.out.println(booking.getVessel());

        bookingService.updateBooking((long)1,booking);
        verify(bookingRepository, times(1)).save(booking);
        assertEquals(booking.getVessel().getShippingRoutes().size(), 3);
        assertEquals(booking.getVessel().getShippingRoutes().get(0).getPort(), "test");
        assertEquals(booking.getBookingDateTime().getBooktime(), "14:00"); // change to 13:00 for success
        assertEquals(booking.getBerth().getId(), berth2.getId());
        assertEquals(booking.getActivity(), "Update");
        assertEquals(booking.getId(), 1);
    }
}
        

        
