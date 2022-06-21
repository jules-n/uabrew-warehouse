package com.julesn.uabrewwarehouse.services;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import com.julesn.uabrewwarehouse.persistence.ingredients.IngredientRepository;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Log4j2
public class IngredientServiceImpl implements IngredientService {

    @Setter(onMethod_ = {@Autowired})
    private IngredientRepository ingredientRepository;

    @Override
    public boolean save(Ingredient ingredient) {
        try {
            ingredientRepository.save(ingredient);
        } catch (Exception ex) {
            log.warn(ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Ingredient ingredient, String name) {
        return false;
    }

    @Override
    public boolean delete(String bar, String name) {
        return false;
    }
}
