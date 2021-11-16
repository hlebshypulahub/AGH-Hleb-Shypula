import LoginForm from "../components/LoginForm";
import "./LoginPage.scss";
import staffImg from "../img/staff_bg.jpg";

export const LoginPage = (props) => {
    return (
        <div className="LoginPage">
            {/* <header>
                <h1>Ledikom C. Manager</h1>
            </header> */}

            <div className="container">
                <div className="login-form">
                    <LoginForm />
                </div>
                <div className="staff-img">
                    <img src={staffImg} alt="Staff" />
                </div>
            </div>

            {/* <footer>
                <h1>Praca Inżynierska © Hleb Shypula WFiIS AGH 2021/2022</h1>
            </footer> */}
        </div>
    );
};

export default LoginPage;
