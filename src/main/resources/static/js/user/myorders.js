window.addEventListener("load", function () {
    fillOrders();
})

function fillOrders() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/myorders");
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

    var date = document.createElement("td");
    date.innerText = order.dateTime.replace("T", " ");
    tr.appendChild(date);

    var status = document.createElement("td");
    status.innerText = order.status;
    tr.appendChild(status);

    var sum = document.createElement("td");
    sum.innerText = order.sum;
    sum.classList.add("text-right");
    tr.appendChild(sum);
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
    button.classList.add("btn", "btn-primary");
    button.setAttribute("data-toggle", "collapse");
    button.setAttribute("href", "#collapse" + order.id);
    button.textContent = "Show products";
    button.addEventListener("click", function () {
        this.classList.remove("btn-primary", "btn-secondary");
        if (this.textContent == "Show products") {
            this.textContent = "Hide products";
            this.classList.add("btn-secondary");
        } else {
            this.textContent = "Show products";
            this.classList.add("btn-primary");
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
    table.classList.add("table-sm", "table-hover", "table-small");
    body.appendChild(table);
    //Table head
    var head = document.createElement("thead");
    var headKey = document.createElement("th");
    var headName = document.createElement("th");
    var headMan = document.createElement("th");
    var headPrice = document.createElement("th");
    headKey.innerText = "Key";
    headName.innerText = "Name";
    headMan.innerText = "Manufacturer";
    headPrice.innerText = "Price";
    headPrice.classList.add("text-right");
    head.appendChild(headKey);
    head.appendChild(headName);
    head.appendChild(headMan);
    head.appendChild(headPrice);
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

        tBody.appendChild(sTr);
    }
    return tr;

}