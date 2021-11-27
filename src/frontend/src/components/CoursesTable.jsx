import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { DataGrid } from "@mui/x-data-grid";
import { getCourses } from "../services/employee.service";
import { LocaldateFormatter as formatter } from "../helpers/LocaldateFormatter";
import "./CoursesTable.scss";

const CoursesTable = (props) => {
    const employee = props.employee;

    const columns = [
        { field: "name", headerName: "Nazwa", width: 200 },
        {
            field: "description",
            headerName: "Opis",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "hours",
            headerName: "Ilość godzin",
            type: "number",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "startDate",
            headerName: "Data początku",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "endDate",
            headerName: "Data końca",
            flex: 1,
            minWidth: 200,
        },
    ];

    const rows = employee.courses
        ? employee.courses.map(
              ({ id, name, description, hours, startDate, endDate }) => {
                  return {
                      id,
                      name,
                      description,
                      hours,
                      startDate: formatter(startDate),
                      endDate: formatter(endDate),
                  };
              }
          )
        : [];

    return (
        <div className="CoursesTable">
            <div className="table">
                <DataGrid rows={rows} columns={columns} />
            </div>
        </div>
    );
};

export default CoursesTable;
