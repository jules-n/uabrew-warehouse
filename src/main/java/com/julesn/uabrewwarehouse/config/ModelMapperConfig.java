package com.julesn.uabrewwarehouse.config;

import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.dtos.MenuPosition;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(positionToMenuPositionConverter);
        return modelMapper;
    }

    private Converter<Position, MenuPosition> positionToMenuPositionConverter = new AbstractConverter<>() {
        protected MenuPosition convert(Position model) {
            var position = MenuPosition.builder()
                    .name(model.getName())
                    .characteristics(model.getCharacteristics())
                    .components(model.getComponents())
                    .amount(model.getAmount())
                    .price(model.getPrice())
                    .build();
            return position;
        }
    };
}
