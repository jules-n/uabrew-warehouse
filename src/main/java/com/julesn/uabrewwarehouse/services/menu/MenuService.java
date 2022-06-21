package com.julesn.uabrewwarehouse.services.menu;

import com.julesn.uabrewwarehouse.dtos.Menu;

import java.util.Optional;

public interface MenuService {

    Optional<Menu> getMenu(String barName);

}
