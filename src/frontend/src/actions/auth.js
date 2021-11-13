import { LOGIN_SUCCESS, LOGIN_FAIL, LOGOUT, SET_MESSAGE } from "./types";

import AuthService from "../services/auth.service";

const API_BASE_URL = "http://localhost:8080/api/auth/";

export const login = (username, password) => (dispatch) => {
    return AuthService.login(username, password)
        .then((data) => {
            console.log(1);
            dispatch({
                type: LOGIN_SUCCESS,
                payload: { user: data },
            });

            return Promise.resolve();
        })
        .catch((error) => {
            if (error.message === "401") {
                const message = "Zła nazwa użytkownika lub hasło";

                localStorage.removeItem("user");

                dispatch({
                    type: LOGIN_FAIL,
                });

                dispatch({
                    type: SET_MESSAGE,
                    payload: message,
                });
            }
        });
};

// export const login = (username, password) => (dispatch) => {
//     return AuthService.login(username, password).then(
//         (data) => {
//             console.log(1);
//             dispatch({
//                 type: LOGIN_SUCCESS,
//                 payload: { user: data },
//             });

//             return Promise.resolve();
//         },
//         (error) => {
//             const message =
//                 (error.response &&
//                     error.response.data &&
//                     error.response.data.message) ||
//                 error.message ||
//                 error.toString();

//             console.log(2 + " " + message);

//             dispatch({
//                 type: LOGIN_FAIL,
//             });

//             dispatch({
//                 type: SET_MESSAGE,
//                 payload: message,
//             });

//             return Promise.reject();
//         }
//     );
// };

export const logout = () => (dispatch) => {
    AuthService.logout();

    dispatch({
        type: LOGOUT,
    });
};
