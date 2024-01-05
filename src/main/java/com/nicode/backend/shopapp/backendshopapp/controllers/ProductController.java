package com.nicode.backend.shopapp.backendshopapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nicode.backend.shopapp.backendshopapp.exceptions.EntityAlreadyExistException;
import com.nicode.backend.shopapp.backendshopapp.exceptions.EntityNotFoundException;
import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;
import com.nicode.backend.shopapp.backendshopapp.services.ProductServiceImplement;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductServiceImplement service;

    @GetMapping("/all/products")
    public List<Product> list() {
        return this.service.findAll();
    }

    @GetMapping("/paged-products")
    public ResponseEntity<Page<Product>> getAllPaged(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int elements) {

        Page<Product> products = this.service.findAllPaged(page, elements);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/search/{keyword}")
    public Page<Product> searchProducts(
            @PathVariable String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int elements) {

        Page<Product> productsPage = this.service.findByKeywordPaged(keyword, page, elements);

        return productsPage;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Optional<Product> product = this.service.getById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody Product product) {
        try {
            String successMessage = this.service.create(product);
            Map<String, String> response = new HashMap<>();
            response.put("message", successMessage);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityAlreadyExistException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Long id,
            @RequestBody Product product) {

        try {
            String succesMessage = this.service.update(id, product);
            Map<String, String> response = new HashMap<>();
            response.put("message", succesMessage);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            String succesMessage = this.service.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", succesMessage);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
