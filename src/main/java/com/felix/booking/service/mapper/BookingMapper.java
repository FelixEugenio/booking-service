package com.felix.booking.service.mapper;

import com.felix.booking.service.dto.BookingDTO;
import com.felix.booking.service.model.Booking;

public class BookingMapper {
        public static BookingDTO toDTO(Booking booking){
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(booking.getId());
            bookingDTO.setCustomerId(booking.getCustomerId());
            bookingDTO.setSalonId(booking.getSalonId());
            bookingDTO.setStartTime(booking.getStartTime());
            bookingDTO.setEndTime(booking.getEndTime());
            bookingDTO.setStatus(booking.getStatus());
            bookingDTO.setTotalPrice(booking.getTotalPrice());
            return bookingDTO;
        }
    }
