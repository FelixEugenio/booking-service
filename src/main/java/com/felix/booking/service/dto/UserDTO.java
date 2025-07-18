package com.felix.booking.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;

}
