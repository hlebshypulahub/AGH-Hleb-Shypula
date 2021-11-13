import React, { useState, useRef, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Redirect, Route } from "react-router-dom";

import { history } from "../helpers/history";

import { login } from "../actions/auth";

import { TextField, Button } from "@mui/material";
import "./LoginForm.scss";

export const LoginForm = (props) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const { isLoggedIn } = useSelector((state) => state.auth);
    const { message } = useSelector((state) => state.message);

    const dispatch = useDispatch();

    const onChangeUsername = (e) => {
        const username = e.target.value;
        setUsername(username);
    };

    const onChangePassword = (e) => {
        const password = e.target.value;
        setPassword(password);
    };

    const handleLogin = useCallback(
        (e) => {
            e.preventDefault();

            dispatch(login(username, password));
        },
        [dispatch, username, password]
    );

    useEffect(() => {
        const listener = (event) => {
            if (event.code === "Enter" || event.code === "NumpadEnter") {
                event.preventDefault();
                handleLogin(event);
            }
        };
        document.addEventListener("keydown", listener);
        return () => {
            document.removeEventListener("keydown", listener);
        };
    }, [dispatch, username, password, handleLogin]);

    if (isLoggedIn) {
        return <Redirect to="/home" />;
    }

    return (
        <div className="LoginForm">
            <h3 className="label">A U T O R Y Z A C J A</h3>

            {message && (
                <div className="error">
                    <span>{message}</span>
                </div>
            )}

            <TextField
                className="input"
                id="outlined-basic"
                label="Nazwa użytkownika"
                variant="outlined"
                onChange={onChangeUsername}
            />
            <TextField
                className="input"
                id="outlined-password-input"
                label="Hasło"
                type="password"
                autoComplete="current-password"
                onChange={onChangePassword}
            />
            <Button
                className="button"
                variant="contained"
                color="success"
                onClick={handleLogin}
            >
                Zaloguj
            </Button>
        </div>
    );
};

export default LoginForm;
