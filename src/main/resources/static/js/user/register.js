window.addEventListener("load", function () {
    var form = document.getElementById("form").onsubmit = registerUser;
    document.getElementById("username-input").addEventListener("keyup", checkUserNameAvability);

})

function checkUserNameAvability() {

    var username = document.getElementById("username-input").value;
    var button = document.getElementById("submit-input");
    var message = document.getElementById("register-message");
    message.classList.remove("text-succes", "text-danger");

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/users?usernamecheck=" + username);
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);

                if (serverResponse.available === true) {
                    message.classList.add("text-success");
                    message.innerText = "Username is available";
                    button.disabled = false;

                } else {
                    message.classList.add("text-danger");
                    message.innerText = "Username is not avalible";
                    button.disabled = true;
                }
                if (username === "") {
                    button.disabled = true;
                    message.innerText = "";
                }
            }
        };

    }
    xhr.send(null);
    return false;

}

function registerUser() {
    var username = document.getElementById("username-input").value;
    var password = document.getElementById("password-input").value;
    var realname = document.getElementById("realname-input").value;

    var createMessage = document.getElementById("create-message");
    createMessage.classList.remove("text-success", "text-danger");

    var button = document.getElementById("submit-input");
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/users");
    xhr.setRequestHeader("Content-type", "application/json");
    var req = {
        "name": username,
        "password": password,
        "realName": realname
    }
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                if (serverResponse.success === true) {
                    var message = document.getElementById("register-message").innerText = "";
                    createMessage.classList.add("text-success");
                    document.getElementById("username-input").value = "";
                    document.getElementById("password-input").value = "";
                    document.getElementById("realname-input").value = "";
                } else {
                    createMessage.classList.add("text-danger");
                }
                createMessage.innerText = serverResponse.message;
            }
        };

    }
    xhr.send(JSON.stringify(req));
    return false;
}