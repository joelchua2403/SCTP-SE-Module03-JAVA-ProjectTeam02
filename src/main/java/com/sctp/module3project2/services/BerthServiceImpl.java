package com.sctp.module3project2.services;

import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.entity.Booking;
import com.sctp.module3project2.repository.BerthRepository;
import com.sctp.module3project2.repository.BookingRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
// Responsible for business logics.
// Commonly skipped if business logics are too retrieval

public class BerthServiceImpl implements BerthService {

    private final BerthRepository berthRepository;
    private final BookingRepository bookingRepository;

    public BerthServiceImpl(BerthRepository berthRepository, BookingRepository bookingRepository) {
        this.berthRepository = berthRepository;
        this.bookingRepository = bookingRepository;
    }

    // Added by Farhan
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Berth createBerth(Berth berth) {
        // Reset ID to a new lower digit ID
        berth.setId(null);
        Berth createdBerth = berthRepository.save(berth);

        // Update the ID to a lower number
        Long newId = 1L;
        createdBerth.setId(newId);
        berthRepository.save(createdBerth);
        return berthRepository.save(berth);
    }

    @Override
    public List<Berth> getAllBerths() {
        return berthRepository.findAll();
    }

    @Override
    public Berth getBerthById(Long id) {
        Optional<Berth> optionalBerth = berthRepository.findById(id);
        return optionalBerth.orElse(null);
    }

    @Override
    public Berth saveBerth(Berth berth) {
        // Reset ID to a new lower digit ID
        berth.setId(null);
        return berthRepository.save(berth);
    }

    @Override
    public Berth updateBerth(Long id, Berth berth) {
        Optional<Berth> optionalBerth = berthRepository.findById(id);
        if (optionalBerth.isPresent()) {
            Berth existingBerth = optionalBerth.get();
            existingBerth.setName(berth.getName());
            existingBerth.setLocation(berth.getLocation());
            existingBerth.setAvailability(berth.isAvailability());

            // <---- Added by Farhan - Associated with 'Booking' ---->
            // Booking booking = existingBerth.getBooking();
            // if (booking != null) {
            // Berth berthInfo = booking.getBerth();
            // berthInfo.setName(existingBerth.getName());
            // berthInfo.setLocation(existingBerth.getLocation());
            // berthInfo.setAvailability(existingBerth.isAvailability());
            // }
            // <---- End of edit by Farhan ---->

            return berthRepository.save(existingBerth);
        }
        return null;
    }

    // @Override
    // public void deleteBerth(Long id) {
    //     Optional<Berth> optionalBerth = berthRepository.findById(id);
    //     if (optionalBerth.isPresent()) {
    //         Berth berth = optionalBerth.get();
    //         Booking booking = berth.getBooking();
    //         if (booking != null) {
    //             berth.setBooking(null); // Dissociate the booking from the berth
    //             berthRepository.save(berth);
    //             bookingRepository.delete(booking);
    //         }
    //         berthRepository.deleteById(id);
    //     }
    // }

}
