package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Goods;
import com.hrabrov.electronic_device_catalog.repositories.GoodsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private GoodsRepository goodsRepository;

    public MainController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Goods> allGoods = goodsRepository.findAll();
        model.put("goods", allGoods);
        return "main";
    }

    @PostMapping("/main")
    public String addGoods(@RequestParam String goodsName, @RequestParam String category,
                           Map<String, Object> model) {
        Goods newGoods = new Goods(goodsName, category);
        goodsRepository.save(newGoods);

        Iterable<Goods> allGoods = goodsRepository.findAll();
        model.put("goods", allGoods);

        return "main";
    }

    @PostMapping("/filter")
    public String searchGoodsByCategory(@RequestParam String filter, Map<String, Object> model) {
        List<Goods> findGoods = goodsRepository.findByCategory(filter);

        model.put("goods", findGoods);

        return "main";
    }
}
