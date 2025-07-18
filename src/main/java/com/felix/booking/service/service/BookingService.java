package com.felix.booking.service.service;

import com.felix.booking.service.domain.BookingStatus;
import com.felix.booking.service.dto.BookingRequest;
import com.felix.booking.service.dto.SalonDTO;
import com.felix.booking.service.dto.ServiceDTO;
import com.felix.booking.service.dto.UserDTO;
import com.felix.booking.service.model.Booking;
import com.felix.booking.service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {
    Booking createBooking(BookingRequest booking, SalonDTO salon , UserDTO user, Set<ServiceDTO> serviceDTOset) throws Exception;
    Booking getBookingById(Long id) throws Exception;
    List<Booking> getBookingBySalonId(Long salonId);
    List<Booking> getBookingByCustomerId(Long customerId);
    Booking updateBooking(Long bookingId, BookingStatus bookingStatus) throws Exception;
    List<Booking> getBookingByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);

}
