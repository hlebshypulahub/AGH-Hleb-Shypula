import authHeader from "./auth-header";

const API_BASE_URL = "http://localhost:8080/api/v1";

export const getEmployees = () => {
    return fetch(API_BASE_URL + "/employees", {
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
