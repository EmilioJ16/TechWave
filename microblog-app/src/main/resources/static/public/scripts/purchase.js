document.addEventListener("DOMContentLoaded", () => {
    const historyDiv = document.getElementById('purchase-history');
    if (!historyDiv) return; // No está en esta página

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
