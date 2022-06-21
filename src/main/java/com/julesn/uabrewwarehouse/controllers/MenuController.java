package com.julesn.uabrewwarehouse.controllers;

import com.julesn.uabrewwarehouse.dtos.Menu;
import com.julesn.uabrewwarehouse.services.menu.MenuService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Setter(onMethod_ = {@Autowired})
    private MenuService menuService;

    @GetMapping("/{bar}/get")
    public ResponseEntity<Menu> getMenu(@PathVariable String bar) {
        var menu = menuService.getMenu(bar);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
