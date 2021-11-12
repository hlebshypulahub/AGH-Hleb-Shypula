import authHeader from "./auth-header";

const API_BASE_URL = "http://localhost:8080/api/user/";

export const getPublicContent = () => {
    return fetch(API_BASE_URL + "all");
};

export const getUserBoard = () => {
    return fetch(API_BASE_URL + "user", { headers: authHeader() });
};

export const getAdminBoard = () => {
    return fetch(API_BASE_URL + "admin", { headers: authHeader() });
};