package es.uc3m.microblog.dto;

import java.util.List;

public class PurchaseDto {
    private String purchaseDate;
    private Double totalAmount;
    private List<PurchaseItemDto> items;

    // → Nuevos campos
    private String recipientName;
    private String shippingAddress;

    // Getters / Setters existentes...
    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public List<PurchaseItemDto> getItems() { return items; }
    public void setItems(List<PurchaseItemDto> items) { this.items = items; }

    // → Getters / Setters nuevos
    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
