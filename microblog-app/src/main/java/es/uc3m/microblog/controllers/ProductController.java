package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.Product;
import es.uc3m.microblog.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Si en el futuro deseas tener una vista separada para mobiles, puedes mantener este método.
    // Por ahora, al usar MainController para la vista index, este método es opcional.
    @GetMapping("/mobiles")
    public String showMobiles(Model model) {
        List<Product> mobiles = productRepository.findByCategoryName("Mobile");
        model.addAttribute("mobiles", mobiles);
        return "mobiles"; 
    }
}
