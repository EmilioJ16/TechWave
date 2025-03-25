package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.User;
import es.uc3m.microblog.model.UserRepository;
import es.uc3m.microblog.services.UserService;
import es.uc3m.microblog.services.UserServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Vista principal: muestra mensajes de los usuarios seguidos si hay usuario autenticado, si no, mensajes generales
    @GetMapping("/")
    public String mainView(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = userRepository.findByEmail(principal.getName());
        } else {
        }
        return "index";
    }



    // Vista de perfil de usuario
    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable("userId") int userId, Model model, Principal principal) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        User profileUser = userOpt.get();
        String userHandle = "@" + profileUser.getName().toLowerCase().replace(" ", "");
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("userHandle", userHandle);

        return "profile";
    }


    @GetMapping(path = "/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());  // Añadir un objeto vacío User para el formulario
        return "signup";  // Cargar la vista signup.html
    }

    // Método para procesar el registro de un nuevo usuario
    @PostMapping(path = "/signup")
    public String signUp(@Valid @ModelAttribute("user") User user,
                         BindingResult bindingResult,
                         @RequestParam(name = "passwordRepeat") String passwordRepeat,
                         Model model) {

        // Validar si las contraseñas coinciden
        if (!user.getPassword().equals(passwordRepeat)) {
            bindingResult.rejectValue("password", "password.mismatch", "Las contraseñas no coinciden");
        }

        // Verificar si el correo ya está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "email.exists", "Ya existe una cuenta con este correo electrónico");
        }

        // Si hay errores de validación, volver a mostrar el formulario con los mensajes de error
        if (bindingResult.hasErrors()) {
            return "signup";  // Redirigir de nuevo al formulario
        }

        // Registrar el usuario en el servicio
        userService.register(user);

        // Redirigir a la página de login después de un registro exitoso
        return "redirect:/login?registered";
    }

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";  // Cargar el formulario de login
    }
}
