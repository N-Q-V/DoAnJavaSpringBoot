package com.example.fashion_spring_boot.dao;

import com.example.fashion_spring_boot.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUser_Id(Long id);
    Page<Order> findAllByOrderByOrderDateDesc(Pageable pageable);

    Order findOrderById(Long id);
}
