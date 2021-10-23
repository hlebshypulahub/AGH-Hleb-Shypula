import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import "./App.css";

function App() {
    return (
        <div className="App">
            <Router>
                <Switch>
                    <Route path="/" exact component={HomePage}></Route>
                    <Route path="/login" component={LoginPage}></Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;
