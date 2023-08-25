package com.proje.salad_App.entity.concretes;

import com.proje.salad_App.entity.abstracts.BaseUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class User extends BaseUser {


    @Column
    private String address;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
    //private UserRole
}
