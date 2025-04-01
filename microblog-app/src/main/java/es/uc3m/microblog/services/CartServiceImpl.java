package es.uc3m.microblog.services;

import es.uc3m.microblog.model.Cart;
import es.uc3m.microblog.model.CartItem;
import es.uc3m.microblog.model.Product;
import es.uc3m.microblog.model.User;
import es.uc3m.microblog.model.CartRepository;
import es.uc3m.microblog.model.ProductRepository;
import es.uc3m.microblog.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public Cart getCart(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Cart cart = cartRepository.findByUser(user);
        if(cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public void addItem(String userEmail, Integer productId, Integer quantity) {
        User user = userRepository.findByEmail(userEmail);
        Cart cart = getCart(userEmail);
        Optional<Product> productOpt = productRepository.findById(productId);
        if(productOpt.isPresent()) {
            Product product = productOpt.get();
            boolean exists = false;
            for (CartItem item : cart.getItems()) {
                if(item.getProduct().getId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setQuantity(quantity);
                newItem.setCart(cart);
                cart.getItems().add(newItem);
            }
            cartRepository.save(cart);
        }
    }

    @Override
    public void removeItem(String userEmail, Integer productId) {
        Cart cart = getCart(userEmail);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userEmail) {
        Cart cart = getCart(userEmail);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
