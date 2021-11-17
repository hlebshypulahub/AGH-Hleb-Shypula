import EmployeesTable from "../components/EmployeesTable";
import { Redirect } from "react-router-dom";
import { useSelector } from "react-redux";

const EmployeesPage = (props) => {
    const { user: currentUser } = useSelector((state) => state.auth);

    if (!currentUser) {
        return <Redirect to="/login" />;
    }

    return (
        <div className="EmployeesPage">
            <EmployeesTable />
        </div>
    );
};

export default EmployeesPage;
