import React, { Component } from "react";
import { TextField, Button } from "@mui/material";
import "./LoginForm.scss";

class LoginForm extends Component {
    render() {
        return (
            <div className="LoginForm">
                <h3 className="label">A U T O R Y Z A C J A</h3>

                <TextField
                    className="input"
                    id="outlined-basic"
                    label="Nazwa użytkownika"
                    variant="outlined"
                />
                <TextField
                    className="input"
                    id="outlined-password-input"
                    label="Hasło"
                    type="password"
                    autoComplete="current-password"
                />
                <Button className="button" variant="contained" color="success">
                    Zaloguj
                </Button>
            </div>
        );
    }
}

export default LoginForm;
