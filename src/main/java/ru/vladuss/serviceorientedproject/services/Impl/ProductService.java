package ru.vladuss.serviceorientedproject.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.repositories.IProductRepository;
import ru.vladuss.serviceorientedproject.services.IProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService implements IProductService<String> {

    private IProductRepository productRepository;

    @Autowired
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Product product) {
        if (product.getUuid() != null && productRepository.existsById(product.getUuid())) {
            Optional<Product> existingProduct = productRepository.findById(product.getUuid());

            if (existingProduct.isPresent()) {
                Product existing = existingProduct.get();
                if (existing.getStockQuantity() == 0) {
                    existing.setInStock(true);
                }
                existing.setStockQuantity(existing.getStockQuantity() + product.getStockQuantity());
                productRepository.saveAndFlush(existing);
            }
        } else {
            productRepository.saveAndFlush(product);
        }
    }

    @Override
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

    @Override
    public Product findByUUID(UUID uuid) {
        Optional<Product> existingProduct = productRepository.findById(uuid);
        if (existingProduct.isPresent()) {
            return existingProduct.get();
        } else {
            System.out.println("takzhe logi need");
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void editProduct(Product updatingProduct) {
        if (updatingProduct.getUuid() != null) {
            Optional<Product> exisitngProduct = productRepository.findById(updatingProduct.getUuid());
            if (exisitngProduct.isPresent()) {
                Product existing = exisitngProduct.get();

                existing.setName(updatingProduct.getName());
                existing.setDescription(updatingProduct.getDescription());
                existing.setPrice(updatingProduct.getPrice());

                if (!existing.getStockQuantity().equals(updatingProduct.getStockQuantity())) {
                    if (existing.getStockQuantity() == 0 && updatingProduct.getStockQuantity() > 0) {
                        existing.setInStock(true);
                    }
                    if (updatingProduct.getStockQuantity() == 0) {
                        existing.setInStock(false);
                    }
                    existing.setStockQuantity(updatingProduct.getStockQuantity());
                }
                productRepository.saveAndFlush(existing);
            } else {
                System.out.println("logi");
            }
        } else {
            System.out.println("logi");
        }
    }
}
