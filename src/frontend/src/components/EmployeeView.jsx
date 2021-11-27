import React, { useState, useEffect } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
// import CardFooter from "@mui/material/CardFooter";
import Collapse from "@mui/material/Collapse";
import Button from "@mui/material/Button";

import { getEmployeeById } from "../services/employee.service";
import { LocaldateFormatter as formatter } from "../helpers/LocaldateFormatter";
import CoursesTable from "./CoursesTable";

import "./EmployeeView.scss";

const EmployeeView = (props) => {
    const id = props.match.params.id;
    const [employee, setEmployee] = useState({});
    const [shownEducation, setShownEducation] = useState(false);
    const [shownExemption, setShownExemption] = useState(false);

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
                <Card className="card main-info">
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: banana_color,
                        }}
                    >
                        <div className="card-label">
                            <span className="header-label">Dane osobowe</span>
                            <span
                                className="bold-text"
                                style={
                                    employee.active
                                        ? {
                                              backgroundColor: "#3da13f",
                                              borderRadius: "5px",
                                              padding: "5px",
                                              display: "inline-block",
                                          }
                                        : {
                                              backgroundColor: "#ff4747",
                                              borderRadius: "5px",
                                              padding: "5px",
                                              display: "inline-block",
                                          }
                                }
                            >
                                {employee.active ? "Aktywny" : "Nieaktywny"}
                            </span>
                        </div>
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
                            <span className="label-text-large">
                                Kwalifikacja:
                            </span>
                            <span className="value-text">
                                {employee.qualification}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">Kategoria:</span>
                            <span className="value-text">
                                {employee.category}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">Numer:</span>
                            <span className="value-text">
                                {employee.categoryNumber}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Data nadania:
                            </span>
                            <span className="value-text">
                                {formatter(employee.categoryAssignmentDate)}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Termin potwierdzenia:
                            </span>
                            <span className="value-text">
                                {formatter(
                                    employee.categoryAssignmentDeadlineDate
                                )}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Termin dostarczenia dokumentów:
                            </span>
                            <span className="value-text">
                                {formatter(employee.docsSubmitDeadlineDate)}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
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
            <div className="second-row">
                <Card
                    className="card"
                    onMouseEnter={() => setShownEducation(true)}
                    onMouseLeave={() => setShownEducation(false)}
                >
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: banana_color,
                        }}
                    >
                        <span className="header-label">Wykształcenie</span>
                        <Collapse in={shownEducation} timeout={700}>
                            <div className="info-row">
                                <span className="label-text">Rodzaj:</span>
                                <span className="value-text">
                                    {employee.education}
                                </span>
                            </div>
                            <div className="info-row">
                                <span className="label-text">Szkoła:</span>
                                <span className="value-text">
                                    {employee.eduName}
                                </span>
                            </div>
                            <div className="info-row">
                                <span className="label-text">
                                    Data zakończenia:
                                </span>
                                <span className="value-text">
                                    {formatter(employee.eduGraduationDate)}
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
                        </Collapse>
                    </CardContent>
                </Card>
                <Card
                    className="card"
                    onMouseEnter={() => setShownExemption(true)}
                    onMouseLeave={() => setShownExemption(false)}
                >
                    <CardContent
                        className="card-content"
                        style={{
                            backgroundColor: banana_color,
                        }}
                    >
                        <div className="card-label">
                            <span className="header-label">Zwolnienie</span>
                            <span
                                className="bold-text"
                                style={
                                    employee.exemptioned
                                        ? {
                                              backgroundColor: "#ff4747",
                                              borderRadius: "5px",
                                              padding: "5px",
                                              display: "inline-block",
                                          }
                                        : {
                                              backgroundColor: "#3da13f",
                                              borderRadius: "5px",
                                              padding: "5px",
                                              display: "inline-block",
                                          }
                                }
                            >
                                {employee.exemptioned ? "Zwolniony" : "Brak"}
                            </span>
                        </div>
                        {employee.exemptioned && (
                            <Collapse in={shownExemption} timeout={700}>
                                <div className="info-row">
                                    <span className="label-text">
                                        Przyczyna:
                                    </span>
                                    <span className="value-text">
                                        {employee.certificationExemptionReason}
                                    </span>
                                </div>
                                <div className="info-row">
                                    <span className="label-text">
                                        Data początku:
                                    </span>
                                    <span className="value-text">
                                        {formatter(employee.exemptionStartDate)}
                                    </span>
                                </div>
                                <div className="info-row">
                                    <span className="label-text">
                                        Data zakończenia:
                                    </span>
                                    <span className="value-text">
                                        {formatter(employee.exemptionEndDate)}
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
                            </Collapse>
                        )}
                    </CardContent>
                </Card>
            </div>
            <CoursesTable employee={employee} />
        </div>
    );
};

export default EmployeeView;
