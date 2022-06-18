package com.julesn.uabrewwarehouse.controllers;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import com.julesn.uabrewwarehouse.dtos.MenuIngredient;
import com.julesn.uabrewwarehouse.services.IngredientService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Setter(onMethod_ = {@Autowired})
    private IngredientService ingredientService;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @PostMapping("{bar}")
    ResponseEntity addIngredient(@PathVariable("bar") String bar, @RequestBody MenuIngredient menuIngredient) {
        var ingredient = modelMapper.map(menuIngredient, Ingredient.class);
        ingredient.setBar(bar);
        return ingredientService.save(ingredient) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{bar}/{name}")
    ResponseEntity deleteIngredient(@PathVariable("bar") String bar, @PathVariable("name") String name) {
        return ingredientService.delete(bar, name) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("{bar}/{name}")
    ResponseEntity updateIngredient(@PathVariable("bar") String bar, @PathVariable("name") String name,
                                    @RequestBody MenuIngredient menuIngredient) {
        var ingredient = modelMapper.map(menuIngredient, Ingredient.class);
        ingredient.setBar(bar);
        return ingredientService.update(ingredient, name) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
