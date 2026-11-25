// js/loginPage.js
import { login } from "./api/authApi.js";
import { refreshSessionProfile, saveSession } from "./auth/session.js";

const form = document.getElementById("login-form");
const usuarioInput = document.getElementById("usuario");
const contrasenaInput = document.getElementById("contrasena");
const submitBtn = document.getElementById("login-submit");
const alertBox = document.getElementById("login-alert");

function showError(message, codigo = "") {
    alertBox.textContent = codigo ? `${message} (${codigo})` : message;
    alertBox.classList.remove("is-hidden");
}

function hideError() {
    alertBox.textContent = "";
    alertBox.classList.add("is-hidden");
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    hideError();

    const usuario = usuarioInput.value.trim();
    const contrasena = contrasenaInput.value;

    if (!usuario || !contrasena) {
        showError("Debes ingresar tu usuario y contraseña.", "VALIDATION");
        return;
    }

    submitBtn.disabled = true;
    submitBtn.textContent = "Ingresando...";

    try {
        const authResponse = await login(usuario, contrasena);
        const token = authResponse?.token ?? authResponse?.jwt ?? authResponse?.accessToken;

        if (!token) {
            throw new Error("El servidor no devolvió un token válido.");
        }

        saveSession(token);
        await refreshSessionProfile();
        window.location.href = "index.html";
    } catch (err) {
        showError(err.message || "Credenciales inválidas.", err.codigo || "");
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = "Entrar";
    }
});