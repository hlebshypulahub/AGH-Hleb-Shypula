import React, { useState, useEffect, useCallback } from "react";
import MyTextField from "./MyTextField";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import validator from "validator";

import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";

import { getEducationValues } from "../services/education.service";
import { getEmployeeById } from "../services/employee.service";

import { banana_color, green, red } from "../helpers/color";
import "../css/EditEducation.scss";

const EditEducation = (props) => {
    const id = props.match.params.id;
    const [eduType, setEduType] = useState("");
    const [eduTypes, setEduTypes] = useState([]);
    const [eduName, setEduName] = useState("");
    const [eduGraduationDate, setEduGraduationDate] = useState("");
    const [employee, setEmployee] = useState({});

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
        const eduGraduationDate = employee.eduGraduationDate;
        setEduGraduationDate(eduGraduationDate);
        const eduName = employee.eduName;
        setEduName(eduName);
        const eduType = employee.eduType;
        setEduType(eduType);
        console.log(1);
    }, [id, employee.eduGraduationDate, employee.eduName, employee.eduType]);

    const handleSubmit = useCallback(
        (e) => {
            e.preventDefault();

            if (validator.isDate(eduGraduationDate)) {
                console.log(eduName + " " + eduGraduationDate + " " + eduType);
            } else {
                console.log("error");
            }
        },
        [eduName, eduGraduationDate, eduType]
    );

    const onChangeName = (e) => {
        const name = e.target.value;
        setEduName(name);
    };

    const onChangeEduType = (e) => {
        const eduType = e.target.value;
        setEduType(eduType);
    };

    const onChangeEduGraduationDate = (e) => {
        const eduGraduationDate = e.target.value;
        setEduGraduationDate(eduGraduationDate);
    };

    // const getEduGraduationDate = () => {
    //     return eduGraduationDate === "" && !employee.eduGraduationDate
    //         ? ""
    //         : eduGraduationDate !== ""
    //         ? eduGraduationDate
    //         : employee.eduGraduationDate
    //         ? employee.eduGraduationDate
    //         : "";
    // };

    // const getEduName = () => {
    //     return eduName === "" && !employee.eduName
    //         ? ""
    //         : eduName !== ""
    //         ? eduName
    //         : employee.eduName
    //         ? employee.eduName
    //         : "";
    // };

    // const getEduType = () => {
    //     return eduType === "" && !employee.eduType
    //         ? ""
    //         : eduType !== ""
    //         ? eduType
    //         : employee.eduType
    //         ? employee.eduType
    //         : "";
    // };

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
                            <MyTextField
                                id="custom-css-outlined-input"
                                label="Data zakończenia"
                                type="date"
                                value={
                                    eduGraduationDate ? eduGraduationDate : ""
                                }
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                onChange={onChangeEduGraduationDate}
                            />
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
