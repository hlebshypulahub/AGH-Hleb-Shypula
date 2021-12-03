import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { DataGrid } from "@mui/x-data-grid";
import { getEmployees } from "../services/employee.service";
// import { LocaldateFormatter as formatter } from "../helpers/LocaldateFormatter";
import "../css/EmployeesTable.scss";

const EmployeesTable = (props) => {
    const [employees, setEmployees] = useState([]);
    const [isLoading, setLoading] = useState(true);

    const history = useHistory();

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
        { field: "fullName", headerName: "Imię i nazwisko", width: 200 },
        {
            field: "hiringDate",
            headerName: "Data zatrudnienia",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "jobFacility",
            headerName: "Miejsce pracy",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "position",
            headerName: "Stanowisko",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "qualification",
            headerName: "Kwalifikacja",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "category",
            headerName: "Kategoria",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "categoryNumber",
            headerName: "Numer kategorii",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "categoryAssignmentDate",
            headerName: "Data nadania kategorii",
            type: "date",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "categoryAssignmentDeadlineDate",
            headerName: "Termin potwierdzenia kategorii",
            type: "date",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "docsSubmitDeadlineDate",
            headerName: "Termin dostarczenia dokumentów",
            type: "date",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "categoryPossiblePromotionDate",
            headerName: "Możliwe nadanie kolejnej kategorii po",
            type: "date",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "courseHoursSum",
            headerName: "Suma godzin",
            type: "number",
            headerAlign: "left",
            align: "left",
            flex: 1,
            minWidth: 200,
        },
        {
            field: "education",
            headerName: "Wykształcenie",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "eduName",
            headerName: "Szkoła",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
        {
            field: "eduGraduationDate",
            headerName: "Data zakończenia studiów",
            hide: true,
            flex: 1,
            minWidth: 200,
        },
    ];

    const rows = employees
        ? employees.map(
              ({
                  id,
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
              }) => {
                  return {
                      id,
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
                <DataGrid
                    onRowDoubleClick={(rowData) => {
                        history.push("/employees/" + rowData.row.id);
                    }}
                    rows={rows}
                    columns={columns}
                />
            </div>
        </div>
    );
};

export default EmployeesTable;
