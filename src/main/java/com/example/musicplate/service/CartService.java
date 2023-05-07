package com.example.musicplate.service;

import com.example.musicplate.models.Cart;
import com.example.musicplate.repositories.CartRepo;

public class CartService {

    private final CartRepo cartRepo;

    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public void addCart(Cart cart) {
        cartRepo.addCart(cart);
    }

    public void removeCart(Long id) {
        cartRepo.removeCart(id);
    }

    public void removeCartByPlate(Long cartId, Long plateId) {
        cartRepo.removeCartByPlate(cartId, plateId);
    }

    public Cart getCart(Long id) {
        return cartRepo.getCart(id);
    }
}

