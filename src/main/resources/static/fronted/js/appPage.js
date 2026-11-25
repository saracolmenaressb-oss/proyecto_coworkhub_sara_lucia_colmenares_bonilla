// js/appPage.js
import {
    applyRoleVisibility,
    getSessionProfile,
    hasToken,
    logoutAndRedirect,
    refreshSessionProfile,
} from "./auth/session.js";

const usuarioEl = document.getElementById("session-usuario");
const rolEl = document.getElementById("session-rol");
const miembroIdEl = document.getElementById("session-miembro-id");
const welcomeSummaryEl = document.getElementById("welcome-summary");
const viewContainerEl = document.getElementById("view-container");
const pageTitleEl = document.getElementById("page-title");
const appAlertEl = document.getElementById("app-alert");
const logoutBtn = document.getElementById("logout-btn");
const navButtons = document.querySelectorAll(".nav-btn[data-view]");

function showAppError(message, codigo = "") {
    appAlertEl.textContent = codigo ? `${message} (${codigo})` : message;
    appAlertEl.classList.remove("is-hidden");
}

function renderProfile(profile) {
    usuarioEl.textContent = profile.usuario || "-";
    rolEl.textContent = profile.rol || "SIN ROL";
    miembroIdEl.textContent = profile.miembroId ?? "N/A";
    welcomeSummaryEl.textContent =
        `Has iniciado sesión como "${profile.usuario}" con rol ${profile.rol}` +
        (profile.miembroId ? ` (Miembro #${profile.miembroId}).` : ".");
    applyRoleVisibility(profile.rol);
}

function setActiveNav(viewName) {
    navButtons.forEach((btn) => {
        btn.classList.toggle("is-active", btn.getAttribute("data-view") === viewName);
    });
}

function mountPlaceholderView(viewName, label) {
    pageTitleEl.textContent = label;
    setActiveNav(viewName);
    viewContainerEl.innerHTML = `
    <div class="welcome-panel">
      <h2>${label}</h2>
      <p>Contenedor listo para montar la vista de <strong>${viewName}</strong>.</p>
    </div>
  `;
}

async function initApp() {
    if (!hasToken()) {
        logoutAndRedirect("login.html");
        return;
    }

    const cachedProfile = getSessionProfile();
    if (cachedProfile) {
        renderProfile(cachedProfile);
    }

    try {
        const freshProfile = await refreshSessionProfile();
        renderProfile(freshProfile);
    } catch (err) {
        if (err.status === 401) {
            logoutAndRedirect("login.html");
            return;
        }
        showAppError(err.message || "No se pudo cargar el perfil.", err.codigo || "");
    }

    logoutBtn.addEventListener("click", () => {
        logoutAndRedirect("login.html");
    });

    navButtons.forEach((btn) => {
        btn.addEventListener("click", () => {
            const viewName = btn.getAttribute("data-view");
            const label = btn.textContent.trim();
            mountPlaceholderView(viewName, label);
        });
    });
}

initApp();