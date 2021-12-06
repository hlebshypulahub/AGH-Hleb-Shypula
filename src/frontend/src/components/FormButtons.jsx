import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import CancelIcon from "@mui/icons-material/Cancel";
import { useHistory } from "react-router-dom";
import Button from "@mui/material/Button";
import { green, red } from "../helpers/color";

const FormButtons = (props) => {
    const history = useHistory();

    return (
        <>
            {" "}
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
                type="submit"
            >
                Zatwierdź
            </Button>
        </>
    );
};

export default FormButtons;
