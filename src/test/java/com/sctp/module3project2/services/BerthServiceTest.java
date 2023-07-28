package com.sctp.module3project2.services;

import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.repository.BerthRepository;
import com.sctp.module3project2.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BerthServiceTest {

    @Mock
    private BerthRepository berthRepository;
    @Mock
    private BookingRepository bookingRepository;

    private BerthService berthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        berthService = new BerthServiceImpl(berthRepository, bookingRepository);
    }

    @Test
    void getAllBerths() {
        // Arrange
        List<Berth> expectedBerths = new ArrayList<>();
        when(berthRepository.findAll()).thenReturn(expectedBerths);

        // Act
        List<Berth> result = berthService.getAllBerths();

        // Assert
        assertEquals(expectedBerths, result);
        verify(berthRepository, times(1)).findAll();
        verifyNoMoreInteractions(berthRepository);
    }

    @Test
    void getBerthById() {
        // Arrange
        Long berthId = 1L;
        Berth expectedBerth = new Berth(berthId, "Test Berth", "Test Location", true);
        when(berthRepository.findById(berthId)).thenReturn(Optional.of(expectedBerth));

        // Act
        Berth result = berthService.getBerthById(berthId);

        // Assert
        assertEquals(expectedBerth, result);
        verify(berthRepository, times(1)).findById(berthId);
        verifyNoMoreInteractions(berthRepository);
    }

    @Test
    void createBerth() {
        // Arrange
        Berth berth = new Berth(null, "New Berth", "New Location", true);
        Berth savedBerth = new Berth(1L, "New Berth", "New Location", true);
        when(berthRepository.save(any(Berth.class))).thenReturn(savedBerth);

        // Act
        Berth result = berthService.createBerth(berth);

        // Assert
        assertEquals(savedBerth, result);
        verify(berthRepository, times(3)).save(any(Berth.class));
        verifyNoMoreInteractions(berthRepository);
    }

    @Test
    void saveBerth() {
        // Arrange
        Berth berth = new Berth(null, "New Berth", "New Location", true);
        Berth savedBerth = new Berth(1L, "New Berth", "New Location", true);
        when(berthRepository.save(berth)).thenReturn(savedBerth);

        // Act
        Berth result = berthService.saveBerth(berth);

        // Assert
        assertEquals(savedBerth, result);
        verify(berthRepository, times(1)).save(berth);
        verifyNoMoreInteractions(berthRepository);
    }

    @Test
    void updateBerth() {
        // Arrange
        Long berthId = 1L;
        Berth existingBerth = new Berth(berthId, "Old Berth", "Old Location", false);
        Berth updatedBerth = new Berth(berthId, "Updated Berth", "Updated Location", true);
        when(berthRepository.findById(berthId)).thenReturn(Optional.of(existingBerth));
        when(berthRepository.save(existingBerth)).thenReturn(updatedBerth);

        // Act
        Berth result = berthService.updateBerth(berthId, updatedBerth);

        // Assert
        assertEquals(updatedBerth, result);
        verify(berthRepository, times(1)).findById(berthId);
        verify(berthRepository, times(1)).save(existingBerth);
        verifyNoMoreInteractions(berthRepository);
    }

    // @Test
    // void deleteBerth() {
    //     // Arrange
    //     Long berthId = 1L;
    //     Berth existingBerth = new Berth(berthId, "Test Berth", "Test Location", true);
    //     when(berthRepository.findById(berthId)).thenReturn(Optional.of(existingBerth));

    //     // Act
    //     berthService.deleteBerth(berthId);

    //     // Assert
    //     verify(berthRepository, times(1)).findById(berthId);
    //     verify(berthRepository, times(1)).deleteById(berthId);
    //     verifyNoMoreInteractions(berthRepository);
    // }
}