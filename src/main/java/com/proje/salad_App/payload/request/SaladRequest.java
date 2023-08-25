package com.proje.salad_App.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaladRequest {
    @NotBlank(message = "Please enter name")
    private String name;

    @NotNull(message = "Please provide ingredientIds")
    private Set<Long> ingredientIds;
}
