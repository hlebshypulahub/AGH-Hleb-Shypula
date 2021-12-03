import React, { useState, useEffect, useCallback } from "react";
import MyTextField from "./MyTextField";
import MyDatePicker from "./MyDatePicker";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import validator from "validator";

import { useHistory } from "react-router-dom";

import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";

import { getEducationValues } from "../services/education.service";
import { getEmployeeById, patchEmployeeEducation } from "../services/employee.service";
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
    const [errors, setErrors] = useState({
        eduType: "",
        eduName: "",
        eduGraduationDate: {},
    });

    const history = useHistory();

    useEffect(() => {
        const fetchEducationValues = () => {
            getEducationValues().then((data) => {
                setEduTypes(data);
            });
        };

        fetchEducationValues();
    }, []);

    const validate = useCallback(() => {
        let tempErrors = {};
        tempErrors.eduType = eduType ? "" : "Należy podać rodzaj wykształcenia";
        tempErrors.eduName = eduName ? "" : "Należy podać nazwę szkoły";
        tempErrors.eduGraduationDate = validator.isDate(eduGraduationDate)
            ? ""
            : "Należy podać datę ukończenia stodiów";

        setErrors(tempErrors);

        return Object.values(tempErrors).every((item) => item === "");
    }, [eduType, eduName, eduGraduationDate]);

    useEffect(() => {
        const fetchEmployee = () => {
            getEmployeeById(id).then((data) => {
                setEmployee(data);
                setEduGraduationDate(parse(data.eduGraduationDate));
                setEduName(data.eduName);
                setEduType(data.eduType);
            });
        };

        fetchEmployee("fetchEmployee");
    }, [id]);

    const handleSubmit = useCallback(
        (e) => {
            e.preventDefault();

            if (validate()) {
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

                patchEmployeeEducation(employee.id, patch).then(() => {
                    history.push(`/employees/${employee.id}`);
                });
            } else {
                return;
            }
        },
        [eduName, eduGraduationDate, eduType, employee, history, validate]
    );

    useEffect(() => {
        validate();
    }, [validate]);

    const onChangeEduName = (e) => {
        const newName = e.target.value;
        setEduName(newName);
    };

    const onChangeEduType = (e) => {
        const newEduType = e.target.value;
        setEduType(newEduType);
    };

    const onChangeEduGraduationDate = (newEduGraduationDate) => {
        if (newEduGraduationDate == null || newEduGraduationDate === undefined) {
            setErrors({
                ...errors,
                eduGraduationDate: "Należy podać datę ukończenia stodiów",
            });
        }
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
                                disabled
                                label="Imię i nazwisko"
                                value={
                                    employee.fullName ? employee.fullName : ""
                                }
                            />
                        </div>
                        <div className="input text-field">
                            <MyTextField
                                error={errors.eduType.length > 0}
                                helperText={errors.eduType}
                                select
                                label="Rodzaj"
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
                                error={errors.eduName.length > 0}
                                helperText={errors.eduName}
                                label="Nazwa szkoły"
                                value={eduName ? eduName : ""}
                                onChange={onChangeEduName}
                            />
                        </div>
                        <div className="input text-field">
                            <MyDatePicker
                                error={errors.eduGraduationDate.length > 0}
                                helperText={errors.eduGraduationDate.toString()}
                                label="Data zakończenia"
                                value={eduGraduationDate}
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
                                onClick={() => {
                                    history.goBack();
                                }}
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
