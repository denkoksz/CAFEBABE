window.addEventListener("load", function () {
    var showall = document.getElementById("showall");
    var showactive = document.getElementById("showactive");
    fillAllOrders();
    showall.addEventListener("click", function () {
        this.classList.remove("btn-light");
        this.classList.add("btn-dark");
        document.getElementById("showactive").classList.remove("btn-dark")
        document.getElementById("showactive").classList.add("btn-light");
        fillAllOrders();
        isAll = true;
    })
    showactive.addEventListener("click", function () {
        this.classList.remove("btn-light");
        this.classList.add("btn-dark");
        document.getElementById("showall").classList.remove("btn-dark")
        document.getElementById("showall").classList.add("btn-light");
        fillActiveOrders();
        isAll = false;
    })
});

var isAll = true;
var targetOrderId = 0;
var targetProductId = 0;

function fillActiveOrders() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/orders/active");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var orders = JSON.parse(xhr.responseText);
                var table = document.getElementById("orders-table");
                table.innerHTML = "";
                for (var i = 0; i < orders.length; i++) {
                    var order = orders[i];
                    table.appendChild(createRow(order));
                }
            } else {
                console.log("ERROR: " + xhr.status);
            }
        }
    }
    xhr.send(null);
}

function fillAllOrders() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/orders");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var orders = JSON.parse(xhr.responseText);
                var table = document.getElementById("orders-table");
                table.innerHTML = "";
                for (var i = 0; i < orders.length; i++) {
                    var order = orders[i];
                    table.appendChild(createRow(order));
                }
            } else {
                console.log("ERROR: " + xhr.status);
            }
        }
    }
    xhr.send(null);
}

function createRow(order) {
    var tr = document.createElement("tr");

    var id = document.createElement("td");
    id.innerText = order.id;
    tr.appendChild(id);

    var username = document.createElement("td");
    username.innerText = order.userName;
    tr.appendChild(username);

    var date = document.createElement("td");
    date.innerText = order.dateTime.replace("T", " ");
    tr.appendChild(date);

    var productnumber = document.createElement("td");
    productnumber.innerText = order.products.length;
    tr.appendChild(productnumber);

    var status = document.createElement("td");
    status.innerText = order.status;
    tr.appendChild(status);

    var sum = document.createElement("td");
    sum.innerText = order.sum;
    sum.classList.add("text-right");
    tr.appendChild(sum);

    var buttons = document.createElement("td");
    buttons.colSpan = "2";
    buttons.id = order.id;
    tr.appendChild(buttons);

    var fulfill = document.createElement("button");
    buttons.appendChild(fulfill);
    fulfill.type = "button";
    fulfill.id = order.id;
    fulfill.textContent = "Fulfill order";
    if (order.status == "ACTIVE") {
        fulfill.classList.add("btn", "btn-info");
        fulfill.setAttribute("data-toggle", "modal");
        fulfill.setAttribute("data-target", "#updateModal");
        fulfill.addEventListener("click", function () {
            document.getElementById("updateModalBackground").classList.remove("bg-succes", "bg-danger")
            fulfillOrder(this.id);
        })
    } else {
        fulfill.disabled = true;
        fulfill.classList.add("btn", "btn-secondary");
    }


    var deleteButton = document.createElement("button");

    buttons.appendChild(deleteButton);
    deleteButton.type = "button";
    deleteButton.textContent = "Delete";
    if (order.status == "ACTIVE") {
        deleteButton.classList.add("btn", "btn-danger");
        deleteButton.setAttribute("data-toggle", "modal");
        deleteButton.setAttribute("data-target", "#deleteModal");
        deleteButton.addEventListener("click", function () {
            var orderId = this.parentNode.id;
            document.getElementById("delete-form-message").innerText = "Are you sure you want to delete the selected order?";
            var modalButton = document.getElementById("deletebutton");
            targetOrderId = orderId;
            modalButton.disabled = false;
            modalButton.onclick = function () {
                deleteOrder(targetOrderId);
            }
            var modal = document.getElementById("deleteModalBackground");
            modal.classList.remove("bg-succes", "bg-danger");
            modal.classList.add("bg-warning");
        });
    } else {
        deleteButton.disabled = true;
        deleteButton.classList.add("btn", "btn-secondary");
    }

    //itt kezdődik a kistable tároló
    var collapsible = document.createElement("td");
    collapsible.id = "collapsible";
    tr.appendChild(collapsible);

    var panel = document.createElement("div");
    panel.classList.add("panel", "panel-default");
    collapsible.appendChild(panel);

    var heading = document.createElement("div");
    heading.classList.add("panel-heading", "text-right");
    panel.appendChild(heading);
    //Gomb
    var button = document.createElement("button");
    button.type = "button";
    button.classList.add("btn", "btn-info");
    button.setAttribute("data-toggle", "collapse");
    button.setAttribute("href", "#collapse" + order.id);
    button.textContent = "Show products";
    button.addEventListener("click", function () {
        this.classList.remove("btn-info", "btn-secondary");
        if (this.textContent == "Show products") {
            this.textContent = "Hide products";
            this.classList.add("btn-secondary");
        } else {
            this.textContent = "Show products";
            this.classList.add("btn-info");
        }
    })
    heading.appendChild(button);



    var collapse = document.createElement("div");
    collapse.id = "collapse" + order.id;
    collapse.classList.add("panel-collapse", "collapse", "in");
    panel.appendChild(collapse);

    var body = document.createElement("div");
    body.classList.add("panel-body");
    collapse.appendChild(body);
    //Table
    var table = document.createElement("table");
    table.classList.add("table-sm", "table-hover", "table-ligth");
    body.appendChild(table);
    //Table head
    var head = document.createElement("thead");
    var headKey = document.createElement("th");
    var headName = document.createElement("th");
    var headMan = document.createElement("th");
    var headPrice = document.createElement("th");
    var headButton = document.createElement("th");
    headKey.innerText = "Key";
    headName.innerText = "Name";
    headMan.innerText = "Manufacturer";
    headPrice.innerText = "Price";
    headPrice.classList.add("text-right");
    headButton.innerText = "Delete";
    head.appendChild(headKey);
    head.appendChild(headName);
    head.appendChild(headMan);
    head.appendChild(headPrice);
    head.appendChild(headButton);
    table.appendChild(head);
    //Table body
    var tBody = document.createElement("tbody");
    table.appendChild(tBody);
    var products = order.products;
    for (var i = 0; i < order.products.length; i++) {
        var product = products[i];

        var sTr = document.createElement("tr");

        var key = document.createElement("td");
        key.innerText = product.productKey;
        sTr.appendChild(key);

        var name = document.createElement("td");
        name.innerText = product.name;
        sTr.appendChild(name);

        var man = document.createElement("td");
        man.innerText = product.manufacturer;
        sTr.appendChild(man);

        var price = document.createElement("td");
        price.innerText = product.price;
        price.classList.add("text-right");
        sTr.appendChild(price);

        var productDeleteButton = document.createElement("button");
        productDeleteButton.id = product.id;
        productDeleteButton.type = "button";
        productDeleteButton.textContent = "Delete";
        if (order.status != "ACTIVE") {
            productDeleteButton.classList.add("btn", "btn-secondary");
            productDeleteButton.disabled = true;
        } else {


            productDeleteButton.classList.add("btn", "btn-danger");
            productDeleteButton.setAttribute("data-toggle", "modal");
            productDeleteButton.setAttribute("data-target", "#deleteModal");
            productDeleteButton.addEventListener("click", function () {
                var productId = this.id;
                document.getElementById("delete-form-message").innerText = "Are you sure you want to delete the selected product?";
                var modalButton = document.getElementById("deletebutton");
                targetProductId = productId;
                targetOrderId = this.parentNode.id;

                modalButton.disabled = false;
                modalButton.setAttribute("targetOrderId", this.id);
                modalButton.setAttribute("targetProductId", productId);
                modalButton.onclick = function () {
                    deleteProductFromOrder(targetOrderId, targetProductId);
                }
                var modal = document.getElementById("deleteModalBackground");
                modal.classList.remove("bg-succes", "bg-danger");
                modal.classList.add("bg-warning");
            })
        }
        sTr.id = order.id;
        sTr.appendChild(productDeleteButton);

        tBody.appendChild(sTr);
    }
    return tr;

}

function deleteOrder(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/orders/" + id);
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var response = JSON.parse(xhr.responseText);
                var modal = document.getElementById("deleteModalBackground")
                modal.classList.remove("bg-warning");
                var title = document.getElementById("delete-form-title");
                var message = document.getElementById("delete-form-message");
                var deleteButton = document.getElementById("deletebutton");
                message.innerText = response.message;


                if (response.success == true) {
                    modal.classList.add("bg-success");
                } else {
                    modal.classList.add("bg-danger");

                }
                if (isAll) {
                    fillAllOrders();
                } else {
                    fillActiveOrders();
                }
                title.innerText = "Result";
                deleteButton.disabled = true;
            } else {
                console.log("ERROR: " + xhr.status);
            }
        }
    }
    xhr.send(null);
}

function deleteProductFromOrder(orderId, productId) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/orders/" + orderId + "/" + productId);
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var response = JSON.parse(xhr.responseText);
                var modal = document.getElementById("deleteModalBackground")
                modal.classList.remove("bg-warning");
                var title = document.getElementById("delete-form-title");
                var message = document.getElementById("delete-form-message");
                var deleteButton = document.getElementById("deletebutton");
                message.innerText = response.message;
                if (response.success == true) {
                    modal.classList.add("bg-success");
                } else {
                    modal.classList.add("bg-danger");

                }
                if (isAll) {
                    fillAllOrders();
                } else {
                    fillActiveOrders();
                }
                title.innerText = "Result";
                deleteButton.disabled = true;
            } else {
                console.log("ERROR: " + xhr.status);
            }
        }
    }
    xhr.send(null);
}

function fulfillOrder(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/orders/" + id + "/status");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var response = JSON.parse(xhr.responseText);
                var modal = document.getElementById("updateModalBackground")
                modal.classList.remove("bg-warning");
                var message = document.getElementById("update-form-message");

                message.innerText = response.message;
                if (response.success == true) {
                    modal.classList.add("bg-success");
                } else {
                    modal.classList.add("bg-danger");

                }
                if (isAll) {
                    fillAllOrders();
                } else {
                    fillActiveOrders();
                }
            } else {
                console.log("ERROR: " + xhr.status);
            }
        }
    }
    xhr.send(null);
}