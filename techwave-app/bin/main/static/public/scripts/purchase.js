document.addEventListener("DOMContentLoaded", () => {
    const historyDiv = document.getElementById('purchase-history');
    if (!historyDiv) return;

    fetch('/api/purchase/history')
        .then(res => {
            if (!res.ok) throw new Error("Respuesta no válida");
            return res.json();
        })
        .then(purchases => {
            if (!purchases.length) {
                historyDiv.innerHTML = "<p>No has realizado ninguna compra todavía.</p>";
                return;
            }
            console.log(purchases);
            // Renderizamos cada compra con resumen + detalles ocultos
            const html = purchases.map((p, idx) => {
                const fecha = new Date(p.purchaseDate).toLocaleString();
                const total = p.totalAmount.toFixed(2);
                const comprador = p.recipientName;
                const direccion = p.shippingAddress;
                const productos = p.items.map(item =>
                    `<li>${item.productName} × ${item.quantity} = ${(item.priceAtPurchase * item.quantity).toFixed(2)}€</li>`
                ).join('');

                return `
                <div class="purchase-entry">
                  <div class="purchase-summary">
                    <span class="purchase-number">#${idx + 1}</span>
                    <span class="purchase-date">${fecha}</span>
                    <span class="purchase-total">${total}€</span>
                    <button class="view-details-btn">Ver detalles</button>
                  </div>
                  <div class="purchase-details" style="display: none;">
                    <p><strong>Destinatario:</strong> ${comprador}</p>
                    <p><strong>Dirección:</strong> ${direccion}</p>
                    <ul>${productos}</ul>
                  </div>
                </div>`;
            }).join('');

            historyDiv.innerHTML = html;

            // Añadimos comportamiento de toggle a los botones
            historyDiv.querySelectorAll('.view-details-btn').forEach(btn => {
                btn.addEventListener('click', () => {
                    const details = btn.closest('.purchase-entry').querySelector('.purchase-details');
                    const showing = details.style.display === 'block';
                    details.style.display = showing ? 'none' : 'block';
                    btn.textContent = showing ? 'Ver detalles' : 'Ocultar detalles';
                });
            });

            // Calculamos el total gastado para la barra (si lo quieres recalcular aquí)
            const totalGastado = purchases.reduce((sum, p) => sum + p.totalAmount, 0);
            actualizarBarraProgreso(totalGastado);
        })
        .catch(err => {
            console.error("Error al cargar el historial de compras", err);
            historyDiv.innerHTML = "<p>Error al cargar el historial.</p>";
            actualizarBarraProgreso(0);
        });
});

function actualizarBarraProgreso(totalGastado) {
    const barra = document.querySelector('.progress-fill');
    const textoNivel = document.querySelector('.profile-level span');
    const textoPorcentaje = document.querySelector('.progress-text');
    const textoGasto = document.querySelector('.progress-amount');
    if (!barra || !textoNivel || !textoPorcentaje || !textoGasto) return;

    let nivel = 0, progreso = 0;
    if (totalGastado >= 10000) {
        nivel = 5; progreso = 100;
    } else if (totalGastado >= 5000) {
        nivel = 4; progreso = ((totalGastado - 5000) / 5000) * 100;
    } else if (totalGastado >= 1000) {
        nivel = 3; progreso = ((totalGastado - 1000) / 4000) * 100;
    } else if (totalGastado >= 500) {
        nivel = 2; progreso = ((totalGastado - 500) / 500) * 100;
    } else if (totalGastado >= 100) {
        nivel = 1; progreso = ((totalGastado - 100) / 400) * 100;
    } else {
        nivel = 0; progreso = (totalGastado / 100) * 100;
    }
    progreso = Math.min(progreso, 100);

    barra.style.width = progreso.toFixed(0) + "%";
    textoNivel.textContent = nivel;
    textoPorcentaje.textContent = progreso.toFixed(0) + "%";
    textoGasto.textContent = `— ${totalGastado.toFixed(2)}€ gastados`;
}
