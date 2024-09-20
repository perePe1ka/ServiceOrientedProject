package ru.vladuss.serviceorientedproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vladuss.serviceorientedproject.entity.Orders;

import java.util.UUID;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, UUID> {
}
