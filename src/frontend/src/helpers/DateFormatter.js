export const DateFormatter = (date) => {
    if (typeof date === "undefined" || date == null) {
        return null;
    }

    console.log(date.toLocaleDateString());

    return date.toLocaleDateString();
};
