package com.hrabrov.electronic_device_catalog.controller;

import com.hrabrov.electronic_device_catalog.domain.Goods;
import com.hrabrov.electronic_device_catalog.repositories.GoodsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        model.put("allGoods", allGoods);
        return "main";
    }

    @PostMapping("/main")
    public String addGoods(@RequestParam String goodsName, @RequestParam String category,
                           Map<String, Object> model) {
        Goods goods = new Goods(goodsName, category);
        goodsRepository.save(goods);

        Iterable<Goods> allGoods = goodsRepository.findAll();
        model.put("allGoods", allGoods);

        return "main";

    }
}
