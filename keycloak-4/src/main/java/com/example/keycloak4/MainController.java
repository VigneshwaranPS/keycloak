package com.example.keycloak4;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@SecurityRequirement(name = "Keycloak")
public class MainController {


    @PostMapping("/create")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant){
        return restaurant;
    }

    @PutMapping("/create")
    public Restaurant updateRestaurant(@RequestBody Restaurant restaurant){
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setLocation("mumbai");
        restaurant1.setName("m2m");

        restaurant1.setName(restaurant.getName());

        return restaurant1;
    }


    @PostMapping("/menuCreate")
    public Menu createMenu(@RequestBody Menu menu){
        return menu;
    }

    @GetMapping("/cash")
    public String getTotalAmount(){
        return "50000";
    }

    @GetMapping("/public/list")
    public List<Restaurant> getAllRestaurant(){
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("A2B Veg");
        restaurant1.setName("madurai");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("Bismi");
        restaurant2.setLocation("kerla");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        return restaurants;
    }



    @GetMapping("/public/menu")
    public List<Menu> getMenu(){
        List<Menu> menus = new ArrayList<>();

        Menu menu1 = new Menu();
        menu1.setName("pizza");
        menu1.setPrice(100);

        Menu menu2 = new Menu();
        menu2.setName("pizza");
        menu2.setPrice(100);

        menus.add(menu1);
        menus.add(menu2);

        return menus;
    }
}
