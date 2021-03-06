package com.julesn.uabrewwarehouse.services;

import com.julesn.uabrewwarehouse.domain.Ingredient;

public interface IngredientService {

    boolean save(Ingredient ingredient);

    boolean update(Ingredient ingredient, String name);

    boolean delete(String bar, String name);
}
