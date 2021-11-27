import LoginPage from "./LoginPage";
import React from "react";
import { Switch, Route, useHistory } from "react-router-dom";
import HomePage from "./HomePage";
import EmployeesPage from "./EmployeesPage";
import EmployeeView from "../components/EmployeeView";
import "./Dashboard.scss";
import Button from "@mui/material/Button";
import PeopleAltIcon from "@mui/icons-material/PeopleAlt";

export const Dashboard = (props) => {
    const history = useHistory();

    const toEmployeesPage = () => {
        history.push("/employees");
    };

    return (
        <div className="Dashboard">
            <header>
                <p>Course Manager</p>
                <div className="buttons">
                    <Button
                        variant="contained"
                        startIcon={<PeopleAltIcon />}
                        style={{
                            backgroundColor: "#f5f551",
                            color: "black",
                            fontWeight: "bold",
                            height: "40px",
                        }}
                        onClick={toEmployeesPage}
                    >
                        Pracownicy
                    </Button>
                </div>
            </header>

            <Switch>
                <Route exact path="/" component={HomePage} />
                <Route path="/home" component={HomePage} />
                <Route path="/login" component={LoginPage} />
                <Route exact path="/employees" component={EmployeesPage} />
                <Route path="/employees/:id" component={EmployeeView} />
            </Switch>
        </div>
    );
};
