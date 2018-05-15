function getDestAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            // $(".ul-my-addresses").append(obj.getAddressLine())
            alert(obj.getAddressLine())
        }
    });
}