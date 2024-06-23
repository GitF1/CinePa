/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function debounce(cb) {
    let timeout;
    let delay = 1200;

    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => {
            cb(...args);
        }, delay);
    };
}

function callServlet(id, url, methodType) {
    document.getElementById(id).action = url;
    document.getElementById(id).method = methodType;
    document.getElementById(id).submit();
}

function testStyleJS() {
    console.log("Call testStyleJS()");
}
