import React, { Component } from 'react';
import { Redirect } from "react-router-dom";

class HomePage extends Component {
    render() {
        return (
            <div className="HomePage">
                <Redirect to="/login" />
            </div>
        );
    }
}

export default HomePage;