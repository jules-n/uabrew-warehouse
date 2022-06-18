package com.julesn.uabrewwarehouse.services;

import com.google.common.annotations.VisibleForTesting;
import com.julesn.uabrewwarehouse.domain.Component;
import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.domain.Product;
import com.julesn.uabrewwarehouse.dtos.Menu;
import com.julesn.uabrewwarehouse.dtos.MenuPosition;
import com.julesn.uabrewwarehouse.persistence.ingredients.IngredientRepository;
import com.julesn.uabrewwarehouse.persistence.positions.PositionRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class MenuServiceImpl implements MenuService{

    @Setter(onMethod_ = {@Autowired})
    @VisibleForTesting
    private PositionRepository positionRepository;

    @Setter(onMethod_ = {@Autowired})
    @VisibleForTesting
    private IngredientRepository ingredientRepository;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @Override
    public Optional<Menu> getMenu(String barName) {
        List<Position> positions = positionRepository.getPositionsByBar(barName);

        if (positions.isEmpty()) return Optional.empty();

        var menuPositions = positions.stream().map(this::checkAmount).filter(Objects::nonNull)
                .map(position -> modelMapper.map(position, MenuPosition.class))
                .collect(Collectors.toList());
        return positions.isEmpty() ?
                Optional.empty() : Optional.of(new Menu(barName, menuPositions));
    }

    private Position checkAmount(Position position) {
        boolean isContainsComponents = position.getComponents() == null || position.getComponents().isEmpty();
        boolean isEnough = position.getAmount() <= position.getTotalAmount();
        if (isContainsComponents && isEnough) {
            return position;
        }

        if(checkAmount(position.getComponents())) {
            return position;
        }

        return null;
    }

    private boolean checkAmount(List<Component> components) {
        return components.stream().allMatch(
            component -> {
                var position = positionRepository.getPositionByName(component.getName());
                if (position != null) {
                    if (checkAmount(position) != null) return true;
                }
                var ingredient = ingredientRepository.getIngredientByName(component.getName());
                if (ingredient != null) {
                    return ingredient.getTotalAmount() >= component.getAmount();
                }
                return false;
            }
        );
    }



}
