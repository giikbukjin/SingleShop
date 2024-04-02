package com.elice.team4.singleShop.cart.controller;

import com.elice.team4.singleShop.cart.entity.Cart;
import com.elice.team4.singleShop.cart.service.CartService;
import com.elice.team4.singleShop.product.domain.Product;
import com.elice.team4.singleShop.product.service.ProductService;
import com.elice.team4.singleShop.user.entity.User;
import com.elice.team4.singleShop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


}