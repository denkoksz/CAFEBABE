window.addEventListener("load", function () {
    checkUser();
})

function checkUser() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/users/current");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                var username = serverResponse.username;
                if (serverResponse.present) {
                    document.getElementById("dash").innerText = "Dashboard - " + username;
                }
            } else {
                console.log("ERROR:" + xhr.status);
            }
        };
    }
    xhr.send(null);
}