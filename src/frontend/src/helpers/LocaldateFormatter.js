export const LocaldateFormatter = (localdate) => {
    if (localdate === "") {
        return "";
    }
    var dd = localdate.substring(8, 10);
    var mm = localdate.substring(5, 7);
    var yyyy = localdate.substring(0, 4);

    return dd + "." + mm + "." + yyyy;
}
