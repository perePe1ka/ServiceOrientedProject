package ru.vladuss.serviceorientedproject.services;

import org.springframework.stereotype.Service;
import ru.vladuss.serviceorientedproject.entity.Product;

import java.util.List;

@Service
public interface IProductService<T, U> {

    void addProduct(T product);

    void deleteByUUID(U uuid);

    Product findByUUID(U uuid);

    List<Product> findAll();

    void editProduct(T updatingProduct);
}
