package ru.vladuss.serviceorientedproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.model.ProductActionModel;
import ru.vladuss.serviceorientedproject.services.IProductService;
import ru.vladuss.serviceorientedproject.utils.NotFoundUuidException;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService<Product, UUID> productService;

    @Autowired
    public ProductController(IProductService<Product, UUID> productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws NotFoundUuidException {
        productService.addProduct(product);
        EntityModel<Product> productEntityModel = EntityModel.of(product);
        productEntityModel.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withSelfRel());
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/{productUUID}")
    public ResponseEntity<ProductActionModel> getProduct(@PathVariable UUID productUUID) throws NotFoundUuidException {
        Product product = productService.findByUUID(productUUID);

        if (product == null) {
            throw new NotFoundUuidException("Product not found for UUID: " + productUUID);
        }

        ProductActionModel productActionModel = new ProductActionModel(product);

        productActionModel.add(linkTo(methodOn(ProductController.class).getProduct(productUUID)).withSelfRel());
        productActionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("get-all"));
        productActionModel.add(linkTo(methodOn(ProductController.class).deleteProduct(productUUID)).withRel("delete"));
        productActionModel.add(linkTo(methodOn(ProductController.class).addProduct(product)).withRel("add-product"));
        productActionModel.add(linkTo(methodOn(ProductController.class).editProduct(product)).withRel("edit-product"));

        productActionModel.addProduct("update", "PUT", linkTo(methodOn(ProductController.class).editProduct(product)));
        productActionModel.addProduct("delete", "DELETE", linkTo(methodOn(ProductController.class).deleteProduct(productUUID)));

        return ResponseEntity.ok(productActionModel);
    }


    @GetMapping("/")
    public ResponseEntity<CollectionModel<ProductActionModel>> getAllProducts() {
        List<ProductActionModel> products = productService.findAll().stream()
                .map(product -> {
                    try {
                        ProductActionModel productModel = new ProductActionModel(product);

                        productModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
                        productModel.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withRel("get-one"));
                        productModel.add(linkTo(methodOn(ProductController.class).addProduct(product)).withRel("add-product"));
                        productModel.add(linkTo(methodOn(ProductController.class).editProduct(product)).withRel("edit-product"));
                        productModel.add(linkTo(methodOn(ProductController.class).deleteProduct(product.getUuid())).withRel("delete-product"));

                        productModel.addProduct("update", "PUT", linkTo(methodOn(ProductController.class).editProduct(product)));
                        productModel.addProduct("delete", "DELETE", linkTo(methodOn(ProductController.class).deleteProduct(product.getUuid())));

                        return productModel;
                    } catch (NotFoundUuidException e) {
                        throw new RuntimeException("Product not found for UUID: " + product.getUuid(), e);
                    }
                })
                .toList();

        return ResponseEntity.ok(CollectionModel.of(products));
    }


    @PatchMapping("/update")
    public ResponseEntity<EntityModel<Product>> editProduct(@RequestBody Product product) throws NotFoundUuidException {
        productService.editProduct(product);

        EntityModel<Product> resource = EntityModel.of(product);
        resource.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withSelfRel());
        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/delete/{productUUID}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productUUID) {
        productService.deleteByUUID(productUUID);
        return ResponseEntity.ok().build();
    }
}
