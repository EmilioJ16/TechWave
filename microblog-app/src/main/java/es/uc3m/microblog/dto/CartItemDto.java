package es.uc3m.microblog.dto;

public class CartItemDto {
    private Integer productId;
    private Integer quantity;

    // Getters y Setters
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
