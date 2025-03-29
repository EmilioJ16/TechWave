package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.User;
import es.uc3m.microblog.model.UserRepository;
import es.uc3m.microblog.model.Product;
import es.uc3m.microblog.model.ProductRepository;
import es.uc3m.microblog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductRepository productRepository;

    // Vista principal: muestra los datos generales y los móviles destacados (categoria "Mobile")
    @GetMapping("/")
    public String mainView(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userRepository.findByEmail(principal.getName());
            model.addAttribute("currentUser", currentUser);
            // Se pueden agregar más atributos para usuarios autenticados


        } else {
            // Datos generales para usuarios no autenticados
        }
        
        List<Product> mobiles = productRepository.findByCategoryName("Mobile");
    System.out.println("Found " + mobiles.size() + " mobile products.");
    model.addAttribute("mobiles", mobiles);

        
        return "index";  // Retorna la vista index.html
    }

    // Vista de perfil de usuario
    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable("userId") int userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User profileUser = userOpt.get();

        // En el futuro, podrías:
        // profileUser.setLevel(userService.calculateLevel(profileUser));
        // profileUser.setPoints(userService.getPoints(profileUser));
        // ...

        String userHandle = "@" + profileUser.getName().toLowerCase().replace(" ", "");
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("userHandle", userHandle);

        return "profile"; // la vista con la maqueta anterior
        }

    // Formulario de registro de usuario
    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Procesa el registro de un nuevo usuario
    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         @RequestParam(name = "passwordRepeat") String passwordRepeat,
                         Model model) {

        // Validar si las contraseñas coinciden
        if (!user.getPassword().equals(passwordRepeat)) {
            bindingResult.rejectValue("password", "password.mismatch", "Passwords do not match");
        }

        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.exists", "An account with this email already exists");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.register(user);
        return "redirect:/login?registered";
    }

    // Formulario de inicio de sesión
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
