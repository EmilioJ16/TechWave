package es.uc3m.microblog.dto;

import java.util.List;

public class PurchaseDto {

    private String purchaseDate;
    private double totalAmount;
    private List<PurchaseItemDto> items;

    // Getters y Setters
    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PurchaseItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemDto> items) {
        this.items = items;
    }
}
