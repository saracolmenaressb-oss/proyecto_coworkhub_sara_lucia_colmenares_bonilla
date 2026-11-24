
const BASE_URL = "http://localhost:8080";

// 1. Obtener el token almacenado en el navegador
function getToken() {
    return localStorage.getItem("token");
}

// 2. Crear las cabeceras HTTP
function createHeaders(customHeaders = {}) {
    const headers = new Headers(customHeaders);

    headers.set("Accept", "application/json");

    const token = getToken();

    if (token) {
        headers.set("Authorization", `Bearer ${token}`);
    }

    return headers;
}

// 3. Clase personalizada para los errores HTTP
class HttpError extends Error {
    constructor(message, status, codigo, timestamp, details = null) {
        super(message);

        this.name = "HttpError";
        this.status = status;
        this.codigo = codigo;
        this.timestamp = timestamp;
        this.details = details;
    }
}

// 4. Procesar la respuesta del servidor
async function handleResponse(response) {
    const contentType = response.headers.get("content-type") || "";

    let data = null;

    if (response.status !== 204) {
        if (contentType.includes("application/json")) {
            data = await response.json();
        } else {
            const text = await response.text();
            data = text || null;
        }
    }

    // Si la respuesta fue exitosa
    if (response.ok) {
        return data;
    }

    // Obtener el cuerpo del error
    const errorBody = data && typeof data === "object"
        ? data
        : {};

    // Mensajes según el código HTTP
    const mensajes = {
        400: "La solicitud contiene datos incorrectos.",
        401: "No estás autenticado o tu sesión ha expirado.",
        403: "No tienes permisos para realizar esta operación.",
        404: "El recurso solicitado no fue encontrado.",
        409: "La solicitud genera un conflicto con los datos existentes."
    };

    const message =
        errorBody.error ||
        mensajes[response.status] ||
        `Error en la petición HTTP: ${response.status}`;

    // Lanzar el error personalizado
    throw new HttpError(
        message,
        response.status,
        errorBody.codigo || null,
        errorBody.timestamp || null,
        errorBody
    );
}

// 5. Función principal para realizar peticiones
async function request(endpoint, options = {}) {
    const headers = createHeaders(options.headers);

    const config = {
        ...options,
        headers
    };

    // Convertir objetos JavaScript a JSON
    if (
        config.body !== undefined &&
        config.body !== null &&
        !(config.body instanceof FormData) &&
        typeof config.body !== "string"
    ) {
        config.body = JSON.stringify(config.body);
        headers.set("Content-Type", "application/json");
    }

    // Realizar petición al backend
    const response = await fetch(`${BASE_URL}${endpoint}`, config);

    return handleResponse(response);
}

// 6. Métodos HTTP disponibles
const httpClient = {
    get(endpoint, options = {}) {
        return request(endpoint, {
            ...options,
            method: "GET"
        });
    },

    post(endpoint, body, options = {}) {
        return request(endpoint, {
            ...options,
            method: "POST",
            body
        });
    },

    put(endpoint, body, options = {}) {
        return request(endpoint, {
            ...options,
            method: "PUT",
            body
        });
    },

    patch(endpoint, body, options = {}) {
        return request(endpoint, {
            ...options,
            method: "PATCH",
            body
        });
    },

    delete(endpoint, options = {}) {
        return request(endpoint, {
            ...options,
            method: "DELETE"
        });
    }
};

// 7. Exportar el cliente
export default httpClient;
