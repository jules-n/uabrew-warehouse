package com.julesn.uabrewwarehouse.services.menu;

import com.google.common.annotations.VisibleForTesting;
import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.dtos.Menu;
import com.julesn.uabrewwarehouse.dtos.MenuPosition;
import com.julesn.uabrewwarehouse.services.PositionService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class MenuServiceImpl implements MenuService {

    @Setter(onMethod_ = {@Autowired})
    private PositionService positionService;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @Override
    public Optional<Menu> getMenu(String barName) {
        List<Position> positions = positionService.getPositionsByBar(barName);

        if (positions.isEmpty()) return Optional.empty();

        var menuPositions = positions.stream().map(positionService::checkAmount).filter(Objects::nonNull)
                .map(position -> modelMapper.map(position, MenuPosition.class))
                .collect(Collectors.toList());
        return positions.isEmpty() ?
                Optional.empty() : Optional.of(new Menu(barName, menuPositions));
    }

}
