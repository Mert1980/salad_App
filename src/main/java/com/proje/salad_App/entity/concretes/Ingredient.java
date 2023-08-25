package com.proje.salad_App.entity.concretes;

import com.proje.salad_App.entity.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientType ingredientType;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Salad> salads;

    @OneToMany(mappedBy = "ingredient")
    private Set<Stock> stocks;
}


