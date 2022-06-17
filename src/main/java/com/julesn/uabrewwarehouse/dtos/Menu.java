package com.julesn.uabrewwarehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    private String bar;
    private List<MenuPosition> positions;
}
