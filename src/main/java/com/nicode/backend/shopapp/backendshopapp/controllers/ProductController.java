package com.nicode.backend.shopapp.backendshopapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;
import com.nicode.backend.shopapp.backendshopapp.services.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("/products")
    public List<Product>  list() {
        return this.service.findAll();
    }

    @GetMapping("/products/search/{keyword}")
    public Page<Product> searchProducts(
        @PathVariable String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int elements) {
        
        Page<Product> productsPage = this.service.findByKeywordPaged(keyword, page, elements);

        return productsPage;
    }
}
