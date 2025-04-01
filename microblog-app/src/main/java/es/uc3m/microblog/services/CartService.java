package es.uc3m.microblog.services;

import es.uc3m.microblog.model.Cart;

public interface CartService {
    Cart getCart(String userEmail);
    void addItem(String userEmail, Integer productId, Integer quantity);
    void removeItem(String userEmail, Integer productId);
    void clearCart(String userEmail);
}
