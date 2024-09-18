package ru.vladuss.serviceorientedproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vladuss.serviceorientedproject.entity.Product;
import ru.vladuss.serviceorientedproject.services.IProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        EntityModel<Product> productEntityModel = EntityModel.of(product);
        productEntityModel.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withSelfRel());
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/{productUUID}")
    public ResponseEntity<EntityModel<Product>> getProduct(@PathVariable UUID productUUID) {
        Product product = productService.findByUUID(productUUID);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Product> productEntityModel = EntityModel.of(product);

        productEntityModel.add(linkTo(methodOn(ProductController.class).getProduct(productUUID)).withSelfRel());
        productEntityModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        productEntityModel.add(linkTo(methodOn(ProductController.class).deleteProduct(productUUID)).withSelfRel());

        return ResponseEntity.ok(productEntityModel);
    }

    @GetMapping("/")
    public ResponseEntity<EntityModel<List<EntityModel<Product>>>> getAllProducts() {
        List<Product> products = productService.findAll();
        List<EntityModel<Product>> productResources = products.stream()
                .map(product -> {
                    EntityModel<Product> resource = EntityModel.of(product);
                    resource.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withSelfRel());
                    return resource;
                })
                .collect(Collectors.toList());

        EntityModel<List<EntityModel<Product>>> resource = EntityModel.of(productResources);
        resource.add(linkTo(methodOn(ProductController.class).addProduct(new Product())).withRel("add-product"));

        return ResponseEntity.ok(resource);
    }

    @PatchMapping("/update")
    public ResponseEntity<EntityModel<Product>> editProduct(@RequestBody Product product) {
        productService.editProduct(product);

        EntityModel<Product> resource = EntityModel.of(product);
        resource.add(linkTo(methodOn(ProductController.class).getProduct(product.getUuid())).withSelfRel());
        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/delete/{productUUID}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productUUID) {
        productService.deleteByUUID(productUUID);
        EntityModel<Void> resource = EntityModel.of(null);
        resource.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("all-products"));

        return ResponseEntity.ok(resource);
    }
}
