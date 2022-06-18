package com.julesn.uabrewwarehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuIngredient {
    private String name;
    private Integer totalAmount;
}
