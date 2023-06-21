package com.sctp.module3project2.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.entity.Booking;
import com.sctp.module3project2.entity.BookingDateTime;
import com.sctp.module3project2.entity.Vessel;
import com.sctp.module3project2.exception.BookingNotFoundException;
import com.sctp.module3project2.repository.BookingRepository;
import com.sctp.module3project2.entity.ShippingRoute;
import java.util.List;

// Joel

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private VesselService vesselService;

    public BookingServiceImpl(BookingRepository bookingRepository, VesselService vesselService) {
        this.bookingRepository = bookingRepository;
        this.vesselService = vesselService;
    }
    
    @Override
    public Booking saveBooking(Booking booking) {
        Booking newBooking = bookingRepository.save(booking);
        return newBooking;
    }

    @Override
    public Booking getBooking(Long id) {
        Optional<Booking> foundBooking = bookingRepository.findById(id);
        if (!foundBooking.isPresent()){
            throw new BookingNotFoundException("Booking not found");
        }
        return foundBooking.get();
        
    }

    @Override
    public ArrayList<Booking> getAllBookings() {
        ArrayList<Booking> allBookings = (ArrayList<Booking>) bookingRepository.findAll();
        return allBookings;
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Optional<Booking> foundBooking = bookingRepository.findById(id);
        if (!foundBooking.isPresent()){
            throw new BookingNotFoundException("Booking not found");
        }
        // fill in when other fields are added
        Booking bookingToUpdate = foundBooking.get();

        bookingToUpdate.setRemarks(booking.getRemarks());
        bookingToUpdate.setActivity(booking.getActivity());
        
      
        BookingDateTime datetimeInfo = booking.getBookingDateTime();

        BookingDateTime datetime = new BookingDateTime(); 
        datetime.setBookdate(datetimeInfo.getBookdate());
        datetime.setBooktime(datetimeInfo.getBooktime());
        // bookingToUpdate.setBookingDateTime(booking.getBookingDateTime());
        bookingToUpdate.getBookingDateTime().setBookdate(datetime.getBookdate());      
        bookingToUpdate.getBookingDateTime().setBooktime(datetime.getBooktime());

        Berth berthInfo = booking.getBerth();

        Berth berth = new Berth();
        berth.setName(berthInfo.getName());
        berth.setLocation(berthInfo.getLocation());
        berth.setAvailability(berthInfo.isAvailability());
        // bookingToUpdate.setBerth(booking.getBerth());

        bookingToUpdate.getBerth().setName(berth.getName());
        bookingToUpdate.getBerth().setLocation(berth.getLocation());
        bookingToUpdate.getBerth().setAvailability(berth.isAvailability());

        // Vessel vesselInfo = booking.getVessel();
        // Vessel vessel = new Vessel();
        // vessel.setName(vesselInfo.getName());
        // vessel.setType(vesselInfo.getType());
        // vessel.setShippingRoutes(vesselInfo.getShippingRoutes());
        // bookingToUpdate.getVessel().setName(vessel.getName());
        // bookingToUpdate.getVessel().setType(vessel.getType());
        // bookingToUpdate.getVessel().setShippingRoutes(vessel.getShippingRoutes());

        Vessel vesselInfo = booking.getVessel();
        Vessel vessel = new Vessel();
        vessel.setName(vesselInfo.getName());
        vessel.setType(vesselInfo.getType());
        vessel.setShippingRoutes(vesselInfo.getShippingRoutes());
        bookingToUpdate.getVessel().setName(vessel.getName());
        bookingToUpdate.getVessel().setType(vessel.getType());
        // ArrayList<ShippingRoute> shippingRouteInfo = (ArrayList<ShippingRoute>) vesselInfo.getShippingRoutes();
        // ArrayList<ShippingRoute> shippingRoute = new ArrayList<ShippingRoute>();
        // for (ShippingRoute route : shippingRouteInfo) {
        //     ShippingRoute newRoute = new ShippingRoute();
        //         newRoute.setDate_of_arrival(route.getDate_of_arrival());
        //         newRoute.setPort(route.getPort());
        //         newRoute.setPurpose_of_travel(route.getPurpose_of_travel());
        //         newRoute.setTax_fees_port_expenses(route.getTax_fees_port_expenses());
        //         shippingRoute.add(newRoute);
        // };
        // bookingToUpdate.getVessel().setShippingRoutes(vessel.getShippingRoutes());
        List<ShippingRoute> shippingRouteInfo = vesselInfo.getShippingRoutes();

        for (int i = 0; i < shippingRouteInfo.size(); i++) {
            ShippingRoute newRoute = new ShippingRoute();
            newRoute.setDate_of_arrival(shippingRouteInfo.get(i).getDate_of_arrival());
            newRoute.setPort(shippingRouteInfo.get(i).getPort());
            newRoute.setPurpose_of_travel(shippingRouteInfo.get(i).getPurpose_of_travel());
            newRoute.setTax_fees_port_expenses(shippingRouteInfo.get(i).getTax_fees_port_expenses());
            newRoute.setId(bookingToUpdate.getVessel().getShippingRoutes().get(i).getId());
            // newRoute.setVessel(shippingRouteInfo.get(i).getVessel());
            newRoute.getVessel().setId(bookingToUpdate.getVessel().getId());
            System.out.println(bookingToUpdate.getVessel().getShippingRoutes().get(i).getVessel());
            bookingToUpdate.getVessel().getShippingRoutes().set(i, newRoute);
        };

       
         

    
        return bookingRepository.save(bookingToUpdate);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }


 
}
