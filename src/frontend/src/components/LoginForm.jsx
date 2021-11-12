import React, { useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Redirect } from "react-router-dom";

import { login } from "../actions/auth";

import { TextField, Button } from "@mui/material";
import "./LoginForm.scss";

export const LoginForm = (props) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);

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

    const handleLogin = (e) => {
        e.preventDefault();

        setLoading(true);

        // form.current.validateAll();

        // if (checkBtn.current.context._errors.length === 0) {
        dispatch(login(username, password))
            .then(() => {
                props.history.push("/profile");
                window.location.reload();
            })
            .catch(() => {
                setLoading(false);
            });
        // } else {
        //     setLoading(false);
        // }
    };

    if (isLoggedIn) {
        return <Redirect to="/profile" />;
    }

    return (
        <div className="LoginForm">
            <h3 className="label">A U T O R Y Z A C J A</h3>

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

            {message && (
                <div>
                    <h3>{message}</h3>
                </div>
            )}
        </div>
    );
};

export default LoginForm;
