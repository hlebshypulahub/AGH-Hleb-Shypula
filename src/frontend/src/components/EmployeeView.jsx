import React, { useState, useEffect } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
// import CardFooter from "@mui/material/CardFooter";
import Button from "@mui/material/Button";

import { getEmployeeById } from "../services/employee.service";
import { LocaldateFormatter as formatter } from "../helpers/LocaldateFormatter";

import "./EmployeeView.scss";

const EmployeeView = (props) => {
    const id = props.match.params.id;
    const [employee, setEmployee] = useState({});

    const banana_color = "#fafff5";

    useEffect(() => {
        const fetchEmployee = () => {
            getEmployeeById(id).then((data) => {
                setEmployee(data);
            });
        };

        fetchEmployee();
    }, [id]);

    return (
        <div className="EmployeeView">
            <div className="first-row">
                <Card className="card">
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: banana_color,
                        }}
                    >
                        <span className="header-label">Dane osobowe</span>
                        <div>
                            <span className="label-text">Imię i nazwisko:</span>
                            <span className="value-text">
                                {employee.fullName}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">
                                Data zatrudnienia:
                            </span>
                            <span className="value-text">
                                {formatter(employee.hiringDate)}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">Miejsce pracy:</span>
                            <span className="value-text">
                                {employee.jobFacility}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">Stanowisko:</span>
                            <span className="value-text">
                                {employee.position}
                            </span>
                        </div>
                    </CardContent>
                </Card>
                <Card className="card">
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: banana_color,
                        }}
                    >
                        <span className="header-label">Kategoria</span>
                        <div>
                            <span className="label-text">Kwalifikacja:</span>
                            <span className="value-text">
                                {employee.qualification}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">Kategoria:</span>
                            <span className="value-text">
                                {employee.category}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">Numer:</span>
                            <span className="value-text">
                                {employee.categoryNumber}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">Data nadania:</span>
                            <span className="value-text">
                                {formatter(employee.categoryAssignmentDate)}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">
                                Termin potwierdzenia:
                            </span>
                            <span className="value-text">
                                {formatter(
                                    employee.categoryAssignmentDeadlineDate
                                )}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">
                                Termin dostarczenia dokumentów:
                            </span>
                            <span className="value-text">
                                {formatter(employee.docsSubmitDeadlineDate)}
                            </span>
                        </div>
                        <div>
                            <span className="label-text">
                                Możliwe nadanie kolejnej kategorii:
                            </span>
                            <span className="value-text">
                                {formatter(
                                    employee.categoryPossiblePromotionDate
                                )}
                            </span>
                        </div>
                        <CardActions
                            style={{
                                paddingTop: "15px",
                                paddingBottom: "0px",
                                paddingLeft: "0px",
                            }}
                        >
                            <Button
                                variant="outlined"
                                style={{ fontWeight: "bold" }}
                                size="large"
                            >
                                Edytuj
                            </Button>
                        </CardActions>
                    </CardContent>
                </Card>
                <Card className="card">
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: "#DA5987",
                        }}
                    >
                        <div className="course-label">
                            <span>Suma godzin na kursach</span>
                        </div>
                        <div className="course-hours">
                            <span>{employee.courseHoursSum}</span>
                        </div>
                    </CardContent>
                </Card>
            </div>
        </div>
    );
};

export default EmployeeView;
