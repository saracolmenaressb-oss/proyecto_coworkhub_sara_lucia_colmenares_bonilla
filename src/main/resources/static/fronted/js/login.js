// js/login.js
import { login } from "./api/authApi.js";
import {
    hasToken,
    refreshSessionProfile,
    saveSessionProfile,
    saveToken,
} from "./auth/session.js";

const formEl = document.getElementById("login-form");
const usernameEl = document.getElementById("username");
const passwordEl = document.getElementById("password");
const submitBtn = document.getElementById("login-submit");
const alertEl = document.getElementById("login-alert");

function showError(message) {
    alertEl.textContent = message;
    alertEl.classList.remove("is-hidden");
}

function hideError() {
    alertEl.textContent = "";
    alertEl.classList.add("is-hidden");
}

if (hasToken()) {
    window.location.href = "index.html";
}

formEl.addEventListener("submit", async (event) => {
    event.preventDefault();
    hideError();

    const username = usernameEl.value.trim();
    const password = passwordEl.value;

    if (!username || !password) {
        showError("Debes ingresar tu usuario y contraseña.");
        return;
    }

    submitBtn.disabled = true;
    submitBtn.textContent = "Ingresando...";

    try {
        const authData = await login(username, password);
        if (!authData || !authData.token) {
            throw new Error("El servidor no devolvió un token válido.");
        }

        saveToken(authData.token);

        try {
            await refreshSessionProfile();
        } catch {
            saveSessionProfile({
                usuario: authData.username || username,
                rol: authData.role || null,
                miembroId: authData.memberId ?? authData.miembroId ?? null,
            });
        }

        window.location.href = "index.html";
    } catch (err) {
        showError(err.message || "No fue posible iniciar sesión.");
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = "Ingresar";
    }
});