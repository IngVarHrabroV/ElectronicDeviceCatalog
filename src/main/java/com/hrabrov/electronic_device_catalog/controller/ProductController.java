package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Product;
import com.hrabrov.electronic_device_catalog.repositories.ProductsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ProductController {
    private final ProductsRepository productsRepository;

    public ProductController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/productsPanel")
    public String productPanel() {
        return "productPanel";
    }

    @PostMapping("/addProduct")
    public String addProducts(
            @RequestParam String productName,
            @RequestParam String category,
            Map<String, Object> model
    ) {
        Product newProduct = new Product(productName, category);
        productsRepository.save(newProduct);

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "redirect:/main";
    }

    @PostMapping("/deleteProduct")
    public String addProducts(@RequestParam Integer productID, Map<String, Object> model) {
        if (!productsRepository.existsById(productID)) {
            model.put("idForDeleteDidntFound", true);
            model.put("deletingId", productID);
            return "redirect:/main";
        }

        productsRepository.delete(productsRepository.findById(productID));

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "redirect:/main";
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam Integer productID,
                              @RequestParam String kindOfEditInformation,
                              @RequestParam String editInformation,
                              Map<String, Object> model) {

        if (!productsRepository.existsById(productID)) {
            model.put("idForEditDidntFound", true);
            model.put("editingId", productID);
            return "redirect:/main";
        }

        Product productForEdit = productsRepository.findById(productID);

        if (kindOfEditInformation.equals("editProductName")) {
            productForEdit.setProductName(editInformation);
        } else {
            productForEdit.setCategory(editInformation);
        }

        productsRepository.save(productForEdit);

        Iterable<Product> allProducts = productsRepository.findAll();
        model.put("products", allProducts);

        return "redirect:/main";
    }
}
