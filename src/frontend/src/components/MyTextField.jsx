import { styled } from "@mui/material/styles";
import { TextField } from "@mui/material";

const MyTextField = styled(TextField)({
    "& label.Mui-focused": {
        color: "black",
    },
    "& .MuiOutlinedInput-root": {
        width: "750px",

        "& fieldset": {
            border: "2px solid #A7A7FF",
        },
        "&:hover fieldset": {
            border: "2px solid #272793",
        },
        "&.Mui-focused fieldset": {
            border: "3px solid #272793",
        },
    },
});

export default MyTextField;
