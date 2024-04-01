package com.elice.team4.singleShop.order.service;

import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistDto;
import com.elice.team4.singleShop.order.dto.OrderItemDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.entity.OrderItem;
import com.elice.team4.singleShop.order.repository.OrderItemRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    /*public Long order(OrderDto orderDto, String Email) {
        Product product = productRepository.findById(orderDto.getProductId()) // 주문할 상품 조회
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email); // 로그인한 회원의 이메일을 이용해 회원 조회

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(product, orderDto.getCount()); // 주문할 상품과 주문 수량 이용해 주문 상품 생성

        orderItemList.add(orderItem);

        Order order = Order.createOrder(user, orderItemList); // 회원 정보와 주문 상품 리스트 이용해 주문 엔티티 생성
        orderRepository.save(order); // 생성한 주문 저장

        return order.getId();
    }*/

    // 주문 목록 조회
    /*@Transactional
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable); // 유저 이메일, 페이징 조건 이용해 주문 목록 조회
        Long totalCount = orderRepository.countOrder(email); // 주문 총 개수

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        // 주문 리스트 돌면서 주문 조회 페이지에 DTO 전달
        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                // 상품이미지 코드
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, 상품이미지 url); // 주문 상품 이미지 조회
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount); // 페이지 구현
    }*/
}