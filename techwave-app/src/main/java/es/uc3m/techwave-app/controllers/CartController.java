package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.Cart;
import es.uc3m.microblog.services.CartService;
import es.uc3m.microblog.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {//devuelve el carrito del usuario autenticado
        Cart cart = cartService.getCart(principal.getName());
        System.out.println("Devolviendo carrito al frontend. Total productos: " + cart.getItems().size());
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto itemDto, Principal principal) {
        cartService.addItem(principal.getName(), itemDto.getProductId(), itemDto.getQuantity());
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Integer productId, Principal principal) {
        cartService.removeItem(principal.getName(), productId);
        Cart cart = cartService.getCart(principal.getName());
        System.out.println("Devolviendo carrito al frontend tras eliminar producto. Total productos: " + cart.getItems().size());
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(Principal principal) {
        cartService.clearCart(principal.getName());
        return ResponseEntity.ok().build();
    }
}
