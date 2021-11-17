const API_BASE_URL = "http://localhost:8080/api/v1";

export const getEmployees = () => {
    return fetch(API_BASE_URL + "/employees")
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
