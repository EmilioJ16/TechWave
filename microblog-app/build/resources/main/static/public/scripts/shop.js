document.addEventListener("DOMContentLoaded", () => {

    // CSRF Token
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


    // Inicializar Swiper
    var swiper = new Swiper(".mySwiper", {
        slidesPerView: 3,
        spaceBetween: 30,
        loop: true,
        loopFillGroupWithBlack: true,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev"
        }
    });

    // Variables del Carrito
    const totalPrecioElemento = document.getElementById("total-precio");
    const cartPanel = document.getElementById("cart-panel");
    const cartTableBody = document.querySelector("#lista-carrito tbody");
    const vaciarCarritoBtn = document.getElementById("btn-clear-cart");
    const btnCart = document.getElementById("btn-cart");

    cargarEventListeners();

    function cargarEventListeners() {
        // Agregar producto al carrito
        document.querySelectorAll(".add-to-cart").forEach(btn => {
            btn.addEventListener("click", agregarProducto);
        });

        // Vaciar carrito
        if (vaciarCarritoBtn) {
            vaciarCarritoBtn.addEventListener("click", vaciarCarrito);
        }

        // Eliminar producto del carrito
        cartTableBody.addEventListener("click", eliminarElemento);

        // Abrir el panel del carrito
        btnCart.addEventListener("click", (e) => {
            e.preventDefault();
            cartPanel.classList.add("active");
            loadCart();
        });

        // Cerrar panel del carrito
        const btnCloseCart = document.getElementById("btn-close-cart");
        if (btnCloseCart) {
            btnCloseCart.addEventListener("click", () => {
                cartPanel.classList.remove("active");
            });
        }

        // Cargar carrito al iniciar
        loadCart();
    }

    function agregarProducto(e) {
        e.preventDefault();
        const btn = e.target;
        const productId = btn.getAttribute("data-id");
        fetch("/api/cart/add", {
            method: "POST",
            headers: { 
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken
             },
            body: JSON.stringify({ productId: productId, quantity: 1 })
        })
        .then(response => {
            if(response.ok) {
                Swal.fire({
                    title: "Agregado",
                    text: "El producto ha sido añadido al carrito.",
                    icon: "success",
                    showConfirmButton: false,
                    timer: 1500
                });
                loadCart();
            } else {
                Swal.fire("Error", "No se pudo agregar el producto al carrito", "error");
            }
        })
        .catch(error => {
            console.error("Error adding product:", error);
        });
    }

    function eliminarElemento(e) {
        e.preventDefault();
        if(e.target.classList.contains("borrar")) {
            const productId = e.target.getAttribute("data-id");
            fetch(`/api/cart/remove/${productId}`, {
                method: "DELETE",
                headers: {
                    [csrfHeader]: csrfToken
                }
            })
            .then(response => {
                if(response.ok) {
                    Swal.fire({
                        title: "Eliminado",
                        text: "El producto ha sido eliminado del carrito.",
                        icon: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    loadCart();
                } else {
                    Swal.fire("Error", "No se pudo eliminar el producto", "error");
                }
            })
            .catch(error => console.error("Error removing product:", error));
        }
    }

    function vaciarCarrito() {
        Swal.fire({
            title: "¿Estás seguro?",
            text: "El carrito se vaciará por completo.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Sí, vaciar"
        }).then((result) => {
            if(result.isConfirmed) {
                fetch("/api/cart/clear", { 
                    method: "DELETE",
                    headers: {
                        [csrfHeader]: csrfToken
                    } 
                })
                .then(response => {
                    if(response.ok) {
                        Swal.fire("Vaciado!", "El carrito ha sido vaciado.", "success");
                        loadCart();
                    } else {
                        Swal.fire("Error", "No se pudo vaciar el carrito.", "error");
                    }
                })
                .catch(error => console.error("Error clearing cart:", error));
            }
        });
    }

    function loadCart() {
        fetch("/api/cart")
        .then(response => response.json())
        .then(cart => {
            console.log("Carrito recibido del backend:", cart);
            // Actualizar contador del carrito
            document.getElementById("cart-count").innerText = cart.items.length;
            // Vaciar la tabla
            cartTableBody.innerHTML = "";
            let total = 0;
            cart.items.forEach(item => {
                if (item.product && item.product.currentPrice !== undefined) {
                    total += item.product.currentPrice * item.quantity;
                }
                const row = document.createElement("tr");
                if (item.product) {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${item.product.name}</td>
                        <td>$${item.product.currentPrice}</td>
                        <td>${item.quantity}</td>
                        <td>$${(item.product.currentPrice * item.quantity).toFixed(2)}</td>
                        <td><a href="#" class="borrar" data-id="${item.product.id}">X</a></td>
                    `;
                    cartTableBody.appendChild(row);
                }
                cartTableBody.appendChild(row);
            });
            totalPrecioElemento.textContent = `$${total.toFixed(2)}`;
        })
        .catch(error => console.error("Error loading cart:", error));
    }
});
