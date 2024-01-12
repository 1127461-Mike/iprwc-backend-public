package com.example.demo.service;

import com.example.demo.dto.OrderDetailDto;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.OrderItemDetailDto;
import com.example.demo.dto.OrderItemDto;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrderDetailsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderDto orderDto, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setDatePurchased(LocalDateTime.now());
        double total = 0;

        order = orderRepository.save(order); // cannot be null so saved before being complete

        for (OrderItemDto item : orderDto.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (!product.isAvailable()) {
                throw new RuntimeException("Product is not available for purchase.");
            }
            if (item.getProductId() == null) {
                throw new RuntimeException("Product ID cannot be null");
            }

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setProduct(product);
            orderDetails.setQuantity(item.getQuantity());
            orderDetails.setPrice(product.getPrice());
            total += product.getPrice() * item.getQuantity();

            orderDetailsRepository.save(orderDetails);
        }
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }


    public List<Order> getOrdersByUserEmail(String userEmail) {
        return orderRepository.getOrdersByUserEmail(userEmail);
    }


    public OrderDetailDto getOrderDetailsForUser(UUID orderId, String userEmail) {
        Order order = orderRepository.findByIdAndUserEmail(orderId, userEmail)
                .orElseThrow(() -> new RuntimeException("Order not found or access denied"));
        return convertOrderToDto(order);
    }

    public OrderDetailDto getOrderDetails(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertOrderToDto(order);
    }

    private OrderDetailDto convertOrderToDto(Order order) {
        OrderDetailDto orderDto = new OrderDetailDto();
        orderDto.setOrderId(order.getId());
        orderDto.setDatePurchased(order.getDatePurchased());
        orderDto.setTotalPrice(order.getTotalPrice());

        List<OrderItemDetailDto> itemDtos = order.getOrderDetails().stream()
                .map(orderDetail -> {
                    OrderItemDetailDto itemDto = new OrderItemDetailDto();
                    itemDto.setProductId(orderDetail.getProduct().getId());
                    itemDto.setProductName(orderDetail.getProduct().getName());
                    itemDto.setProductPrice(orderDetail.getPrice());
                    itemDto.setQuantity(orderDetail.getQuantity());
                    return itemDto;
                })
                .collect(Collectors.toList());

        orderDto.setItems(itemDtos);
        return orderDto;
    }

}
