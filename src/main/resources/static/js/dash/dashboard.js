window.addEventListener("load", function () {
    fillDash();
})

function fillDash() {



    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/api/dashboard");
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var serverResponse = JSON.parse(xhr.responseText);
                document.getElementById("product-number").innerText = serverResponse.numberOfProducts;
                document.getElementById("active-product-number").innerText = serverResponse.numberOfActiveProducts;
                document.getElementById("order-number").innerText = serverResponse.numberOfOrders;
                document.getElementById("active-order-number").innerText = serverResponse.numberOfActiveOrders;
                document.getElementById("user-number").innerText = serverResponse.numberOfUsers;
            }
        };

    }
    xhr.send(null);


}