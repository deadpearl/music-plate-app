package com.example.musicplate.repositories;

import com.example.musicplate.models.Cart;
import com.example.musicplate.models.Plate;

import java.util.List;

public interface CartRepo {
    void addCart(Cart cart);
    void removeCart(Long id);
    void removeCartByPlate(Long user_id, Long plate_id);
    Cart getCart(Long user_id);
    List<Plate> getCartPlates();
}
