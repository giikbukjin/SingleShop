package com.elice.team4.singleShop.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    //private final ProductRepository productRepository;
    //private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    //private final CartRepository cartRepository;

    // 주문 처리
    /*public Long order(OrderDto orderDto, String email) {
        Product product = productRepository.findById(orderDto.getProductId()) // 주문할 제품 조회
                //.orElseThrow();

        User user = userRepository.findByEmail(email); // 주문하는 사용자 조회

        List<Product> productList = new ArrayList<>(); // 주문할 재품 목록 초기화

        Product orderedProduct = Product.createProduct(product, orderDto.getCount()); // 주문할 제품 생성
        productList.add(orderedProduct);

        Order order = Order.createOrder(user, productList); // 주문 생성
        orderRepository.save(order); // 리포지토리에 주문 저장

        return order.getId(); // 생성된 주문ID 반환
    }*/
}