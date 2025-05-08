package es.uc3m.microblog.dto;

public class CheckoutDto {
    private String recipientName;
    private String shippingAddress;

    // Getters y Setters
    public String getRecipientName() {
        return recipientName;
    }
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
