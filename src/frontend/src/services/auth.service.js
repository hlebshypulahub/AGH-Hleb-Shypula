const API_BASE_URL = "http://localhost:8080/api/auth/";

const login = (username, password) => {
    return fetch(API_BASE_URL + "signin", {
        method: "POST",
        body: JSON.stringify({ username, password }),
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
    }).then((response) =>
        response.json().then((data) => {
            if (data.accessToken) {
                localStorage.setItem("user", JSON.stringify(data));
            }
        })
    );
};

const logout = () => {
    localStorage.removeItem("user");
};

const exportedObject = {
    login,
    logout,
};

export default exportedObject;
