package ru.vladuss.serviceorientedproject.controllers.dataFetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.services.IProductService;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class ProductDataFetcher {

    private final IProductService<Product, UUID> productService;

    @Autowired
    public ProductDataFetcher(IProductService<Product, UUID> productService) {
        this.productService = productService;
    }

    @DgsQuery
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @DgsQuery
    public Product getProduct(@InputArgument UUID productUUID) throws IllegalArgumentException {
        Product product = productService.findByUUID(productUUID);
        if (product.getUuid() == null) {
            throw new IllegalArgumentException();
        }
        return product;
    }

    @DgsMutation
    public Product addProduct(@InputArgument Product product) {
        productService.addProduct(product);
        return product;
    }

    @DgsMutation
    public void deleteProduct(@InputArgument UUID productUUID) {
        productService.deleteByUUID(productUUID);
    }

    @DgsMutation
    public Product editProduct(@InputArgument Product product) {
        productService.editProduct(product);
        return product;
    }
}
