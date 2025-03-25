package es.uc3m.microblog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 3, max = 64, message = "El nombre debe tener entre 3 y 64 caracteres.")
    private String name;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(min = 3, max = 64, message = "El apellido debe tener entre 3 y 64 caracteres.")
    private String surname;

    @Column(unique = true, nullable = false, length = 64)
    @Email(message = "El correo electrónico no es válido.")
    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    @Size(max = 64, message = "El correo electrónico no puede tener más de 64 caracteres.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;

    // Relación de uno a muchos con los mensajes (si la necesitas, añádela aquí)

    // Getters y Setters

    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
        this.id = id; 
    }

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    @Override
    public String toString() {
        return "User: " + name + " " + surname + " <" + email + ">";
    }
}
