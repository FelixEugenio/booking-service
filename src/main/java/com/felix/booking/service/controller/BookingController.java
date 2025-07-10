package com.felix.booking.service.controller;

import com.felix.booking.service.domain.BookingStatus;
import com.felix.booking.service.dto.*;
import com.felix.booking.service.mapper.BookingMapper;
import com.felix.booking.service.model.Booking;
import com.felix.booking.service.model.SalonReport;
import com.felix.booking.service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam Long salonId, @RequestBody BookingRequest bookingRequest) throws Exception {

        UserDTO user = new UserDTO();
        user.setId(1L);

        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.now());
        salon.setCloseTime(LocalTime.now().plusHours(12));

        Set<ServiceDTO> serviceDTOset = new HashSet<>();

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setDuration(45);
        serviceDTO.setPrice(100);
        serviceDTO.setName("Hair  Cut for men");
        serviceDTOset.add(serviceDTO);

        Booking booking = bookingService.createBooking(bookingRequest,salon,user,serviceDTOset);

       return ResponseEntity.ok(booking);

    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingByCustomer(){

        List<Booking> bookings = bookingService.getBookingByCustomerId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream().map(booking -> {
            return BookingMapper.toDTO(booking);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingBySalon(){

        List<Booking> bookings = bookingService.getBookingBySalonId(1L);

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) throws Exception {

        Booking bookings = bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(BookingMapper.toDTO(bookings));
    }

    @PutMapping("/{bookingId}/ststus")
    public ResponseEntity<BookingDTO> updateSatus(@PathVariable Long bookingId, @RequestParam BookingStatus status) throws Exception {

        Booking bookings = bookingService.updateBooking(bookingId,status);

        return ResponseEntity.ok(BookingMapper.toDTO(bookings));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(@PathVariable Long salonId, @RequestParam LocalDate date) throws Exception {

        List<Booking> bookings = bookingService.getBookingByDate(date,salonId);

        List<BookingSlotDTO> slotDTOs = (List<BookingSlotDTO>) (List<BookingSlotDTO>) bookings.stream().map(booking ->
        {
            BookingSlotDTO bookingSlotDTO = new BookingSlotDTO();
            bookingSlotDTO.setStartTime(booking.getStartTime());
            bookingSlotDTO.setEndTime(booking.getEndTime());
            return bookingSlotDTO;
        }).collect(Collectors.toSet());

        return ResponseEntity.ok(slotDTOs);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(@PathVariable Long salonId,@RequestParam(required = false) LocalDate date) throws Exception {

        SalonReport salonReport = bookingService.getSalonReport(1L);

        return ResponseEntity.ok(salonReport);
    }



}
