package com.felix.booking.service.service.impl;

import com.felix.booking.service.dto.BookingRequest;
import com.felix.booking.service.dto.SalonDTO;
import com.felix.booking.service.dto.ServiceDTO;
import com.felix.booking.service.dto.UserDTO;
import com.felix.booking.service.model.Booking;
import com.felix.booking.service.model.SalonReport;
import com.felix.booking.service.repository.BookingRepository;
import com.felix.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Internal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, SalonDTO salon, UserDTO user, Set<ServiceDTO> serviceDTOset) {
        int totalDuration = serviceDTOset.stream().mapToInt(ServiceDTO-> Integer.parseInt(ServiceDTO.getDuration())).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);
        return null;
    }

    @Override
    public Booking getBookingById(Long id) {
        return null;
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return List.of();
    }

    @Override
    public List<Booking> getBookingByCustomerId(Long customerId) {
        return List.of();
    }

    @Override
    public List<Booking> getBookingByServiceId(Long serviceId) {
        return List.of();
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return null;
    }

    @Override
    public void deleteBooking(Long id) {

    }

    @Override
    public List<Booking> getAllBookings() {
        return List.of();
    }

    @Override
    public List<Booking> getBokingByDate(LocalDate date, Long salonId) {
        return List.of();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        return null;
    }
}
