package com.proje.salad_App.payload.request;

import com.proje.salad_App.entity.concretes.Admin;
import com.proje.salad_App.entity.concretes.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaladRequest {
    @NotBlank(message = "Please enter name")
    private String name;

    @NotNull(message = "Please provide ingredientIds")
    private List<Long> ingredientIds;
//
//    private User user;
//
//    private Admin admin;

}
