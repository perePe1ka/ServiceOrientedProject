package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Product;

import java.util.List;
import java.util.UUID;

@Service
public interface IProductService<T, U> {

    void createProduct(T product);

    void addProduct(UUID productUuid, int quantity);

    void deleteByUUID(U uuid);

    Product findByUUID(U uuid);

    List<Product> findAll();

    void editProduct(T updatingProduct);
}
