package com.nicode.backend.shopapp.backendshopapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
