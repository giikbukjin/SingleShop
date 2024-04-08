package com.elice.team4.singleShop;

import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.category.entity.Category;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init stub data");
//        userRepository.save(new User());
        categoryRepository.save(new Category("간편조리식품", "신속하고 편리하게 조리할 수 있는 식품으로, 빠른 시간에 요리를 완성할 수 있도록 가공된 제품입니다."));
        categoryRepository.save(new Category("소포장 반찬", "작은 포장에 담긴 편리한 반찬으로, 한끼 식사나 간편한 식사 준비에 사용됩니다."));
        categoryRepository.save(new Category("소포장 야채", "신선한 야채를 통해 건강한 삶과 풍부한 음식을 즐겨보세요."));

        saveProductIfNotExists("연어 스테이크 200g", 2L, "마지막 청정지역 페로 제도의 프리미엄 냉동 사시미 연어", "음식물 쓰레기 걱정 없이 미리 손질되어 있는 연어 스테이크예요.", "", 100, 8000);
    }

    private void saveProductIfNotExists(String productName, Long categoryId, String summary, String description, String image, int stock, int price) {
        Optional<Product> productOptional = productRepository.findByName(productName);
        if (!productOptional.isPresent()) {
            productRepository.save(new Product(productName, categoryId, summary, description, image, stock, price));
        }
    }
}