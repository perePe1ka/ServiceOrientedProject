package ru.vladuss.serviceorientedproject.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.repositories.IProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService{

    private IProductRepository productRepository;

    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void addProduct(Product product) {
        Optional<Product> exsitingProduct = productRepository.findById(product.getUuid());

        if (exsitingProduct.isPresent()) {
            Product existing = exsitingProduct.get();

            if (existing.getStockQuantity() == 0) {
                existing.setInStock(true);
            }
            existing.setStockQuantity(existing.getStockQuantity() + product.getStockQuantity());

            productRepository.saveAndFlush(existing);
        } else {
            productRepository.saveAndFlush(product);
            System.out.println("logi novogo Producta");
        }

        productRepository.saveAndFlush(product);
    }


    public void deleteByUUID(UUID uuid) {
        Optional<Product> exsitingProduct = productRepository.findById(uuid);
        if (exsitingProduct.isPresent()) {
            Product product = exsitingProduct.get();

            if (product.getStockQuantity() > 1) {
                product.setStockQuantity(product.getStockQuantity() - 1);
            } else if (product.getStockQuantity() == 1) {
                product.setStockQuantity(0);
                product.setInStock(false);
            }
            productRepository.saveAndFlush(product);
        } else {
            System.out.println("logi sdelat pozhe");
        }
    }


    public Product findByUUID(UUID uuid) {
        Optional<>
    }


    public List<Product> findAll() {
        return productRepository.findAll();
    }


    public void editProduct(Product product) {
        productRepository.
    }


    public Product getAll(Object product) {
        return null;
    }
}
