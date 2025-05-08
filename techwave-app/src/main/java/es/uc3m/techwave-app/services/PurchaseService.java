package es.uc3m.microblog.services;

import es.uc3m.microblog.model.Purchase;
import es.uc3m.microblog.dto.CheckoutDto;
import java.util.List;

public interface PurchaseService {
    void checkout(String userEmail, CheckoutDto checkoutDto);
    List<Purchase> getPurchaseHistory(String userEmail);
}
