package com.nicode.backend.shopapp.backendshopapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicode.backend.shopapp.backendshopapp.exceptions.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public Optional<Product> getById(Long id) {
        return this.repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) this.repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> findAllPaged(int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        return pagingAndSortingRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> findByKeywordPaged(String keyword, int page, int elements) {
        Pageable pageable = PageRequest.of(page, elements);
        return pagingAndSortingRepository.findAllByNameContainingOrDescriptionContaining(keyword, keyword, pageable);
    }

    @Transactional
    public String create(Product product) {
        try {
            this.repository.save(product);
            return ("Producto creado con éxito.");
        } catch (Exception e) {
            return ("No se pusdo crear el producto. Trace: " + e);
        }
    }

    @Transactional
    public String update(Long id, Product product) {
        Optional<Product> existinfProduct = this.repository.findById(id);

        if (existinfProduct.isPresent()) {
            Product productToUpdate = existinfProduct.get();
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setImagePath(product.getImagePath());
            productToUpdate.setStock(product.getStock());

            this.repository.save(productToUpdate);
            return ("Producto actualizado con éxito.");
        } else {
            throw new EntityNotFoundException("No se encontró el producto con id: " + product.getId());
        }
    }

    @Transactional
    public String delete(Long id) {
        Optional<Product> productToDelete = this.repository.findById(id);

        if (productToDelete.isPresent()) {
            this.repository.deleteById(id);
            return ("Se ha eliminado correctamente el producto.");
        } else {
            throw new EntityNotFoundException("No se encotró el producto con id: " + id);
        }
    }
}
