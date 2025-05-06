package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.Purchase;
import es.uc3m.microblog.services.PurchaseService;
import es.uc3m.microblog.dto.CheckoutDto;
import es.uc3m.microblog.dto.PurchaseDto;
import es.uc3m.microblog.dto.PurchaseItemDto;

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
    public ResponseEntity<List<PurchaseDto>> getPurchaseHistory(Principal principal) {
        List<Purchase> purchases = purchaseService.getPurchaseHistory(principal.getName());

        List<PurchaseDto> dtos = purchases.stream().map(p -> {
            PurchaseDto dto = new PurchaseDto();
            dto.setPurchaseDate(p.getPurchaseDate().toString());
            dto.setTotalAmount(p.getTotalAmount());

            // ← Aquí asignas los nuevos campos:
            dto.setRecipientName(p.getRecipientName());
            dto.setShippingAddress(p.getShippingAddress());

            List<PurchaseItemDto> itemDtos = p.getItems().stream().map(i -> {
                PurchaseItemDto itemDto = new PurchaseItemDto();
                itemDto.setProductName(i.getProduct().getName());
                itemDto.setQuantity(i.getQuantity());
                itemDto.setPriceAtPurchase(i.getPriceAtPurchase());
                return itemDto;
            }).toList();

            dto.setItems(itemDtos);
            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }

}
