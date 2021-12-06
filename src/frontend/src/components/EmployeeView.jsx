import React, { useState, useEffect } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Tooltip from "@mui/material/Tooltip";
import Collapse from "@mui/material/Collapse";
import Button from "@mui/material/Button";
import { useHistory } from "react-router-dom";
import AddIcon from "@mui/icons-material/Add";

import { getEmployeeById } from "../services/employee.service";
import CoursesTable from "./CoursesTable";
import { banana_color, green, red, sky_blue } from "../helpers/color";

import "../css/EmployeeView.scss";

const EmployeeView = (props) => {
    const id = props.match.params.id;
    const [employee, setEmployee] = useState({});
    const [shownEducation, setShownEducation] = useState(false);
    const [shownExemption, setShownExemption] = useState(false);

    const history = useHistory();

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
                                className="pin"
                                style={
                                    employee.active
                                        ? {
                                              backgroundColor: green,
                                          }
                                        : {
                                              backgroundColor: red,
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
                                {employee.hiringDate}
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
                        <div className="card-label">
                            <span className="header-label">Kategoria</span>
                            <span
                                className="pin"
                                style={
                                    !employee.category
                                        ? {
                                              backgroundColor: red,
                                          }
                                        : {
                                              backgroundColor: green,
                                          }
                                }
                            >
                                {!employee.category
                                    ? "Należy podać"
                                    : employee.category.label}
                            </span>
                        </div>
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
                                {employee
                                    ? employee.category
                                        ? employee.category.label
                                        : ""
                                    : ""}
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
                                {employee.categoryAssignmentDate}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Termin potwierdzenia:
                            </span>
                            <span className="value-text">
                                {employee.categoryAssignmentDeadlineDate}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Termin dostarczenia dokumentów:
                            </span>
                            <span className="value-text">
                                {employee.docsSubmitDeadlineDate}
                            </span>
                        </div>
                        <div>
                            <span className="label-text-large">
                                Możliwe nadanie kolejnej kategorii:
                            </span>
                            <span className="value-text">
                                {employee.categoryPossiblePromotionDate}
                            </span>
                        </div>
                        <CardActions className="card-actions">
                            <Button
                                variant="outlined"
                                style={{ fontWeight: "bold" }}
                                size="large"
                                onClick={() => {
                                    history.push(
                                        `/employees/${employee.id}/edit-category`
                                    );
                                }}
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
                        <div className="course-hours">
                            <span>{employee.courseHoursSum}</span>
                        </div>
                        <div className="course-label">
                            <span>Suma godzin na kursach</span>
                        </div>
                        <div className="add-course-btn">
                            <Button
                                variant="contained"
                                startIcon={<AddIcon />}
                                style={{
                                    backgroundColor: sky_blue,
                                    color: "black",
                                    fontWeight: "bold",
                                    height: "50px",
                                    width: "200px",
                                }}
                                onClick={() => {
                                    history.push({
                                        pathname: `/employees/${id}/add-course`,
                                        state: { employeeFullName: employee.fullName },
                                    });
                                }}
                            >
                                Dodaj kurs
                            </Button>
                        </div>
                    </CardContent>
                </Card>
            </div>
            <div className="second-row">
                <Tooltip
                    title={shownEducation ? "" : "Kliknuj, aby rozwinąć"}
                    followCursor
                >
                    <Card
                        className="card"
                        onClick={() => setShownEducation(!shownEducation)}
                    >
                        <CardContent
                            className="card-content"
                            style={{
                                backgroundColor: banana_color,
                            }}
                        >
                            <div className="card-label">
                                <span className="header-label">
                                    Wykształcenie
                                </span>
                                <span
                                    className="pin"
                                    style={
                                        !employee.education
                                            ? {
                                                  backgroundColor: red,
                                              }
                                            : {
                                                  backgroundColor: green,
                                              }
                                    }
                                >
                                    {!employee.education
                                        ? "Należy podać"
                                        : employee.education.label}
                                </span>
                            </div>
                            <Collapse in={shownEducation} timeout={700}>
                                <div className="info-row">
                                    <span className="label-text">Rodzaj:</span>
                                    <span className="value-text">
                                        {employee
                                            ? employee.education
                                                ? employee.education.label
                                                : ""
                                            : ""}
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
                                        {employee.eduGraduationDate}
                                    </span>
                                </div>
                                <CardActions className="card-actions">
                                    <Button
                                        variant="outlined"
                                        style={{ fontWeight: "bold" }}
                                        size="large"
                                        onClick={() => {
                                            history.push(
                                                `/employees/${employee.id}/edit-edu`
                                            );
                                        }}
                                    >
                                        Edytuj
                                    </Button>
                                </CardActions>
                            </Collapse>
                        </CardContent>
                    </Card>
                </Tooltip>
                <Tooltip
                    title={
                        shownExemption || !employee.exemptioned
                            ? ""
                            : "Kliknuj, aby rozwinąć"
                    }
                    followCursor
                >
                    <Card
                        className="card"
                        onClick={() => setShownExemption(!shownExemption)}
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
                                    className="pin"
                                    style={
                                        employee.exemptioned
                                            ? {
                                                  backgroundColor: red,
                                              }
                                            : {
                                                  backgroundColor: green,
                                              }
                                    }
                                >
                                    {employee.exemptioned
                                        ? "Zwolniony"
                                        : "Brak"}
                                </span>
                            </div>
                            {employee.exemptioned && (
                                <Collapse in={shownExemption} timeout={700}>
                                    <div className="info-row">
                                        <span className="label-text">
                                            Przyczyna:
                                        </span>
                                        <span className="value-text">
                                            {
                                                employee.certificationExemptionReason
                                            }
                                        </span>
                                    </div>
                                    <div className="info-row">
                                        <span className="label-text">
                                            Data początku:
                                        </span>
                                        <span className="value-text">
                                            {employee.exemptionStartDate}
                                        </span>
                                    </div>
                                    <div className="info-row">
                                        <span className="label-text">
                                            Data zakończenia:
                                        </span>
                                        <span className="value-text">
                                            {employee.exemptionEndDate}
                                        </span>
                                    </div>
                                    <CardActions className="card-actions">
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
                </Tooltip>
            </div>
            <CoursesTable employeeId={id} />
        </div>
    );
};

export default EmployeeView;
