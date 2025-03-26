package es.uc3m.microblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto es obligatorio.")
    private String name;

    @Lob
    private String description;

    // Ruta de la imagen (guardada en /public/images/)
    private String image;

    // Relación con Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Relación con Price (histórico de precios)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Price> prices;

    // Método auxiliar para obtener el precio actual
    @Transient
    public Double getCurrentPrice() {
        // Se devuelve el precio con fecha de inicio más reciente (no futura)
        // Este método puede ser más complejo, dependiendo de la lógica de negocio
        return prices.stream()
            .filter(price -> price.getValidFrom().isBefore(java.time.LocalDateTime.now()))
            .sorted((p1, p2) -> p2.getValidFrom().compareTo(p1.getValidFrom()))
            .findFirst()
            .map(Price::getPrice)
            .orElse(0.0);
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<Price> getPrices() { return prices; }
    public void setPrices(List<Price> prices) { this.prices = prices; }
}
