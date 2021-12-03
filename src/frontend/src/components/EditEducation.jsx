import React, { useState, useEffect, useCallback } from "react";
import MyTextField from "./MyTextField";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import validator from "validator";

import { useHistory } from "react-router-dom";

import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";
import DesktopDatePicker from "@mui/lab/DesktopDatePicker";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import AdapterDateFns from "@mui/lab/AdapterDateFns";

import { getEducationValues } from "../services/education.service";
import { getEmployeeById, patchEmployee } from "../services/employee.service";
import { DateParser as parse } from "../helpers/DateParser";
import { DateFormatter as format } from "../helpers/DateFormatter";

import { banana_color, green, red } from "../helpers/color";
import "../css/EditEducation.scss";

const EditEducation = (props) => {
    const id = props.match.params.id;
    const [eduType, setEduType] = useState("");
    const [eduTypes, setEduTypes] = useState([]);
    const [eduName, setEduName] = useState("");
    const [eduGraduationDate, setEduGraduationDate] = useState({});
    const [employee, setEmployee] = useState({});

    const history = useHistory();

    useEffect(() => {
        const fetchEducationValues = () => {
            getEducationValues().then((data) => {
                setEduTypes(data);
            });
        };

        fetchEducationValues();
    }, []);

    useEffect(() => {
        const fetchEmployee = () => {
            getEmployeeById(id).then((data) => {
                setEmployee(data);
            });
        };

        fetchEmployee();

        setEduGraduationDate(parse(employee.eduGraduationDate));
        setEduName(employee.eduName);
        setEduType(employee.eduType);

        console.log(1);
    }, [id, employee.eduGraduationDate, employee.eduName, employee.eduType]);

    const handleSubmit = useCallback(
        (e) => {
            e.preventDefault();

            console.log(eduGraduationDate + " " + eduName + " " + eduType);

            if (
                validator.isDate(eduGraduationDate) &&
                eduName != null &&
                eduType != null
            ) {
                const patch = {
                    eduName,
                    eduGraduationDate: format(eduGraduationDate),
                    education:
                        eduType === "Wyższe"
                            ? "HIGHER"
                            : eduType === "Średnie"
                            ? "SECONDARY"
                            : null,
                };

                patchEmployee(employee.id, patch).then(() => {
                    console.log("employee updated");
                    history.push(`/employees/${employee.id}`);
                });
            } else {
            }
        },
        [eduName, eduGraduationDate, eduType, employee, history]
    );

    const onChangeName = (e) => {
        const name = e.target.value;
        setEduName(name);
    };

    const onChangeEduType = (e) => {
        const eduType = e.target.value;
        setEduType(eduType);
    };

    const onChangeEduGraduationDate = (newEduGraduationDate) => {
        setEduGraduationDate(newEduGraduationDate);
    };

    return (
        <div className="EditEducation">
            <Card className="card">
                <CardContent
                    className="card-content"
                    style={{
                        backgroundColor: banana_color,
                    }}
                >
                    <div className="card-label">
                        <span className="header-label">
                            Edytuj wykształcenie
                        </span>
                    </div>
                    <form className="form" onSubmit={handleSubmit}>
                        <div className="input text-field">
                            <MyTextField
                                id="custom-css-outlined-input"
                                select
                                defaultValue=""
                                label="Rodzaj"
                                variant="outlined"
                                value={eduType ? eduType : ""}
                                onChange={onChangeEduType}
                            >
                                {eduTypes.map((type) => {
                                    return (
                                        <MenuItem
                                            key={type.name}
                                            value={type.label}
                                        >
                                            {type.label}
                                        </MenuItem>
                                    );
                                })}
                            </MyTextField>
                        </div>
                        <div className="input text-field">
                            <MyTextField
                                id="custom-css-outlined-input"
                                className="input"
                                label="Nazwa szkoły"
                                variant="outlined"
                                value={eduName ? eduName : ""}
                                onChange={onChangeName}
                            />
                        </div>
                        <div className="input text-field">
                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                <DesktopDatePicker
                                    label="Data zakończenia"
                                    mask="__.__.____"
                                    inputFormat="dd.MM.yyyy"
                                    value={eduGraduationDate}
                                    onChange={onChangeEduGraduationDate}
                                    renderInput={(params) => (
                                        <MyTextField
                                            {...params}
                                            onFocus={(event) => {
                                                event.target.select();
                                            }}
                                        />
                                    )}
                                />
                            </LocalizationProvider>
                        </div>
                        <div className="buttons">
                            <Button
                                variant="contained"
                                endIcon={<CancelIcon />}
                                style={{
                                    backgroundColor: red,
                                    color: "white",
                                    fontWeight: "bold",
                                    height: "40px",
                                }}
                                onClick={handleSubmit}
                            >
                                Anuluj
                            </Button>
                            <Button
                                variant="contained"
                                endIcon={<CheckCircleIcon />}
                                style={{
                                    marginLeft: "30px",
                                    backgroundColor: green,
                                    color: "white",
                                    fontWeight: "bold",
                                    height: "40px",
                                }}
                                onClick={handleSubmit}
                            >
                                Zatwierdź
                            </Button>
                        </div>
                    </form>
                </CardContent>
            </Card>
        </div>
    );
};

export default EditEducation;
