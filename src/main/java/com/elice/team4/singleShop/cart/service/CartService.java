package com.elice.team4.singleShop.cart.service;

import com.elice.team4.singleShop.cart.entity.CartItem;
import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.global.exception.NotEnoughStockException;
import com.elice.team4.singleShop.order.service.OrderService;
import com.elice.team4.singleShop.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.user.entity.User;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    //장바구니 생성
    @Transactional
    public void createCart(User user) {
        Cart cart = Cart.createCart(user);
        cartRepository.save(cart);
    }
    //장바구니 추가
    @Transactional
    public void addCart(User user, Product product, int count) {
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, product, count);
            cartItemRepository.save(cartItem);
        } else {
            cartItem.addCount(count);
        }
    }

    // 장바구니 조회
    @Transactional
    public List<CartItem> userCartView(Cart cart){
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItem> userItems = new ArrayList<>();

        for(CartItem cartItem : cartItems){
            if(cartItem.getCart().getId() == cart.getId()){
                userItems.add(cartItem);
            }
        }

        return userItems;
    }

    // 장바구니 특정 아이템 삭제
    @Transactional
    public void cartItemDelete(long id){
        cartItemRepository.deleteById(id);
    }

    // 장바구니 아이템 전체 삭제
    @Transactional
    public void cartDelete(int id){
        List<CartItem> cartItems = cartItemRepository.findAll(); // 전체 cartItem 찾기

        // 반복문을 이용하여 접속 User의 CartItem 만 찾아서 삭제
        for(CartItem cartItem : cartItems){
            if(cartItem.getCart().getUser().getId() == id){
                cartItem.getCart().setCount(cartItem.getCart().getCount() - 1);
                cartItemRepository.deleteById(cartItem.getId());
            }
        }
    }

    // 장바구니 결제
    @Transactional
    public void cartPayment(long id){
        List<CartItem> cartItems = cartItemRepository.findAll(); // 전체 cartItem 찾기
        List<CartItem> userCartItems = new ArrayList<>();
        User buyer = userRepository.findById(id).get();

        // 반복문을 이용하여 접속 User의 CartItem만 찾아서 저장
        for(CartItem cartItem : cartItems){
            if(cartItem.getCart().getUser().getId() == buyer.getId()){
                userCartItems.add(cartItem);
            }
        }


        // 반복문을 이용하여 접속 User의 CartItem 만 찾아서 삭제
        for(CartItem cartItem : userCartItems){
            // 재고 변경
            int stock = cartItem.getProduct().getStock(); // 현재 재고를 변수에 저장
            if(stock < cartItem.getCount()){
                throw new NotEnoughStockException("상품의 재고가 부족합니다. ( 현재 재고 수량 : " + stock + ")");
            }
            stock = stock - cartItem.getCount(); // 저장된 변수를 결제한 갯수만큼 빼준다
            cartItem.getProduct().setStock(stock); // 재고갯수 변경


        }

    }

}