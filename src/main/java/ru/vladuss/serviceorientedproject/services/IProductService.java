package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Product;

import java.util.List;
import java.util.UUID;

@Service
public interface IProductService<String> {

    void addProduct(Product product);

    void deleteByUUID(UUID uuid);

    Product findByUUID(UUID uuid);

    List<Product> findAll();

    void editProduct(Product updatingProduct);
}
