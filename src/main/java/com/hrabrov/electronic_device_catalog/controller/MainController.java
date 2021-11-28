package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Product;
import com.hrabrov.electronic_device_catalog.domain.Role;
import com.hrabrov.electronic_device_catalog.domain.User;
import com.hrabrov.electronic_device_catalog.repositories.ProductsRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    private final ProductsRepository productsRepository;

    public MainController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter,
            Map<String, Object> model) {

        Iterable<Product> allProducts = productsRepository.findAll();

        if (filter == null || filter.isEmpty()) {
            model.put("products", allProducts);
        } else {
            List<Product> findProducts = productsRepository.findByCategory(filter);

            if (findProducts.isEmpty()) {
                model.put("productDidntFound", true);
            }

            model.put("products", findProducts);
        }

        if (user.getRoles().contains(Role.ADMIN)) {
            model.put("editProducts", true);
            model.put("editUsers", true);
        }

        model.put("username", user.getUsername());
        model.put("roles", user.getRolesAsString());

        return "main";
    }

    @GetMapping("/compare")
    public String compareProducts(@RequestParam Map<String, String> idOfCompareProducts,
                                  Map<String, Object> model) {

        Set<Product> compareProducts = new LinkedHashSet<>();

        for(String id: idOfCompareProducts.keySet()) {
            compareProducts.add(productsRepository.findById(Integer.valueOf(id))) ;
        }

        model.put("compareProducts", compareProducts);

        return "compareProducts";
    }
}
