package com.proje.salad_App.entity.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proje.salad_App.entity.concretes.UserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass     // DB de user tablosu olusmadan bu sinifin anac sinif olarak kullanilmasini sagliyor
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract  class BaseUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;


    private String firstName;
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // hassas veri oldugu icin okuma islemlerinde kullanilmasin
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;


}
