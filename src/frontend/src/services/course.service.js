import authHeader from "./auth-header";

const API_BASE_URL = "http://localhost:8080/api/v1/courses";

export const getEmployeeCourses = (employeeId) => {
    return fetch(API_BASE_URL + "/for-employee/" + employeeId, {
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

export const addCourseToEmployee = (employeeId, course) => {
    return fetch(API_BASE_URL + "/for-employee/" + employeeId, {
        method: "POST",
        body: JSON.stringify(course),
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
