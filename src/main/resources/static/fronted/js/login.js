import { loginRequest, saveSession } from "./api/authApi.js";

const loginForm = document.getElementById("loginForm");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const submitBtn = document.getElementById("submitBtn");
const loginAlert = document.getElementById("loginAlert");

function showAlert(message, type = "error") {
    loginAlert.textContent = message;
    loginAlert.classList.remove("is-hidden", "alert-error", "alert-success");
    loginAlert.classList.add(type === "success" ? "alert-success" : "alert-error");
}

function hideAlert() {
    loginAlert.textContent = "";
    loginAlert.classList.add("is-hidden");
    loginAlert.classList.remove("alert-error", "alert-success");
}

loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    hideAlert();

    const username = usernameInput.value.trim();
    const password = passwordInput.value;

    if (!username || !password) {
        showAlert("Por favor completa el usuario y la contraseña.", "error");
        return;
    }

    submitBtn.disabled = true;
    submitBtn.textContent = "Ingresando...";

    try {
        const authResponse = await loginRequest({ username, password });
        saveSession(authResponse);

        showAlert(`¡Bienvenido, ${authResponse.username}! Redirigiendo...`, "success");

        setTimeout(() => {
            window.location.href = "index.html";
        }, 600);
    } catch (error) {
        console.error("Error de autenticación:", error);
        showAlert(error.message || "No fue posible iniciar sesión.", "error");
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = "Entrar";
    }
});