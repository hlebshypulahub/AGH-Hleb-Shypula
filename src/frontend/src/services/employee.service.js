import authHeader from "./auth-header";

const API_BASE_URL = "http://localhost:8080/api/v1/employees";

export const getEmployees = () => {
    return fetch(API_BASE_URL, {
        method: "GET",
        headers: Object.assign(
            {},
            { "Content-type": "application/json" },
            authHeader()
        ),
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.status, response.message);
            }
        })
        .catch((error) => {
            console.log(error.status + " " + error.message);
        });
};

export const getEmployeeById = (id) => {
    return fetch(API_BASE_URL + "/" + id, {
        method: "GET",
        headers: Object.assign(
            {},
            { "Content-type": "application/json" },
            authHeader()
        ),
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.status, response.message);
            }
        })
        .catch((error) => {
            console.log(error.status + " " + error.message);
        });
};

export const patchEmployeeEducation = (id, patch) => {
    return patchEmployee(id, patch, "/education");
};

export const patchEmployeeCategory = (id, patch) => {
    console.log(patch);
    return patchEmployee(id, patch, "/category");
};

const patchEmployee = (id, patch, path) => {
    return fetch(API_BASE_URL + "/" + id + path, {
        method: "PATCH",
        body: JSON.stringify(patch),
        headers: Object.assign(
            {},
            { "Content-type": "application/merge-patch+json" },
            authHeader()
        ),
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                console.log(response.clone().json());
                throw new Error(response.clone().json());
            }
        })
        .catch((error) => {
            console.log(error);
        });
};
