// js/api/httpClient.js
const API_BASE_URL = "http://localhost:8080";

export function getStoredToken() {
    return localStorage.getItem("coworkhub_token");
}

export function setStoredToken(token) {
    if (token) {
        localStorage.setItem("coworkhub_token", token);
    } else {
        localStorage.removeItem("coworkhub_token");
    }
}

export async function apiRequest(path, options = {}) {
    const { method = "GET", body = null, requiresAuth = true } = options;
    const headers = {
        "Content-Type": "application/json",
    };

    if (requiresAuth) {
        const token = getStoredToken();
        if (token) {
            headers["Authorization"] = `Bearer ${token}`;
        }
    }

    const response = await fetch(`${API_BASE_URL}${path}`, {
        method,
        headers,
        body: body !== null ? JSON.stringify(body) : null,
    });

    const contentType = response.headers.get("content-type") || "";
    const hasJson = contentType.includes("application/json");
    const data = hasJson ? await response.json() : null;

    if (!response.ok) {
        const error = new Error(
            (data && (data.error || data.message)) || `Error HTTP ${response.status}`
        );
        error.status = response.status;
        error.codigo = (data && data.codigo) || `HTTP_${response.status}`;
        error.timestamp = (data && data.timestamp) || new Date().toISOString();
        throw error;
    }

    return data;
}