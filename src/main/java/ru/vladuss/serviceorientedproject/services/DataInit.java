//package ru.vladuss.serviceorientedproject.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import ru.vladuss.serviceorientedproject.entity.Orders;
//import ru.vladuss.serviceorientedproject.entity.Product;
//import ru.vladuss.serviceorientedproject.repositories.IOrderRepository;
//import ru.vladuss.serviceorientedproject.repositories.IProductRepository;
//
//@Component
//public class DataInit implements CommandLineRunner {
//
//    private final IOrderRepository orderRepository;
//
//    private final IProductRepository productRepository;
//
//    @Autowired
//    public DataInit(IOrderRepository orderRepository, IProductRepository productRepository) {
//        this.orderRepository = orderRepository;
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Orders orders1 = new Orders(null, "Customer 5", "Address 5", null, 1000);
//        Orders orders2 = new Orders(null, "Customer 6", "Address 6", null, 2000);
//        Orders orders3 = new Orders(null, "Customer 7", "Address 7", null, 3000);
//        Orders orders4 = new Orders(null, "Customer 8", "Address 8", null, 4000);
//
//        orderRepository.saveAndFlush(orders1);
//        orderRepository.saveAndFlush(orders2);
//        orderRepository.saveAndFlush(orders3);
//        orderRepository.saveAndFlush(orders4);
//
//
//        Product product1 = new Product("Product 5", "Description 5", 10.0, 1, orders1, true);
//        Product product2 = new Product("Product 6", "Description 6", 20.0, 2, orders2, true);
//        Product product3 = new Product("Product 7", "Description 7", 30.0, 3, orders3, true);
//        Product product4 = new Product("Product 8", "Description 8", 40.0, 4, orders4, true);
//
//        productRepository.save(product1);
//        productRepository.save(product2);
//        productRepository.save(product3);
//        productRepository.save(product4);
//    }
//}
//
