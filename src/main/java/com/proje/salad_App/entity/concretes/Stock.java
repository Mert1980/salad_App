package com.proje.salad_App.entity.concretes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String unit;

    public Stock(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Long getIngredientId() {
        if (ingredient != null) {
            return ingredient.getId();
        }
        return null;
    }
}
