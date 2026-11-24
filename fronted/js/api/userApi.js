import httpClient from "./httpClient.js";

export async function listarUsuarios() {
    return await httpClient.get("/users");
}