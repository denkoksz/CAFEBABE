window.addEventListener("load", function () {
    fillTable();
    document.getElementById("clear-basket-button").addEventListener("click", clearBasket);
    var zero = 0;
    document.getElementById("total-price").innerText = zero.toLocaleString();
    document.getElementById("order-button").addEventListener("click", function () {
        order();
    })
})

function fillTable() {

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/basket");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                var products = serverResponse.products;
                for (var i = 0; i < products.length; i++) {
                    var product = products[i];
                    var tr = document.createElement("tr");
                    tr.setAttribute("id", product.id);

                    var key = document.createElement("td");
                    key.innerText = product.productKey;

                    var name = document.createElement("td");
                    name.innerText = product.name;

                    var manufacturer = document.createElement("td");
                    manufacturer.innerText = product.manufacturer;

                    var price = document.createElement("td");
                    price.innerText = product.price.toLocaleString();

                    var deleteButton = document.createElement("button");
                    deleteButton.classList.add("btn", "btn-danger");
                    deleteButton.textContent = "Delete";
                    deleteButton.setAttribute("data-toggle", "modal");
                    deleteButton.setAttribute("data-target", "#deleteModal");
                    deleteButton.addEventListener("click", function () {
                        var hiddenId = document.createAttribute("hiddenid");
                        hiddenId.value = this.parentNode.id;
                        var button = document.getElementById("deletebutton");
                        button.disabled = false;
                        button.setAttributeNode(hiddenId);
                        button.addEventListener("click", function () {
                            deleteFromBasket(this.getAttributeNode("hiddenid").value);
                        });
                        document.getElementById("delete-form-title").innerText = "Alert"
                        document.getElementById("delete-form-message").innerText = "Are you sure you want to delete the item from the basket?";
                        var modal = document.getElementById("deleteModalBackground");
                        modal.classList.remove("bg-succes", "bg-danger");
                        modal.classList.add("bg-warning");
                    });
                    tr.appendChild(key);
                    tr.appendChild(name);
                    tr.appendChild(manufacturer);
                    tr.appendChild(price);
                    tr.appendChild(deleteButton);

                    document.getElementById("products-table").appendChild(tr);

                    document.getElementById("total-price").innerText = serverResponse.sum.toLocaleString();


                }
            };
        };
    }
    xhr.send(null);


}

function clearBasket() {
    var button = document.getElementById("clear-basket-button");
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/basket");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                button.textContent = serverResponse.message;
                button.classList.remove("btn-primary", "btn-success", "btn-warning", "btn-alert");
                if (serverResponse.success) {
                    button.classList.add("btn-success");
                } else {
                    button.classList.add("btn-danger");
                }
                document.getElementById("products-table").innerText = "";
                var zero = 0;
                document.getElementById("total-price").innerText = zero.toLocaleString();
            };
        };
    }
    xhr.send(null);
}

function deleteFromBasket(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/basket/" + id);
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

                if (serverResponse.success) {
                    if (serverResponse.success === true) {
                        modal.classList.add("bg-success");
                        message.innerText = serverResponse.message;
                    } else {
                        modal.classList.add("bg-danger");
                        message.innerText = serverResponse.message;
                    }
                    document.getElementById("products-table").innerText = "";
                    fillTable();
                }
            };
        };
    }
    xhr.send(null);

}

function order() {
    var xhr = new XMLHttpRequest();
    var orderButton = document.getElementById("order-button");
    xhr.open("POST", "/api/basket/checkout");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var response = JSON.parse(xhr.responseText);
                orderButton.classList.remove("btn-primary");
                orderButton.textContent = response.message;
                if (response.success) {
                    orderButton.classList.add("btn-success");
                    var zero = 0;
                    document.getElementById("total-price").innerText = zero.toLocaleString();
                    document.getElementById("products-table").innerText = "";
                    fillTable();
                } else {
                    orderButton.classList.add("btn-danger");
                }
            } else {
                console.log("ERROR: ", xhr.readyState);
            }
        }
    }
    xhr.send(null);
}