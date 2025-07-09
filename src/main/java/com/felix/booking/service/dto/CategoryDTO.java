package com.felix.booking.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private String image;
}
