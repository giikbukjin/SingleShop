package com.elice.team4.singleShop.order.service;

import com.elice.team4.singleShop.order.dto.OrderDto;
import com.elice.team4.singleShop.order.dto.OrderHistDto;
import com.elice.team4.singleShop.order.dto.OrderItemDto;
import com.elice.team4.singleShop.order.entity.Order;
import com.elice.team4.singleShop.order.entity.OrderItem;
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
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;


    // 하나의 상품 주문 생성 & 저장
    public Long order(OrderDto orderDto, String email) {
        Product product = productRepository.findById(orderDto.getProductId()) // 주문할 상품 조회
                .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByEmail(email); // 로그인한 회원의 이메일을 이용해 회원 조회

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(product, orderDto.getCount()); // 주문할 상품과 주문 수량 이용해 주문 상품 생성

        orderItemList.add(orderItem);

        // 회원 정보와 주문 상품 리스트 이용해 주문 엔티티 생성
        Order order = Order.createOrder(user, orderItemList);

        orderRepository.save(order); // 생성한 주문 저장

        return order.getId();
    }

    // 여러 개의 상품 주문 생성 & 저장
    public Long orders(List<OrderDto> orderDtoList, String email) {

        User user = userRepository.findByEmail(email); // 사용자 정보 조회
        List<OrderItem> orderItemList = new ArrayList<>(); // 빈 리스트 초기화

        // 주문 상품 리스트 생성
        for (OrderDto orderDto : orderDtoList) {
            Product product = productRepository.findById(orderDto.getProductId()) // 상품 ID 이용해 상품 정보 조회
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(product, orderDto.getCount()); // 주문 상품 객체 생성
            orderItemList.add(orderItem); // 생성한 주문 상품을 주문 상품 리스트에 추가
        }

        Order order = Order.createOrder(user, orderItemList); // 현재 로그인한 회원과 주문 상품 목록 이용해 주문 엔티티 생성
        orderRepository.save(order); // 주문 데이터 저장

        return order.getId(); // 생성된 주문 ID
    }

    // 주문에 배송 정보 추가
    /*public void addDeliveryInfo(Long orderId, DeliveryInfo deliveryInfoDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 배송 정보 생성
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setReceiverName(deliveryInfoDto.getReceiverName());
        deliveryInfo.setReceiverPhoneNumber(deliveryInfoDto.getReceiverPhoneNumber());
        deliveryInfo.setPostalCode(deliveryInfoDto.getPostalCode());
        deliveryInfo.setAddress1(deliveryInfoDto.getAddress1());
        deliveryInfo.setAddress2(deliveryInfoDto.getAddress2());
        deliveryInfo.setDeliveryRequest(deliveryInfoDto.getDeliveryRequest());

        // 주문에 배송 정보 연결
        order.setDeliveryInfo(deliveryInfo);

        // 배송 정보 저장
        deliveryInfoRepository.save(deliveryInfo);
    }*/

    // 주문 목록 조회
    @Transactional
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable); // 유저 이메일, 페이징 조건 이용해 주문 목록 조회
        Long totalCount = orderRepository.countOrder(email); // 주문 총 개수

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        // 주문 리스트 돌면서 주문 조회 페이지에 DTO 전달
        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                String imageUrl = orderItem.getProduct().getImgPath(); // Product에서 이미지 경로 가져오기
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, imageUrl); // 주문 상품 이미지 조회
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount); // 페이지 구현
    }

    // 주문 취소
    @Transactional
    public boolean validateOrder(Long orderId, String email) {

        User curUser = userRepository.findByEmail(email); // 현재 로그인한 사용자 정보 조회
        Order order = orderRepository.findById(orderId) // 주문 정보 조회
                .orElseThrow(EntityNotFoundException::new);
        User saveUser = order.getUser(); // 해당 주문의 소유자 정보 조회

        // 현재 로그인한 사용자와 주문한 사용자가 같은지 검사
        if (!StringUtils.equals(curUser.getEmail(), saveUser.getEmail())) {
            return false; // 다르면 false
        }

        return true; // 같으면 true
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId) // 주문 정보 조회
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder(); // 주문 취소 메서드 호출
    }

    // 주문 정보 수정
    @Transactional
    public void updateDeliveryInfo(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 주문 정보 업데이트
        order.setReceiverName(orderDto.getReceiverName());
        order.setReceiverPhoneNumber(orderDto.getReceiverPhoneNumber());
        order.setPostalCode(orderDto.getPostalCode());
        order.setAddress1(orderDto.getAddress1());
        order.setAddress2(orderDto.getAddress2());
        order.setDeliveryRequest(orderDto.getDeliveryRequest());

        // 주문 정보 저장
        orderRepository.save(order);
    }


    // 주문 내역 조회 - 관리자
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll(); // DB에서 모든 주문 내역 조회
        return orders.stream()
                .map(order -> mapToOrderDto(order))
                .collect(Collectors.toList()); // 조회된 주문을 OrderDto로 변환
    }

    // 주문 엔티티를 주문 데이터 전송 객체로 변환하는 메서드
    private OrderDto mapToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        // Order 엔티티에서 필요한 정보를 OrderDto로 매핑하여 반환
        orderDto.setProductId(order.getId()); // orderDto의 productId에 order의 id를 설정
        orderDto.setCount(order.getOrderItems().size()); // orderDto의 count에 주문 상품의 개수를 설정
        // 필요한 정보들을 매핑하여 orderDto에 설정
        return orderDto;
    }

    // 특정 사용자의 주문 내역 조회 - 관리자
    @Transactional
    public List<OrderDto> getUserOrders(String email) {
        List<Order> orders = orderRepository.findByUserEmail(email);
        return orders.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    // 주문 상태 수정 - 관리자
    public boolean updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId); // 주문 ID 사용해 주문 조회
        if (optionalOrder.isPresent()) {
            // 주문 존재하면 새로운 주문 상태로 업데이트 & 저장
            Order order = optionalOrder.get();
            order.setOrderStatus(newStatus);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    // 주문 내역 삭제 - 관리자
    public boolean deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId); // 주문 ID 사용해 주문 조회
        if (optionalOrder.isPresent()) {
            // 주문 존재하면 해당 주문 삭제
            Order order = optionalOrder.get();
            orderRepository.delete(order);
            return true;
        }
        return false;
    }
}