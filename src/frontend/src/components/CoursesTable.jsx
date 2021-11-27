import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { DataGrid } from "@mui/x-data-grid";
import { getCourses } from "../services/employee.service";
import { LocaldateFormatter as formatter } from "../helpers/LocaldateFormatter";
import "./CoursesTable.scss";

const CoursesTable = (props) => {
    const employee = props.employee;

    // const banana_color = "#fafff5";

    const columns = [
        { field: "name", headerName: "Nazwa", width: 160 },
        {
            field: "description",
            headerName: "Opis",
            width: 130,
        },
        {
            field: "hours",
            headerName: "Ilość godzin",
            width: 130,
        },
        {
            field: "startDate",
            headerName: "Data początku",
            width: 130,
        },
        {
            field: "endDate",
            headerName: "Data końca",
            width: 130,
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
                <DataGrid
                    // style={{
                    //     backgroundColor: banana_color,
                    // }}
                    // onRowDoubleClick={(rowData) => {
                    //     history.push("/employees/" + rowData.row.id);
                    // }}
                    rows={rows}
                    columns={columns}
                />
            </div>
        </div>
    );
};

export default CoursesTable;
