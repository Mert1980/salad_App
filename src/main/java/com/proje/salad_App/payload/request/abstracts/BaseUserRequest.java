package com.proje.salad_App.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@SuperBuilder
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserRequest implements Serializable {
    @NotNull(message = "Please enter your username")
    @Size(min = 4, max = 16, message = "Your username should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters .")
    private String username;

    @NotNull(message = "Please enter your name")
    @Size(min = 4, max = 16, message = "Your name should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters .")
    private String firstName;

    @NotNull(message = "Please enter your surname")
    @Size(min = 4, max = 16, message = "Your surname should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters .")
    private String lastName;

    @NotNull(message = "Please enter your birth place")
    @Size(min = 8, max = 60, message = "Please enter your password as al least 8 chars")
//  @Column(nullable = false,length = 60)   Gerek yok bu DTO degil hoca yanlis yazdi
    private String password;

    @NotNull(message = "Please enter your email")@NotNull(message = "Please enter your email")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "Please enter your phone number")
    @Size(min = 12, max = 12, message = "Phone number should be exact 12 chars")
    @Pattern(regexp = "^((\\(\\d{3}\\))/\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phoneNumber;


}
