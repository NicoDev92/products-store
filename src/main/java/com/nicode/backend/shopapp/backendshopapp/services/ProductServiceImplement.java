package com.nicode.backend.shopapp.backendshopapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicode.backend.shopapp.backendshopapp.models.entities.Product;
import com.nicode.backend.shopapp.backendshopapp.repositories.ProductPagingAndSortingRepository;
import com.nicode.backend.shopapp.backendshopapp.repositories.ProductRepository;

@Service
public class ProductServiceImplement implements ProductService {

    private final ProductRepository repository;
    private final ProductPagingAndSortingRepository pagingAndSortingRepository;

    @Autowired
    public ProductServiceImplement(ProductRepository repository,
            ProductPagingAndSortingRepository pagingAndSortingRepository) {
        this.repository = repository;
        this.pagingAndSortingRepository = pagingAndSortingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAllPaged(int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        return pagingAndSortingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findByKeywordPaged(String keyword, int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        return pagingAndSortingRepository.findAllByNameContainingOrDescriptionContaining(keyword, keyword, pageable);
    }
}
