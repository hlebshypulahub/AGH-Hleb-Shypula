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

// export const updateEmployee = (employee) => {
//     // var path = "";
//     // switch (updateType) {
//     //     case "education":
//     //         path = "/update-education";
//     //         break;
//     //     default:
//     //         path = "";
//     // }

//     return fetch(API_BASE_URL + "/" + employee.id, {
//         method: "PUT",
//         body: JSON.stringify(employee),
//         headers: Object.assign(
//             {},
//             { "Content-type": "application/json" },
//             authHeader()
//         ),
//     })
//         .then((response) => {
//             if (response.ok) {
//                 return response.json();
//             } else {
//                 throw new Error(response.status, response.message);
//             }
//         })
//         .catch((error) => {
//             console.log(error.status + " " + error.message);
//         });
// };

export const patchEmployee = (id, patch) => {
    return fetch(API_BASE_URL + "/" + id, {
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
                return response.text().then((text) => {
                    throw new Error(text);
                });
            }
        })
        .catch((error) => {
            console.log(error);
        });
};
