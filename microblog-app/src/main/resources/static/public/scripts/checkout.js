document.addEventListener("DOMContentLoaded", () => {
    // CSRF
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    async function cargarCarrito() {
        try {
            const res = await fetch('/api/cart');
            const cart = await res.json();
    
            const resumen = document.getElementById('cart-summary');
            let total = 0;
    
            const lista = cart.items.map(item => {
                const nombre = item.product.name;
                const cantidad = item.quantity;
                const precio = item.product.currentPrice;
    
                const subtotal = precio * cantidad;
                total += subtotal;
    
                return `<li>${nombre} × ${cantidad} = ${subtotal.toFixed(2)}€</li>`;
            }).join('');
    
            resumen.innerHTML = `
                <ul>${lista}</ul>
                <p><strong>Total:</strong> ${total.toFixed(2)}€</p>
            `;
        } catch (error) {
            console.error('Error cargando carrito:', error);
        }
    }
    

    async function confirmarCompra() {
        const name = document.getElementById('recipientName').value;
        const address = document.getElementById('address').value;

        const body = {
            recipientName: name,
            shippingAddress: address
        };

        try {
            const res = await fetch('/api/purchase/checkout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(body)
            });

            if (res.ok) {
                Swal.fire("Compra realizada", "Tu pedido ha sido procesado con éxito", "success")
                    .then(() => window.location.href = "/shop");
            } else {
                Swal.fire("Error", "No se pudo completar la compra", "error");
            }
        } catch (error) {
            console.error('Error al confirmar compra:', error);
        }
    }

    document.getElementById('confirmarCompraBtn').addEventListener('click', confirmarCompra);
    cargarCarrito();
});
