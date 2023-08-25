package com.proje.salad_App.payload.response.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
// @MappedSuper
public abstract class BaseUserResponse {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
