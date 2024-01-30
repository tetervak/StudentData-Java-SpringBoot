$(function () {
    // insert the page footer with the formatted current date
    let date = new Date();
    let year = date.getFullYear();
    const months
        = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
    let month = months[date.getMonth()];
    let day = date.getDate();
    $("body").append("<footer></footer>");
    $("footer")
        .html("Sheridan College&nbsp;&nbsp;<span class='hart'>&#9825;</span>&nbsp;&nbsp;")
        .append(`${month} ${day}, ${year}`);
});