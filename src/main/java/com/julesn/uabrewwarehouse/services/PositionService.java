package com.julesn.uabrewwarehouse.services;

import com.julesn.uabrewwarehouse.domain.Position;
import com.julesn.uabrewwarehouse.domain.Product;

import java.util.List;

public interface PositionService {

    boolean save(Position position);

    boolean update(Position position, String name);

    boolean delete(String bar, String name);

    Position checkAmount(Position position);

    Position checkAmount(Position position, int number);

    List<Position> getPositionsByBar(String barName);

    Position getPositionByName(String bar, String name);

    void findAndChangeAmountOfSubComponents(String bar, String name, int number);
}
