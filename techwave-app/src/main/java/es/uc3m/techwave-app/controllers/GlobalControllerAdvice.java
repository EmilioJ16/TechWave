package es.uc3m.microblog.controllers;

import es.uc3m.microblog.model.User;
import es.uc3m.microblog.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User getCurrentUser(Principal principal) {
        if (principal != null) {
            // Se busca el usuario en base a su email (username)
            return userRepository.findByEmail(principal.getName());
        }
        return null;
    }
}
