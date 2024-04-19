package com.elice.team4.singleShop;

import com.elice.team4.singleShop.cart.repository.CartItemRepository;
import com.elice.team4.singleShop.cart.repository.CartRepository;
import com.elice.team4.singleShop.category.repository.CategoryRepository;
import com.elice.team4.singleShop.order.entity.OrdersRepository;
import com.elice.team4.singleShop.order.repository.OrderRepository;
import com.elice.team4.singleShop.product.repository.ProductRepository;
import com.elice.team4.singleShop.user.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class SingleShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingleShopApplication.class, args);
	}

	@Bean
	@Profile(value = {"local", "test", "default"})
	public DataInit stubDataInit(UserRepository userRepository,
								 CategoryRepository categoryRepository,
								 ProductRepository productRepository,
								 OrderRepository orderRepository,
								 CartRepository cartRepository,
								 CartItemRepository cartItemRepository,
								 PasswordEncoder passwordEncoder,
								 OrdersRepository ordersRepository) {
		return new DataInit(userRepository, categoryRepository, productRepository, orderRepository, cartRepository,
				cartItemRepository, passwordEncoder, ordersRepository);
	}
}
