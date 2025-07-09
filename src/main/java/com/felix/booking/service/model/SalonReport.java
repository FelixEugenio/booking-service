package com.felix.booking.service.model;

import lombok.Data;

@Data
public class SalonReport {
    private Long id;
    String SalonName;
    public Double totalEarnings;
    public Integer totalBookings;
    public Integer cancelledBookings;
    public Integer totalRefund;
}
