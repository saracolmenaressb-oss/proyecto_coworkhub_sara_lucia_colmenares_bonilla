// js/api/authApi.js
import { apiRequest } from "./httpClient.js";

/**
 * RF-11: Inicia sesión con usuario y contraseña y devuelve el JWT y datos de sesión.
 */
export async function login(username, password) {
    return apiRequest("/auth/login", {
        method: "POST",
        requiresAuth: false,
        body: {
            username,
            password,
        },
    });
}

/**
 * RF-14: Devuelve los datos del usuario autenticado (username, role y miembroId si aplica).
 */
export async function getPerfil() {
    return apiRequest("/auth/perfil", {
        method: "GET",
        requiresAuth: true,
    });
}