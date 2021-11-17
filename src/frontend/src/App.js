import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import { history } from "./helpers/history";
import { clearMessage } from "./actions/message";
import Profile from "./pages/Profile";
import "./App.css";
import { Dashboard } from "./pages/Dashboard";

function App() {
    const dispatch = useDispatch();

    useEffect(() => {
        history.listen((location) => {
            dispatch(clearMessage()); // clear message when changing location
        });
    }, [dispatch]);

    return (
            <div className="App">
                <Dashboard />
            </div>
    );
}

export default App;
