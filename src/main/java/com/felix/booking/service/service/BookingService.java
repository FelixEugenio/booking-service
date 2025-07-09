package com.felix.booking.service.service;

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
    Booking createBooking(BookingRequest booking, SalonDTO salon , UserDTO user, Set<ServiceDTO> serviceDTOset);
    Booking getBookingById(Long id);
    List<Booking> getBookingBySalonId(Long salonId);
    List<Booking> getBookingByCustomerId(Long customerId);
    List<Booking> getBookingByServiceId(Long serviceId);
    Booking updateBooking(Booking booking);
    void deleteBooking(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBokingByDate(LocalDate date, Long salonId);
    SalonReport getSalonReport(Long salonId);

}
