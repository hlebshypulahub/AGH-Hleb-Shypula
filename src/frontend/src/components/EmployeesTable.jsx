import React, { useState, useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { getEmployees } from "../services/employee.service";
import { LocaldateFormatter } from "../helpers/LocaldateFormatter";
import "./EmployeesTable.scss";

const EmployeesTable = (props) => {
    const [employees, setEmployees] = useState([]);
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        const fetchEmployees = () => {
            getEmployees().then((data) => {
                setEmployees(data);
            });
        };

        fetchEmployees();
        setLoading(false);
    }, []);

    const columns = [
        { field: "fullName", headerName: "Imię i nazwisko", width: 160 },
        {
            field: "hiringDate",
            headerName: "Data zatrudnienia",
            hide: true,
            width: 130,
        },
        {
            field: "jobFacility",
            headerName: "Miejsce pracy",
            hide: true,
            width: 130,
        },
        {
            field: "position",
            headerName: "Stanowisko",
            width: 130,
        },
        {
            field: "qualification",
            headerName: "Kwalifikacja",
            hide: true,
            width: 130,
        },
        {
            field: "category",
            headerName: "Kategoria",
            width: 100,
        },
        {
            field: "categoryNumber",
            headerName: "Numer kategorii",
            hide: true,
            width: 100,
        },
        {
            field: "categoryAssignmentDate",
            headerName: "Data nadania kategorii",
            type: "date",
            hide: true,
            width: 130,
        },
        {
            field: "categoryAssignmentDeadlineDate",
            headerName: "Termin potwierdzenia kategorii",
            type: "date",
            width: 130,
        },
        {
            field: "docsSubmitDeadlineDate",
            headerName: "Termin dostarczenia dokumentów",
            type: "date",
            width: 130,
        },
        {
            field: "categoryPossiblePromotionDate",
            headerName: "Możliwe nadanie kolejnej kategorii po",
            type: "date",
            hide: true,
            width: 130,
        },
        {
            field: "courseHoursSum",
            headerName: "Suma godzin",
            type: "number",
            width: 100,
        },
        {
            field: "education",
            headerName: "Wykształcenie",
            hide: true,
            width: 100,
        },
        {
            field: "eduName",
            headerName: "Szkoła",
            hide: true,
            width: 150,
        },
        {
            field: "eduGraduationDate",
            headerName: "Data zakończenia studiów",
            hide: true,
            width: 130,
        },
    ];

    const rows = employees
        ? employees.map(
              (
                  {
                      fullName,
                      hiringDate,
                      jobFacility,
                      position,
                      qualification,
                      category,
                      categoryNumber,
                      categoryAssignmentDate,
                      categoryAssignmentDeadlineDate,
                      docsSubmitDeadlineDate,
                      categoryPossiblePromotionDate,
                      courseHoursSum,
                      education,
                      eduName,
                      eduGraduationDate,
                  },
                  index
              ) => {
                  return {
                      id: index,
                      fullName,
                      hiringDate: LocaldateFormatter(hiringDate),
                      jobFacility,
                      position,
                      qualification,
                      category,
                      categoryNumber,
                      categoryAssignmentDate,
                      categoryAssignmentDeadlineDate,
                      docsSubmitDeadlineDate,
                      categoryPossiblePromotionDate,
                      courseHoursSum,
                      education,
                      eduName,
                      eduGraduationDate,
                  };
              }
          )
        : [];

    if (isLoading) {
        return <h1>Loading...</h1>;
    }

    return (
        <div className="EmployeesTable">
            <div className="table">
                <DataGrid rows={rows} columns={columns} />
            </div>
        </div>
    );
};

export default EmployeesTable;
