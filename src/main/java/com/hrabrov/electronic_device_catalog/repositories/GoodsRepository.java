package com.hrabrov.electronic_device_catalog.repositories;

import com.hrabrov.electronic_device_catalog.domain.Goods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
    List<Goods> findByCategory(String category);
}
