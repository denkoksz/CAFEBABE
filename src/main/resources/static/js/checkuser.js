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
                var authorities = serverResponse.authorities;
                if (serverResponse.present) {
                    document.getElementById("user-nav").style.visibility = "visible";
                    document.getElementById("user-name").innerText = serverResponse.username;
                    document.getElementById("user-name").setAttribute("href", "/user/basket.html");
                    var regbutton = document.getElementById("register-button");
                    if (regbutton) {
                        regbutton.style.visibility = "hidden";
                    }
                    var loginbutton = document.getElementById("login-button");
                    if (loginbutton) {
                        loginbutton.style.visibility = "hidden";
                    }
                    document.getElementById("log-out").style.visibility = "visible";
                    document.getElementById("log-out").setAttribute("href", "/logout");
                    for (var i = 0; i < authorities.length; i++) {
                        if (authorities[i].authority == "ROLE_ADMIN") {
                            document.getElementById("admin-button").style.visibility = "visible";
                            var basketButton = document.getElementById("add-to-basket-button");
                            document.getElementById("user-name").innerText = "ADMIN-" + serverResponse.username;
                        }
                    }


                }

            };
        };
    }
    xhr.send(null);
}