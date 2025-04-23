package es.uc3m.microblog.services;

import es.uc3m.microblog.model.Cart;
import es.uc3m.microblog.model.CartItem;
import es.uc3m.microblog.model.Purchase;
import es.uc3m.microblog.model.PurchaseItem;
import es.uc3m.microblog.model.User;
import es.uc3m.microblog.model.PurchaseRepository;
import es.uc3m.microblog.model.UserRepository;
import es.uc3m.microblog.dto.CheckoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public void checkout(String userEmail, CheckoutDto checkoutDto) {
        User user = userRepository.findByEmail(userEmail);
        Cart cart = cartService.getCart(userEmail);
        if(cart.getItems().isEmpty()) {
            return; // O lanzar una excepción
        }
        
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setPurchaseDate(LocalDateTime.now());
        purchase.setRecipientName(checkoutDto.getRecipientName());
        purchase.setShippingAddress(checkoutDto.getShippingAddress());
        
        double total = 0;
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            PurchaseItem pi = new PurchaseItem();
            pi.setProduct(cartItem.getProduct());
            pi.setQuantity(cartItem.getQuantity());
            pi.setPriceAtPurchase(cartItem.getProduct().getCurrentPrice());
            pi.setPurchase(purchase);
            purchaseItems.add(pi);
            total += cartItem.getProduct().getCurrentPrice() * cartItem.getQuantity();
        }
        purchase.setItems(purchaseItems);
        purchase.setTotalAmount(total);
        
        purchaseRepository.save(purchase);
        
        cartService.clearCart(userEmail);

        // Construir el resumen de compra
        StringBuilder resumen = new StringBuilder();
        resumen.append("Gracias por tu compra en TechWave!\n\n");
        resumen.append("ID de compra: ").append(purchase.getId()).append("\n");
        resumen.append("Fecha: ").append(purchase.getPurchaseDate()).append("\n");
        resumen.append("Destinatario: ").append(purchase.getRecipientName()).append("\n");
        resumen.append("Dirección de envío: ").append(purchase.getShippingAddress()).append("\n\n");
        resumen.append("Resumen de productos:\n");

        for (PurchaseItem item : purchase.getItems()) {
            resumen.append("- ").append(item.getProduct().getName())
                .append(" × ").append(item.getQuantity())
                .append(" = ").append(String.format("%.2f", item.getQuantity() * item.getPriceAtPurchase()))
                .append("€\n");
        }

        resumen.append("\nTotal: ").append(String.format("%.2f", purchase.getTotalAmount())).append("€\n");

        // Enviar email al usuario
        emailService.sendSimpleMessage(
            user.getEmail(),
            "Confirmación de compra en TechWave",
            resumen.toString()
        );

    }

    @Override
    public List<Purchase> getPurchaseHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        return purchaseRepository.findByUser(user);
    }
}
