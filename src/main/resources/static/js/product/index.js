window.addEventListener('load', function () {
    switchToPage(1, 10);
    var sizeSelect = document.getElementById("sel1");
    sizeSelect.addEventListener("change", function () {
        switchToPage(1, this.value);
        activePage = 1;
    });
});
var activePage = 1;

function fillTable(start, size) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', "/api/products?start=" + start + "&size=" + size);
    xhr.onreadystatechange = function () {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) {
            if (xhr.status === OK) {
                var numberOfPages = JSON.parse(xhr.responseText).totalNumberOfPages;
                var products = JSON.parse(xhr.responseText).page;
                var currentPage = JSON.parse(xhr.response).currentPage;
                createPager(numberOfPages, currentPage);
                var productsTable = document.getElementById("products-table");
                productsTable.innerHTML = "";
                for (var i = 0; i < products.length; i++) {
                    productsTable.innerHTML += "<tr><td>" + products[i].productKey +
                        "</td><td>" + "<a href=/products.html?url=" + products[i].url + ">" + products[i].name + "</a>" +
                        "</td><td>" + products[i].manufacturer +
                        "</td><td>" + products[i].price + "</td></tr>";
                }
            } else {
                console.log('Error: ' + xhr.status);
            }
        }
    };
    xhr.send(null);

}


//function addToBasket(id){
//var xhr = new XMLHttpRequest();
//    xhr.open('POST', "/api/basket?id="+id); // átírni
//    xhr.onreadystatechange = function () {
//        var DONE = 4;
//        var OK = 200;
//        if (xhr.readyState === DONE) {
//            if (xhr.status === OK) {
//                var succes = xhr.responseText;
//                if(succes==false){
//                    alert("failed to create user, item already in basket");
//                }else{
//                    alert("item added to basket");
//                }
//
//            } else {
//                console.log('Error: ' + xhr.status);
//            }
//        }
//    };
//    xhr.send(null);
//
//}


function createPager(numberOfPages, currentPage) {
    var size = document.getElementById("sel1").value;
    var pagerContainer = document.getElementById("pager-container");
    pagerContainer.innerText = "";

    var previous = document.createElement("button");
    previous.setAttribute("class", "btn btn-primary");
    previous.innerText = "Previous";
    if (currentPage == 1) {
        previous.disabled = true;
    }
    previous.addEventListener("click", function () {
        var size = document.getElementById("sel1").value;
        switchToPage(activePage - 1, size);
        activePage--;
    });
    pagerContainer.appendChild(previous);

    for (var i = 1; i <= numberOfPages; i++) {
        var button = document.createElement("button");
        button.innerText = i;
        button.setAttribute("class", "btn btn-primary");
        button.addEventListener("click", function () {
            var size = document.getElementById("sel1").value;
            activePage = parseInt(this.innerText, 10);
            switchToPage(this.innerText, size);
        });
        if (activePage == i) {
            button.style.backgroundColor = "teal";
        }
        pagerContainer.appendChild(button);
    }
    var next = document.createElement("button");
    next.setAttribute("class", "btn btn-primary");
    next.innerText = "Next";
    if (currentPage == numberOfPages) {
        next.disabled = true;
    }
    next.addEventListener("click", function () {
        var size = document.getElementById("sel1").value;
        switchToPage(activePage + 1, size);
        activePage++;
    });
    pagerContainer.appendChild(next);
}

function switchToPage(pageNumber, size) {
    fillTable((pageNumber - 1) * size, size);
}