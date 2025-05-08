document.addEventListener("DOMContentLoaded", () => {
    // CSRF Token
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    document.getElementById('form-contact').addEventListener('submit', function(e){
        e.preventDefault();

        const formData = {
            nombre: this.nombre.value,
            email: this.email.value,
            mensaje: this.mensaje.value
        };

        fetch('/api/contacto/enviar', {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
             },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if(data.success){
                Swal.fire('¡Mensaje enviado!', data.message, 'success');
                this.reset();
            }else{
                Swal.fire('Error', data.message, 'error');
            }
        })
        .catch(err => {
            Swal.fire('Error', 'Ocurrió un error inesperado.', 'error');
            console.error(err);
        });
    });
});