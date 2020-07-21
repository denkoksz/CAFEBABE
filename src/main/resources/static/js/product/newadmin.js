window.addEventListener("load", function () {
    filltable();
    document.getElementById("deletebutton").addEventListener("click", function () {
        deleteProduct(this.getAttribute("target"));
    });
    document.getElementById("product-form-open-create").addEventListener("click", function () {
        document.getElementById("product-form-message").innerText = "";
        document.getElementById("product-form-message").classList.remove("text-success", "text-danger", "text-warning");
        document.getElementById("create-product-button").addEventListener("click", createProduct);
        document.getElementById("create-product-button").innerText = "Create product";
        document.getElementById("product-form-title").innerText = "Create product";
    })

    document.getElementById("product-form-close").addEventListener("click", function () {
        document.getElementById("product-form-key").value = "";
        document.getElementById("product-form-name").value = "";
        document.getElementById("product-form-manufacturer").value = "";
        document.getElementById("product-form-URL").value = "";
        document.getElementById("product-form-price").value = "";
        document.getElementById("product-form-message").classList.remove("text-success", "text-danger", "text-warning");
        document.getElementById("create-product-button").removeEventListener("click", createProduct);
        document.getElementById("create-product-button").removeEventListener("click", updateProduct);
    })
})

function filltable() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/api/products");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var products = JSON.parse(xhr.responseText);
                var table = document.getElementById("products-table");
                table.innerHTML = "";
                for (var i = 0; i < products.length; i++) {
                    table.appendChild(createRow(products[i]));
                }

            } else {
                console.log('Error: ' + xhr.status);
            }
        }
    };
    xhr.send(null);
}

function createRow(product) {
    var tr = document.createElement("tr");
    tr.setAttribute("id", product.id);

    var key = document.createElement("td");
    key.innerText = product.productKey;

    var name = document.createElement("td");
    name.innerText = product.name;

    var manufacturer = document.createElement("td");
    manufacturer.innerText = product.manufacturer;

    var url = document.createElement("td");
    url.innerText = product.url;

    var price = document.createElement("td");
    price.innerText = product.price;

    var del = document.createElement("button");
    del.type = "button";
    del.classList.add("btn", "btn-danger");
    del.setAttribute("data-toggle", "modal");
    del.setAttribute("data-target", "#deleteModal");
    del.textContent = "Delete";
    del.addEventListener("click", function () {
        var target = document.createAttribute("target");
        target.value = this.parentNode.id;
        var deleteButton = document.getElementById("deletebutton");
        deleteButton.setAttributeNode(target);
        deleteButton.disabled = false;
        document.getElementById("delete-form-title").innerText = "Alert"
        document.getElementById("delete-form-message").innerText = "Are you sure you want to delete?";
        var modal = document.getElementById("deleteModalBackground");
        modal.classList.remove("bg-succes", "bg-danger");
        modal.classList.add("bg-warning");
    })
    var update = document.createElement("button");
    update.type = "button";
    update.classList.add("btn", "btn-secondary");
    update.setAttribute("data-toggle", "modal");
    update.setAttribute("data-target", "#myModal");
    update.textContent = "Update"
    update.addEventListener("click", function () {
        document.getElementById("create-product-button").addEventListener("click", updateProduct);
        var row = this.parentNode.childNodes;
        document.getElementById("product-form-message").classList.remove("text-success", "text-danger", "text-warning");
        document.getElementById("product-form-message").innerText = "";
        document.getElementById("product-form-key").value = row[0].innerHTML;
        document.getElementById("product-form-name").value = row[1].innerHTML;
        document.getElementById("product-form-manufacturer").value = row[2].innerHTML;
        document.getElementById("product-form-URL").value = row[3].innerHTML;
        document.getElementById("product-form-price").value = row[4].innerHTML;
        var button = document.getElementById("create-product-button");
        button.innerText = "Update product";
        button.addEventListener("click", update);
        var hiddenId = document.createAttribute("hiddenId");
        hiddenId.value = this.parentNode.getAttribute("id");
        button.setAttributeNode(hiddenId);
        document.getElementById("product-form-title").innerText = "Update product";
    })


    tr.appendChild(key);
    tr.appendChild(name);
    tr.appendChild(manufacturer);
    tr.appendChild(url);
    tr.appendChild(price);
    tr.appendChild(update);
    tr.appendChild(del);

    return tr;
}

function deleteProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/api/products/" + id);
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
                    message.innerText = serverResponse.message;
                } else {
                    modal.classList.add("bg-danger");
                    message.innerText = serverResponse.message;
                }
                filltable();
            }
        };

    }
    xhr.send(null);
}

function createProduct() {
    var message = document.getElementById("product-form-message");
    var key = document.getElementById("product-form-key").value;
    var name = document.getElementById("product-form-name").value;
    var manufacturer = document.getElementById("product-form-manufacturer").value;
    var url = document.getElementById("product-form-URL").value;
    var price = document.getElementById("product-form-price").value;
    var req = {
        "productKey": key,
        "name": name,
        "url": url,
        "manufacturer": manufacturer,
        "price": price
    };

    if (key == "" || name == "" || url == "" || manufacturer == "" || price == "") {
        message.classList.add("text-warning");
        message.innerText = "All fields are required!";
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/products");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                message.classList.remove("text-success", "text-danger", "text-warning");
                if (serverResponse.success === true) {
                    message.classList.add("text-success");
                    message.innerText = serverResponse.message;
                    document.getElementById("product-form-key").value = "";
                    document.getElementById("product-form-name").value = "";
                    document.getElementById("product-form-manufacturer").value = "";
                    document.getElementById("product-form-URL").value = "";
                    document.getElementById("product-form-price").value = "";
                } else {
                    message.classList.add("text-danger");
                    message.innerText = serverResponse.message;
                }
                filltable();
            } else {
                console.log('Error: ' + xhr.status);
            }
        }
    }
    xhr.send(JSON.stringify(req));
}

function updateProduct() {
    var id = document.getElementById("create-product-button").getAttribute("hiddenId");
    var key = document.getElementById("product-form-key").value;
    var name = document.getElementById("product-form-name").value;
    var manufacturer = document.getElementById("product-form-manufacturer").value;
    var url = document.getElementById("product-form-URL").value;
    var price = document.getElementById("product-form-price").value;
    var message = document.getElementById("product-form-message");
    var req = {
        "productKey": key,
        "name": name,
        "url": url,
        "manufacturer": manufacturer,
        "price": price
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/api/products/" + id);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                message.classList.remove("text-success", "text-danger", "text-warning");
                if (serverResponse.success === true) {
                    message.classList.add("text-success");
                    message.innerText = serverResponse.message;
                    document.getElementById("product-form-key").value = "";
                    document.getElementById("product-form-name").value = "";
                    document.getElementById("product-form-manufacturer").value = "";
                    document.getElementById("product-form-URL").value = "";
                    document.getElementById("product-form-price").value = "";
                } else {
                    message.classList.add("text-danger");
                    message.innerText = serverResponse.message;
                }
                filltable();

            }
        }

    }
    xhr.send(JSON.stringify(req));

}