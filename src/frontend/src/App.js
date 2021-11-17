import React, { useEffect } from "react";
import { useDispatch } from "react-redux";
import { BrowserRouter as Router } from "react-router-dom";
import { history } from "./helpers/history";
import { clearMessage } from "./actions/message";
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
            <Router>
                <Dashboard />
            </Router>
        </div>
    );
}

export default App;
