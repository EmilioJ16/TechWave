// Navbar Scroll
let lastScroll = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    
    if (currentScroll > 100) {
        navbar.classList.add('visible');
    } else {
        navbar.classList.remove('visible');
    }
    
    lastScroll = currentScroll;
});

// Swiper Initialization
const swiper = new Swiper('.swiper-container', {
    effect: 'coverflow',
    grabCursor: true,
    centeredSlides: true,
    loop: true,
    slidesPerView: 'auto',
    autoplay: {
        delay: 5000,
        disableOnInteraction: false,
    },
    coverflowEffect: {
        rotate: 0,
        stretch: 50, // Reducido de 100
        depth: 100, // Reducido de 150
        modifier: 1.5, // Reducido de 2.5
        slideShadows: false,
    },

});

// Intersection Observer
const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if(entry.isIntersecting) {
            entry.target.classList.add('visible');
        }
    });
});

document.querySelectorAll('.reveal').forEach((el) => {
    observer.observe(el);
});

// Smooth Scroll
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = this.getAttribute('href');
        if (target && target !== '#' && document.querySelector(target)) {
            document.querySelector(target).scrollIntoView({
                behavior: 'smooth'
            });
        }
    });
});

document.addEventListener("DOMContentLoaded", () => {
    fetch('/api/purchase/history')
        .then(res => res.json())
        .then(purchases => {
            const historyDiv = document.getElementById('purchase-history');

            if (!purchases.length) {
                historyDiv.innerHTML = "<p>No has realizado ninguna compra todavía.</p>";
                return;
            }
            
            // Calcular total gastado
            const totalGastado = purchases.reduce((sum, p) => sum + p.totalAmount, 0);
            actualizarBarraProgreso(totalGastado);

            const html = purchases.map(p => {
                const fecha = new Date(p.purchaseDate).toLocaleString();
                const total = p.totalAmount.toFixed(2);
                const productos = p.items.map(item =>
                    `<li>${item.productName} × ${item.quantity} = ${(item.priceAtPurchase * item.quantity).toFixed(2)}€</li>`
                ).join('');
                               

                return `
                    <div class="purchase-entry">
                        <p><strong>Fecha:</strong> ${fecha}</p>
                        <ul>${productos}</ul>
                        <p><strong>Total:</strong> ${total}€</p>
                    </div>
                `;
            }).join('');

            historyDiv.innerHTML = html;
        })
        .catch(err => {
            console.error("Error al cargar el historial de compras", err);
            document.getElementById('purchase-history').innerHTML = "<p>Error al cargar el historial.</p>";
            actualizarBarraProgreso(0); // fallback
        });
});


function actualizarBarraProgreso(totalGastado) {
    const barra = document.querySelector('.progress-fill');
    const textoNivel = document.querySelector('.profile-level span');
    const textoPorcentaje = document.querySelector('.progress-text');
    const textoGasto = document.querySelector('.progress-amount');

    let nivel = 0;
    let progreso = 0;

    if (totalGastado >= 10000) {
        nivel = 5;
        progreso = 100;
    } else if (totalGastado >= 5000) {
        nivel = 4;
        progreso = ((totalGastado - 5000) / 5000) * 100;
    } else if (totalGastado >= 1000) {
        nivel = 3;
        progreso = ((totalGastado - 1000) / 4000) * 100;
    } else if (totalGastado >= 500) {
        nivel = 2;
        progreso = ((totalGastado - 500) / 500) * 100;
    } else if (totalGastado >= 100) {
        nivel = 1;
        progreso = ((totalGastado - 100) / 400) * 100;
    } else {
        nivel = 0;
        progreso = (totalGastado / 100) * 100;
    }

    progreso = Math.min(progreso, 100);
    barra.style.width = progreso.toFixed(0) + "%";
    textoNivel.textContent = nivel;
    textoPorcentaje.textContent = progreso.toFixed(0) + "%";
    textoGasto.textContent = `— ${totalGastado.toFixed(2)}€ gastados`;

}
