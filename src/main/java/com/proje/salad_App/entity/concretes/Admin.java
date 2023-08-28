package com.proje.salad_App.entity.concretes;

import com.proje.salad_App.entity.abstracts.BaseUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends BaseUser {
    private boolean build_in;
    @ManyToOne // Çok adminin bir rolü olabilir
    @JoinColumn(name = "user_role_id") // UserRole ile ilişkilendirme
    private UserRole userRole;
}
