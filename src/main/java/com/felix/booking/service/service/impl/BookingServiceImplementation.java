package com.felix.booking.service.service.impl;

import com.felix.booking.service.domain.BookingStatus;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking, SalonDTO salon, UserDTO user, Set<ServiceDTO> serviceDTOset) throws Exception {
        int totalDuration = serviceDTOset.stream().mapToInt(ServiceDTO-> Integer.parseInt(ServiceDTO.getDuration())).sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isTimeSloatAvailable = isTimeSloatAvailable(salon,bookingStartTime,bookingEndTime);

        int totalPrice = serviceDTOset.stream().mapToInt(ServiceDTO::getPrice).sum();

        Set<Long> idList = serviceDTOset.stream().map(ServiceDTO::getId).collect(Collectors.toSet());

        Booking newBooking = new Booking();

        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public boolean isTimeSloatAvailable(SalonDTO salon, LocalDateTime startTime, LocalDateTime endTime) throws Exception {

        LocalDateTime salonOpenTime = salon.getOpenTime().atDate(startTime.toLocalDate());
        LocalDateTime salonCloseTime = salon.getCloseTime().atDate(startTime.toLocalDate());

        List<Booking> existingBookings = bookingRepository.findBySalonId(salon.getId());



        if(startTime.isBefore(salonOpenTime) || endTime.isAfter(salonCloseTime)){
            throw  new Exception("Booking time must be within salons working hours");
        }

        for(Booking existingBooking : existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getStartTime();
            LocalDateTime existingBookingCloseTime = existingBooking.getEndTime();

            if(startTime.isBefore(existingBookingCloseTime) && endTime.isAfter(existingBookingStartTime)){
                throw  new Exception("Slot not available , choose diferente time ");
            }

            if(startTime.isEqual(existingBookingStartTime) || endTime.isEqual(existingBookingCloseTime)){
                throw  new Exception("Slot not available , choose diferente time ");
            }
        }

        return true;
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new Exception("Booking not found");
        }
        return booking;
    }

    @Override
    public List<Booking> getBookingBySalonId(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public List<Booking> getBookingByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingByServiceId(Long serviceId) {
        return List.of();
    }

    @Override
    public Booking updateBooking(Long bookingId  ,BookingStatus bookingStatus) throws Exception {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(bookingStatus);

        return bookingRepository.save(booking);
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
        List<Booking> allBookings = bookingRepository.findBySalonId(salonId);
        if(date == null){
            return allBookings;
        }

        allBookings.stream().filter(booking -> isSameDate(booking.getStartTime(),date) || isSameDate(booking.getEndTime(),date)).collect(Collectors.toList());

        return allBookings;
    }

    Boolean isSameDate(LocalDateTime startTime, LocalDate date){
        return startTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking> bookings = getBookingBySalonId(salonId);

        Double totalEarnings = bookings.stream().mapToDouble(booking -> booking.getTotalPrice()).sum();

            Integer totalBookings = bookings.size();

            List<Booking> cancelledBookings = bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED)).collect(Collectors.toList());

            Double totalRefund = (double)cancelledBookings.stream().mapToInt(booking -> booking.getTotalPrice()).sum();

            SalonReport report = new SalonReport();
            report.setSalonId(salonId);
            report.setCancelledBookings(cancelledBookings.size());
            report.setTotalBookings(totalBookings);
            report.setTotalEarnings(report.getTotalEarnings());
            report.setTotalRefund(totalRefund.intValue());
            report.setTotalBookings(totalEarnings.intValue());

            return report;
    }
}
