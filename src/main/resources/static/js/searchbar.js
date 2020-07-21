window.addEventListener('load', function () {
    console.log("search");
    var searchform = document.getElementById("searchform");
    searchform.onsubmit = function (event) {
        event.preventDefault();
        var searcbox = document.getElementById("searchbox");
        location = "/products.html?url=" + searcbox.value;
        return flase;
    };
})