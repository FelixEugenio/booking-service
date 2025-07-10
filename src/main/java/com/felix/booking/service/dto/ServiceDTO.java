package com.felix.booking.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ServiceDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private String duration;
    private Long salonId;
    private Long category;
    private String image;
}
