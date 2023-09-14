package com.proje.salad_App.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Please enter user ID")
    private Long userId;

    @NotNull(message = "Please select at least one salad")
    private List<Long> saladIds;

}
