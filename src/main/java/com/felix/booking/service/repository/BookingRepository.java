package com.felix.booking.service.repository;

import com.felix.booking.service.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findByCustomerId(Long customerId);
  List<Booking> findBySalonId(Long salonId);
  List<Booking> findByServiceId(Long serviceId);
}
