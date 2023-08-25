package com.proje.salad_App.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long userId;
    private Set<Long> saladIds;
    private LocalDateTime orderTime;
    private String status;
}
