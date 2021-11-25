package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Product;
import com.hrabrov.electronic_device_catalog.repositories.ProductsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final ProductsRepository productsRepository;

    public MainController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);
        return "main";
    }

    @PostMapping("/main")
    public String addProducts(@RequestParam String productName, @RequestParam String category,
                              Map<String, Object> model) {
        Product newProduct = new Product(productName, category);
        productsRepository.save(newProduct);

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "main";
    }

    @PostMapping("/filter")
    public String searchProductsByCategory(@RequestParam String filter, Map<String, Object> model) {

        if (filter == null || filter.isEmpty()) {
            model.put("categoryNotEntered", true);
        } else {
            List<Product> findProduct = productsRepository.findByCategory(filter);

            if (findProduct.isEmpty()) {
                model.put("productDidntFound", true);
            }

            model.put("products", findProduct);
        }

        return "main";
    }

    @PostMapping("/delete")
    public String addProducts(@RequestParam Integer productID, Map<String, Object> model) {
        productsRepository.delete(productsRepository.findById(productID));

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "main";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam Integer productID,
                              @RequestParam String kindOfEditInformation,
                              @RequestParam String editInformation,
                              Map<String, Object> model) {
        Product productForEdit = productsRepository.findById(productID);

        if (kindOfEditInformation.equals("editProductName")) {
            productForEdit.setProductName(editInformation);
        } else {
            productForEdit.setCategory(editInformation);
        }

        productsRepository.save(productForEdit);

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "main";
    }
}
