// js/auth/session.js
import { getPerfil } from "../api/authApi.js";
import { getStoredToken, setStoredToken } from "../api/httpClient.js";

const PROFILE_STORAGE_KEY = "coworkhub_profile";

export function hasToken() {
    return Boolean(getStoredToken());
}

export function saveToken(token) {
    setStoredToken(token);
}

export function clearToken() {
    setStoredToken(null);
}

export function normalizeProfile(rawProfile = {}) {
    const rolRaw = rawProfile.rol ?? rawProfile.role ?? null;
    const rol = typeof rolRaw === "string"
        ? rolRaw.replace(/^ROLE_/, "").toUpperCase()
        : null;

    const miembroId =
        rawProfile.miembroId ??
        rawProfile.memberId ??
        rawProfile.member_id ??
        null;

    return {
        usuario: rawProfile.usuario ?? rawProfile.username ?? "",
        rol,
        miembroId,
    };
}

export function saveSessionProfile(profile) {
    const normalized = normalizeProfile(profile);
    localStorage.setItem(PROFILE_STORAGE_KEY, JSON.stringify(normalized));
    return normalized;
}

export function getSessionProfile() {
    const raw = localStorage.getItem(PROFILE_STORAGE_KEY);
    if (!raw) {
        return null;
    }
    try {
        return normalizeProfile(JSON.parse(raw));
    } catch {
        localStorage.removeItem(PROFILE_STORAGE_KEY);
        return null;
    }
}

export function clearSessionProfile() {
    localStorage.removeItem(PROFILE_STORAGE_KEY);
}

export async function refreshSessionProfile() {
    const data = await getPerfil();
    return saveSessionProfile(data);
}

/**
 * Muestra u oculta elementos del DOM que tengan el atributo data-roles="ADMIN,RECEPTIONIST,MEMBER".
 * Si un elemento no tiene data-roles, permanece visible.
 */
export function applyRoleVisibility(role) {
    const normalizedRole = typeof role === "string"
        ? role.replace(/^ROLE_/, "").toUpperCase()
        : "";

    const roleElements = document.querySelectorAll("[data-roles]");
    roleElements.forEach((el) => {
        const allowedRoles = (el.getAttribute("data-roles") || "")
            .split(",")
            .map((r) => r.trim().toUpperCase())
            .filter(Boolean);

        if (allowedRoles.length === 0) {
            el.classList.remove("is-hidden");
            return;
        }

        const isAllowed = Boolean(normalizedRole) && allowedRoles.includes(normalizedRole);
        el.classList.toggle("is-hidden", !isAllowed);
    });
}

export function logoutAndRedirect(loginPage = "login.html") {
    clearToken();
    clearSessionProfile();
    window.location.href = loginPage;
}