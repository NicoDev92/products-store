package com.nicode.backend.shopapp.backendshopapp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;

public interface ProductPagingAndSortingRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findAllByNameContainingOrDescriptionContaining(String keyword1, String keyword2, Pageable pageable);
}
