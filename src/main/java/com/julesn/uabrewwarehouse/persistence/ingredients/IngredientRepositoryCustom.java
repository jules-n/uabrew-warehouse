package com.julesn.uabrewwarehouse.persistence.ingredients;

import com.julesn.uabrewwarehouse.domain.Ingredient;

import java.util.Map;

public interface IngredientRepositoryCustom {

    Ingredient getIngredientByCriteria(Map<String, Object> map);

    boolean deleteIngredientByCriteria(Map<String, Object> map);

    boolean updateIngredientByName(Ingredient ingredient, String name);
}
