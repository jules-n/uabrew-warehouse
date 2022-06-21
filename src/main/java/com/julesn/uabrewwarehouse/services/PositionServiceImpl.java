package com.julesn.uabrewwarehouse.services;

import com.julesn.uabrewwarehouse.domain.Component;
import com.julesn.uabrewwarehouse.domain.Ingredient;
import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.domain.Product;
import com.julesn.uabrewwarehouse.persistence.ingredients.IngredientRepository;
import com.julesn.uabrewwarehouse.persistence.positions.PositionRepository;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@Log4j2
public class PositionServiceImpl implements PositionService {

    @Setter(onMethod_ = {@Autowired})
    private PositionRepository positionRepository;

    @Setter(onMethod_ = {@Autowired})
    private IngredientRepository ingredientRepository;

    @Override
    public boolean save(Position position) {
        if (position.getComponents() !=null && !position.getComponents().isEmpty()) {
            checkOnCycleDependency(position);
            checkOnComponentsExisting(position);
        }
        try {
            positionRepository.save(position);
            return true;
        } catch (Exception e) {
            log.warn(e);
            return false;
        }
    }

    @SneakyThrows
    private boolean checkOnCycleDependency(Position position) {
        var isCyclic = position.getComponents().stream().anyMatch(
                component -> component.getName().equals(position.getName())
        );
        if(isCyclic) throw new Exception("There is a cycle dependency in position data");
        return false;
    }

    @SneakyThrows
    private boolean checkOnComponentsExisting(Position position) {
        var isAllComponentsExists = position.getComponents().stream()
                .allMatch(component -> {
                    var map = new HashMap<String, Object>();

                    map.put("bar", position.getBar());
                    map.put("name", component.getName());
                    var nestedPosition = positionRepository.getPositionByCriteria(map);
                    var ingredient = ingredientRepository.getIngredientByCriteria(map);
                    return nestedPosition != null || ingredient != null;
                });
        if (!isAllComponentsExists) throw new Exception("No such component");
        return true;
    }

    @Override
    public boolean update(Position position, String name) {
        if (position.getComponents() !=null && !position.getComponents().isEmpty()) {
            checkOnCycleDependency(position);
            checkOnComponentsExisting(position);
        }
        return positionRepository.update(position, name);
    }

    @Override
    public boolean delete(String bar, String name) {
        var map = new HashMap<String, Object>();

        map.put("bar", bar);
        map.put("name", name);
        if (!positionRepository.findPositionInComponents(bar, name)) {
            return positionRepository.deleteByCriteria(map);
        }
        throw new RuntimeException("This position is being used");

    }

    public Position checkAmount(Position position, int number) {
        boolean isContainsComponents = position.getComponents() == null || position.getComponents().isEmpty();
        boolean isEnough = position.getAmount()*number <= position.getTotalAmount();
        if (!isContainsComponents && isEnough) {
            return position;
        }

        if(checkAmount(position.getBar(), position.getComponents(), number)) {
            return position;
        }

        return null;
    }

    public Position checkAmount(Position position) {
        boolean isContainsComponents = position.getComponents() == null || position.getComponents().isEmpty();
        boolean isEnough = position.getAmount() <= position.getTotalAmount();
        if (!isContainsComponents && isEnough) {
            return position;
        }

        if(checkAmount(position.getBar(), position.getComponents(), 1)) {
            return position;
        }

        return null;
    }

    private boolean checkAmount(String bar, List<Component> components, int number) {
        return components.stream().allMatch(
                component -> {
                    var map = new HashMap<String, Object>();

                    map.put("bar", bar);
                    map.put("name", component.getName());
                    var position = positionRepository.getPositionByCriteria(map);
                    if (position != null) {
                        if (checkAmount(position, number) != null) return true;
                    }
                    var ingredient = ingredientRepository.getIngredientByCriteria(map);
                    if (ingredient != null) {
                        return ingredient.getTotalAmount() >= component.getAmount()*number;
                    }
                    return false;
                }
        );
    }

    @Override
    public List<Position> getPositionsByBar(String barName) {
        return positionRepository.getPositionsByBar(
                barName
        );
    }

    @Override
    public Position getPositionByName(String bar, String name) {
        var map = new HashMap<String, Object>();

        map.put("bar", bar);
        map.put("name", name);
        var position = positionRepository.getPositionByCriteria(map);
        return position;
    }

    @Override
    @SneakyThrows
    public void findAndChangeAmountOfSubComponents(String bar, String name, int number) {
        var position = getPositionByName(bar, name);
        if (position != null) {
            if (position.getComponents() != null && !position.getComponents().isEmpty()) {
                position.getComponents().stream()
                        .forEach( component -> traverseAndUpdateAmountOfSubComponents(bar, component.getName(), component.getAmount(), number));
            }
            else {
                positionRepository.update((Position) changeTotalAmount(position, position.getAmount(), number), name);
            }
        }
    }

    private Product changeTotalAmount(Product product, int amount, int number){
        product.setTotalAmount(product.getTotalAmount() - amount*number);
        return product;
    }

    private void traverseAndUpdateAmountOfSubComponents(String bar, String name, int amount, int number) {
        var position = getPositionByName(bar, name);
        if (position != null) {
            if (position.getComponents() != null && !position.getComponents().isEmpty()) {
                position.getComponents().stream()
                        .forEach( component -> traverseAndUpdateAmountOfSubComponents(bar, component.getName(), component.getAmount(), number));
            } else {
                positionRepository.update((Position) changeTotalAmount(position, position.getAmount(), number), name);
            }
        } else {
            var map = new HashMap<String, Object>();
            map.put("bar", bar);
            map.put("name", name);
            var ingredient = ingredientRepository.getIngredientByCriteria(map);
            ingredientRepository
                    .updateIngredientByName((Ingredient) changeTotalAmount(ingredient, amount, number), name);
        }
    }
}
