package com.elice.team4.singleShop.cart;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CartTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private Long userId;

    // 사용자 생성
    @BeforeEach
    public void createUser() {
        User user = new User();
        user.setName("testUser"); // 사용자의 이름을 설정합니다.
        user.setPassword("testPassword"); // 사용자의 비밀번호를 설정합니다.
        user.setEmail("test@example.com"); // 사용자의 이메일을 설정합니다.

        // 사용자 저장 및 ID 획득
        user = userRepository.save(user);
        userId = user.getId();
    }

    // 장바구니 생성 및 조회 테스트
    @Test
    public void testCartFunctionality() {
        // HTTP 요청 작성
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-USER-ID", String.valueOf(userId));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // HTTP 클라이언트 생성
        RestTemplate restTemplate = new RestTemplate();

        // 장바구니 생성 API 요청
        String createCartUrl = "http://localhost:8080/cart/" + userId;
        ResponseEntity<String> createCartResponse = restTemplate.exchange(
                createCartUrl, HttpMethod.POST, request, String.class);

        // 응답 확인
        if (createCartResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println("장바구니가 성공적으로 생성되었습니다.");
        } else {
            System.out.println("장바구니 생성에 실패했습니다. 응답 코드: " + createCartResponse.getStatusCode());
            return;
        }

        // 장바구니 조회 API 요청
        String viewCartUrl = "http://localhost:8080/cart/" + userId + "/view";
        ResponseEntity<String> viewCartResponse = restTemplate.exchange(
                viewCartUrl, HttpMethod.GET, request, String.class);

        // 응답 확인
        if (viewCartResponse.getStatusCode().is2xxSuccessful()) {
            System.out.println("장바구니 조회가 성공적으로 수행되었습니다.");
        } else {
            System.out.println("장바구니 조회에 실패했습니다. 응답 코드: " + viewCartResponse.getStatusCode());
        }
    }

    // 테스트 종료 후 사용자 및 장바구니 정보 삭제
    @AfterEach
    public void cleanup() {
        // 사용자 삭제
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            // 사용자가 존재하면 삭제
            userRepository.delete(user);
        }

        // 장바구니 삭제
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            // 장바구니가 존재하면 삭제
            cartRepository.delete(cart);
        }
    }
}