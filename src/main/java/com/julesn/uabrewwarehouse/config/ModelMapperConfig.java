package com.julesn.uabrewwarehouse.config;

import com.julesn.uabrewwarehouse.domain.Ingredient;
import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.dtos.MenuIngredient;
import com.julesn.uabrewwarehouse.dtos.MenuPosition;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(positionToMenuPositionConverter);
        modelMapper.addConverter(positionMenuToPositionConverter);
        modelMapper.addConverter(ingredientMenuToIngredientConverter);
        modelMapper.addConverter(ingredientToMenuIngredientConverter);
        return modelMapper;
    }

    private Converter<Position, MenuPosition> positionToMenuPositionConverter = new AbstractConverter<Position, MenuPosition>() {
        protected MenuPosition convert(Position model) {
            MenuPosition position = MenuPosition.builder()
                    .name(model.getName())
                    .characteristics(model.getCharacteristics())
                    .components(model.getComponents())
                    .amount(model.getAmount())
                    .price(model.getPrice())
                    .build();
            return position;
        }
    };

    private Converter<MenuPosition, Position> positionMenuToPositionConverter = new AbstractConverter<MenuPosition, Position>() {
        protected Position convert(MenuPosition dto) {
            Position position = Position.builder()
                    .characteristics(dto.getCharacteristics())
                    .components(dto.getComponents())
                    .price(dto.getPrice())
                    .build();
            position.setName(dto.getName());
            position.setAmount(dto.getAmount());
            return position;
        }
    };

    private Converter<MenuIngredient, Ingredient> ingredientMenuToIngredientConverter = new AbstractConverter<MenuIngredient, Ingredient>() {
        protected Ingredient convert(MenuIngredient dto) {
            Ingredient model = new Ingredient();
            model.setTotalAmount(dto.getTotalAmount());
            model.setName(dto.getName());
            return model;
        }
    };

    private Converter<Ingredient, MenuIngredient> ingredientToMenuIngredientConverter = new AbstractConverter<Ingredient, MenuIngredient>() {
        protected MenuIngredient convert(Ingredient model) {
            MenuIngredient dto = MenuIngredient.builder()
                    .totalAmount(model.getTotalAmount())
                    .name(model.getName())
                    .build();
            return dto;
        }
    };
}
