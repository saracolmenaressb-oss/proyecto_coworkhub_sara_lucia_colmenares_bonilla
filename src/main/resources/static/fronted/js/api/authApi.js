// js/api/authApi.js
import { apiRequest } from "./httpClient.js";

/**
 * RF-11: Inicia sesión con usuario y contraseña y devuelve el JWT.
 * Si tu backend espera { username, password }, cambia las claves aquí.
 */
export async function login(usuario, contrasena) {
    return apiRequest("/auth/login", {
        method: "POST",
        requiresAuth: false,
        body: {
            usuario,
            contrasena,
        },
    });
}

/**
 * RF-14: Devuelve los datos del usuario autenticado (usuario, rol y miembroId).
 */
export async function getPerfil() {
    return apiRequest("/auth/perfil", {
        method: "GET",
        requiresAuth: true,
    });
}