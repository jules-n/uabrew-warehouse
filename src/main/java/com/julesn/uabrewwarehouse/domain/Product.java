package com.julesn.uabrewwarehouse.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String _id;
    private String name;
    private String bar;
    private Integer totalAmount;
}
