import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Router, Route, Switch, Link } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import { history } from "./helpers/history";
import { logout } from "./actions/auth";
import { clearMessage } from "./actions/message";
import Profile from "./pages/Profile";
import "./App.css";

import { Redirect } from "react-router-dom";

function App() {
    const { user: currentUser } = useSelector((state) => state.auth);
    const { isLoggedIn } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    useEffect(() => {
        history.listen((location) => {
            dispatch(clearMessage()); // clear message when changing location
        });
    }, [dispatch]);

    return (
        <Router history={history}>
            <div className="App">
                {/* {currentUser ? (
                    <div>
                        <li>
                            <Link to={"/profile"}>{currentUser.username}</Link>
                        </li>
                        <li>
                            <a href="/login">LogOut</a>
                        </li>
                    </div>
                ) : (
                    <div>
                        <li>
                            <Link to={"/login"}>Login</Link>
                        </li>
                    </div>
                )} */}

                <Switch>
                    <Route exact path={["/", "/home"]} component={HomePage} />
                    <Route path="/login" component={LoginPage} />
                    <Route path="/profile" component={Profile} />
                </Switch>
            </div>
        </Router>
    );
}

export default App;
