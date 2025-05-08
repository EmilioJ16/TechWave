package es.uc3m.microblog.controllers;

import es.uc3m.microblog.dto.ContactDto;
import es.uc3m.microblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacto")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<?> sendContact(@RequestBody ContactDto contactDto) {
        
        String subject = "Nuevo Mensaje de " + contactDto.getNombre();
        String message = "Mensaje recibido:\n\n" + contactDto.getMensaje() 
            + "\n\nCorreo de contacto: " + contactDto.getEmail();
        
        // Ahora ya no bloquea, se ejecuta en segundo plano
        emailService.sendSimpleMessage("techwave.eja@gmail.com", subject, message);

        // Respuesta al frontend
        return ResponseEntity.ok().body(
            new ApiResponse(true, "Gracias " + contactDto.getNombre() + ", tu mensaje ha sido enviado correctamente.")
        );
    }

    public static class ApiResponse {
        public boolean success;
        public String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public boolean isSuccess(){return success;}
        public String getMessage(){return message;}
    }
}
