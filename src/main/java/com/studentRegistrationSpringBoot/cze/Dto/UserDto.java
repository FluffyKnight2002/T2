package com.studentRegistrationSpringBoot.cze.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor

public class UserDto {

        private String username, email, password;

}
