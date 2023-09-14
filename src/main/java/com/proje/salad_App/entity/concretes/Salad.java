package com.proje.salad_App.entity.concretes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="salads")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Salad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(
            name = "salad_ingredients",
            joinColumns = @JoinColumn(name = "salad_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
        //    inverseJoinColumns = @JoinColumn(name = "quantity"),
    )
    private List<Ingredient> ingredients;

    @Column(nullable = false)
    private double price; // Yeni alan: salad fiyatını tutmak için


}