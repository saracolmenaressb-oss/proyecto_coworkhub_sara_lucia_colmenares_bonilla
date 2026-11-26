// js/views/bookingsView.js
import { apiRequest } from "../api/httpClient.js";

export async function renderBookingsView(containerEl, onError) {
    containerEl.innerHTML = `
        <div class="welcome-panel">
            <h2>Gestión de reservas</h2>
            <p class="subtitle" style="margin-bottom: 1rem;">Consulta y crea reservas conectadas a /api/bookings.</p>

            <form id="create-booking-form" style="margin-bottom: 1.5rem;">
                <div class="form-group">
                    <label for="booking-start">Fecha y hora inicio</label>
                    <input id="booking-start" type="datetime-local" step="1" required />
                </div>
                <div class="form-group">
                    <label for="booking-end">Fecha y hora fin</label>
                    <input id="booking-end" type="datetime-local" step="1" required />
                </div>
                <div class="form-group">
                    <label for="booking-attendees">Asistentes</label>
                    <input id="booking-attendees" type="number" min="1" value="2" required />
                </div>
                <div class="form-group">
                    <label for="booking-room-id">ID de Sala</label>
                    <input id="booking-room-id" type="number" min="1" value="1" required />
                </div>
                <div class="form-group">
                    <label for="booking-member-id">ID de Miembro</label>
                    <input id="booking-member-id" type="number" min="1" value="1" required />
                </div>
                <button type="submit" class="btn-primary">Crear reserva</button>
            </form>

            <h3>Reservas registradas</h3>
            <pre id="bookings-list" style="margin-top: 0.75rem; white-space: pre-wrap; background: var(--bg-purple-dark); padding: 1rem; border-radius: var(--radius-sm); border: 1px solid var(--border-purple);">Cargando reservas...</pre>
        </div>
    `;

    const formEl = document.getElementById("create-booking-form");
    const listEl = document.getElementById("bookings-list");

    async function loadBookings() {
        try {
            const bookings = await apiRequest("/api/bookings", {
                method: "GET",
                requiresAuth: true,
            });
            listEl.textContent = JSON.stringify(bookings, null, 2);
        } catch (err) {
            listEl.textContent = "No se pudieron cargar las reservas.";
            onError(err.message || "Error al consultar reservas.", err.codigo || "");
        }
    }

    formEl.addEventListener("submit", async (e) => {
        e.preventDefault();
        const startVal = document.getElementById("booking-start").value;
        const endVal = document.getElementById("booking-end").value;

        const payload = {
            startTime: startVal.length === 16 ? `${startVal}:00` : startVal, endTime: endVal.length === 16 ? `${endVal}:00` : endVal,
            attendees: Number(document.getElementById("booking-attendees").value),
            roomId: Number(document.getElementById("booking-room-id").value),
            memberId: Number(document.getElementById("booking-member-id").value),
            items: [],
        };

        try {
            await apiRequest("/api/bookings", {
                method: "POST",
                requiresAuth: true,
                body: payload,
            });
            await loadBookings();
        } catch (err) {
            onError(err.message || "No se pudo crear la reserva.", err.codigo || "");
        }
    });

    await loadBookings();
}