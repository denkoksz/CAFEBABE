window.addEventListener("load", function () {
  fillTable();
  document.getElementById("update-user-button").addEventListener("click", function () {
    updateUser();
  });
  document.getElementById("deletebutton").addEventListener("click", function () {
    deleteUser();
  });


})
var targetUser = "";

function fillTable() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', '/api/users');
  xhr.setRequestHeader("Content-type", "application/json");
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var users = JSON.parse(xhr.responseText);
        var table = document.getElementById("users-table");
        table.innerText = "";
        for (var i = 0; i < users.length; i++) {
          var user = users[i];
          var username = user.name;
          var realname = user.realName;

          var tr = document.createElement("tr");
          tr.id = username;

          var userNameTd = document.createElement("td");
          userNameTd.innerText = username;

          var realNameTd = document.createElement("td");
          realNameTd.innerText = realname;

          var updateButton = document.createElement("button");
          updateButton.classList.add("btn", "btn-secondary");
          updateButton.type = "button";
          updateButton.textContent = "Update";
          updateButton.setAttribute("data-toggle", "modal");
          updateButton.setAttribute("data-target", "#update-user-modal");
          updateButton.addEventListener("click", function () {
            targetUser = this.parentNode.id;
            document.getElementById("user-form-title").innerText = "Update user: " + targetUser;
            document.getElementById("user-form-password").value = "";
            document.getElementById("user-form-realname").value = "";
          });

          var deleteButton = document.createElement("button");
          deleteButton.classList.add("btn", "btn-danger");
          deleteButton.textContent = "Delete";
          deleteButton.setAttribute("data-toggle", "modal");
          deleteButton.setAttribute("data-target", "#delete-modal");
          deleteButton.addEventListener("click", function () {
            document.getElementById("deletebutton").disabled = false;
            targetUser = this.parentNode.id;
            document.getElementById("delete-form-title").innerText = "Alert"
            document.getElementById("delete-form-message").innerText = "Are you sure you want to delete user: " + targetUser + " ?";
            var modal = document.getElementById("deleteModalBackground");
            modal.classList.remove("bg-succes", "bg-danger");
            modal.classList.add("bg-warning");
          });






          tr.appendChild(userNameTd);
          tr.appendChild(realNameTd);
          tr.appendChild(updateButton);
          tr.appendChild(deleteButton);
          table.appendChild(tr);
        }
      } else {
        console.log('Error: ' + xhr.status);
      }
    }
  };
  xhr.send(null);
}

function updateUser() {
  var xhr = new XMLHttpRequest();
  console.log(targetUser);
  xhr.open("POST", "/api/users/" + targetUser);
  xhr.setRequestHeader("Content-type", "application/json");
  var pass = document.getElementById("user-form-password").value;
  var real = document.getElementById("user-form-realname").value;
  var message = document.getElementById("user-form-message");
  message.classList.remove("text-success", "text-danger", "text-warning");
  var req = {
    password: pass,
    realName: real
  };
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var serverResponse = JSON.parse(xhr.responseText);
        message.innerText = serverResponse.message;
        if (serverResponse.success) {
          document.getElementById("user-form-password").value = "";
          document.getElementById("user-form-realname").value = "";
          message.classList.add("text-success");
        } else {
          message.classList.add("text-danger");
        }
        fillTable();
      }
    }

  }
  xhr.send(JSON.stringify(req));
}

function deleteUser() {
  var xhr = new XMLHttpRequest();
  console.log(targetUser);
  xhr.open("DELETE", "/api/users?username=" + targetUser);
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var serverResponse = JSON.parse(xhr.responseText);
        var modal = document.getElementById("deleteModalBackground")
        modal.classList.remove("bg-warning");
        var title = document.getElementById("delete-form-title");
        var message = document.getElementById("delete-form-message");
        var deleteButton = document.getElementById("deletebutton");
        title.innerText = "Result";
        deleteButton.disabled = true;
        if (serverResponse.success === true) {
          modal.classList.add("bg-success");
          message.innerText = "User successfully deleted";
        } else {
          modal.classList.add("bg-danger");
          message.innerText = "User could not be deleted";
        }
        fillTable();
      }
    }

  }
  xhr.send(null);
}