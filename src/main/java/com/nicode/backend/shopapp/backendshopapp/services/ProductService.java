package com.nicode.backend.shopapp.backendshopapp.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;

public interface ProductService {

    List<Product> findAll();

    public Page<Product> findAllPaged(int page, int elements);

    Page<Product> findByKeywordPaged(String keyword, int page, int elements);

}
