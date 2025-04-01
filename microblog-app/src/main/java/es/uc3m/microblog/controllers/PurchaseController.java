package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.Purchase;
import es.uc3m.microblog.services.PurchaseService;
import es.uc3m.microblog.dto.CheckoutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutDto checkoutDto, Principal principal) {
        purchaseService.checkout(principal.getName(), checkoutDto);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<Purchase>> getPurchaseHistory(Principal principal) {
        List<Purchase> history = purchaseService.getPurchaseHistory(principal.getName());
        return ResponseEntity.ok(history);
    }
}
