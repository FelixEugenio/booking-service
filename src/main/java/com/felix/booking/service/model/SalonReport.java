package com.felix.booking.service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SalonReport {

    String SalonName;
    private Long salonId;
    public Double totalEarnings;
    public Integer totalBookings;
    public Integer cancelledBookings;
    public Integer totalRefund;
}
