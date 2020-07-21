window.addEventListener("load", function () {
  fillStatusTable();
  fillProductsTable();
})

function fillStatusTable() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', '../api/reports/orders');
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var orders = JSON.parse(xhr.responseText);
        var ordersTable = document.getElementById("reports-status-table");
        ordersTable.innerHTML = "";
        for (var i = 0; i < orders.length; i++) {
          ordersTable.innerHTML += "<tr><td>" + orders[i].date + "</td><td>" + orders[i].status + "</td><td>" + orders[i].count + "</td><td class=\"text-right\">" + orders[i].sum + "</td></tr>";
        }
      } else {
        console.log('Error: ' + xhr.status);
      }
    }
  };
  xhr.send(null);
}

function fillProductsTable() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', '../api/reports/products');
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var orders = JSON.parse(xhr.responseText);
        var ordersTable = document.getElementById("reports-products-table");
        ordersTable.innerHTML = "";
        for (var i = 0; i < orders.length; i++) {
          ordersTable.innerHTML += "<tr><td>" + orders[i].date + "</td><td class=\"text-left\">" + orders[i].productName + "</td><td>" + orders[i].countOfProducts + "</td><td class=\"text-right\">" + orders[i].price + "</td><td class=\"text-right\">" + orders[i].sum + "</td></tr>";
        }
      } else {
        console.log('Error: ' + xhr.status);
      }
    }
  };
  xhr.send(null);
}