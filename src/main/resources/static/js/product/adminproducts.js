window.onload = function () {
  fillTable();
  document.getElementById("product-form").onsubmit = onSubmitForm;
  document.getElementById("product-form-cancel-button").onclick = resetForm;
};

function deleteProduct(id) {
  var xhr = new XMLHttpRequest();
  xhr.open('DELETE', '/api/products/' + id);
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK && xhr.responseText == "true") {
        console.log("Sikeres törlés");
        fillTable();
      } else {
        console.log('Hiba történt a törlés során.');
      }
    }
  };
  xhr.send();
  return false;
}

function fillTable() {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', '/api/products');
  xhr.onreadystatechange = function () {
    var DONE = 4;
    var OK = 200;
    if (xhr.readyState === DONE) {
      if (xhr.status === OK) {
        var products = JSON.parse(xhr.responseText);
        var productsTable = document.getElementById("product-container");
        productsTable.innerHTML = "";
        for (var i = 0; i < products.length; i++) {
          var tr = document.createElement("tr");
          productsTable.appendChild(tr);
          tr["raw-data"] = products[i];

          var idTd = document.createElement("td");
          idTd.innerHTML = products[i].productKey;
          tr.appendChild(idTd);

          var nameTd = document.createElement("td");
          nameTd.innerHTML = products[i].name;
          tr.appendChild(nameTd);

          var manufacturerTd = document.createElement("td");
          manufacturerTd.innerHTML = products[i].manufacturer;
          tr.appendChild(manufacturerTd);

          var priceTd = document.createElement("td");
          priceTd.innerHTML = products[i].price;
          tr.appendChild(priceTd);

          var buttonsTd = document.createElement("td");
          tr.appendChild(buttonsTd);
          var editButton = document.createElement("button");
          buttonsTd.appendChild(editButton);
          editButton.innerHTML = "Szerkeszt";
          editButton.onclick = function () {
            document.getElementById("product-form-submit-button").value = "Termék módosítása";
            var product = this.parentElement.parentElement["raw-data"];
            document.getElementById("product-id").value = product.id;
            document.getElementById("product-productKey").value = product.productKey;
            document.getElementById("product-name").value = product.name;
            document.getElementById("product-url").value = product.url;
            document.getElementById("product-manufacturer").value = product.manufacturer;
            document.getElementById("product-price").value = product.price;
          };

          var deleteButton = document.createElement("button");
          buttonsTd.appendChild(deleteButton);
          deleteButton.innerHTML = "Törlés";
          deleteButton.onclick = function () {
            if (confirm("Biztos vagy benne, hogy törlöd a terméket?")) {
              var product = this.parentElement.parentElement["raw-data"];
              deleteProduct(product.id);
            }
          };
        }
      } else {
        console.log('Error: ' + xhr.status);
      }
    }
  };
  xhr.send(null);
}

function onSubmitForm() {
    var idElement = document.getElementById("product-id");
    var id = idElement ? parseInt(idElement.value) : null;
    var productKey = document.getElementById("product-productKey").value;
    var name = document.getElementById("product-name").value;
    var url = document.getElementById("product-url").value;
    var manufacturer = document.getElementById("product-manufacturer").value;
    var price = parseInt(document.getElementById("product-price").value);

    var errorElement = document.getElementById("product-errors");
    var errorText = "";
    if(productKey == null || productKey == "" || productKey.length != 8){
        errorText += "A termék azonosítója kötelezően kitöltendő, és pontosan 8 karakter kell, hogy legyen. ";
    }
    if(name == null || name == ""){
        errorText += "A termék neve kötelezően kitöltendő. ";
    }
    if(url == null || url == ""){
        errorText += "Az cím (url) kötelezően kitöltendő. ";
    }
    if(manufacturer == null || manufacturer == ""){
        errorText += "A gyártó neve kötelezően kitöltendő. ";
    }
    if(price == null || price == "" || price <= 0){
        errorText += "A termék ára kötelezően kitöltendő és nullánál nagyobb kell, kogy legyen. ";
    }
    if (errorText) {
        errorElement.innerHTML = errorText;
        window.scrollTo(0,0);
        return false;
    }

    var productRequest = {"productKey":productKey, "name":name, "url":url, "manufacturer":manufacturer, "price":price};

    var xhr = new XMLHttpRequest();
    var requestUrl = id ? '/api/products/' + id : '/api/products';
    xhr.open('POST', requestUrl);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.onreadystatechange = function () {
      var DONE = 4;
      var OK = 200;
      if (xhr.readyState === DONE) {
        if (xhr.status === OK && xhr.responseText == "true") {
            console.log("Sikeres művelet");
            resetForm();
            fillTable();
        } else {
          console.log('Hiba a mentés során');
          document.getElementById("product-errors").innerHTML = "Hiba történt a mentés során"
        }
      }
    };
    xhr.send(JSON.stringify(productRequest));
    return false;
}

function resetForm() {
    document.getElementById("product-form-submit-button").value = "Új termék hozzáadása";
    document.getElementById("product-id").value = null;
    document.getElementById("product-productKey").value = null;
    document.getElementById("product-name").value = null;
    document.getElementById("product-url").value = null;
    document.getElementById("product-manufacturer").value = null;
    document.getElementById("product-price").value = null;
}