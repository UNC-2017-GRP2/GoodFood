function getUserName(orderId, userId) {
    $.ajax({
        url: 'getUsername',
        type: 'GET',
        data: ({
            userId: userId
        }),
        dataType: "text",
        success: function (data) {
            $("#user"+orderId).text(data);
        },
        error: function () {
            alert("error");
        }
    });
}

function getAddressByCoordinates(orderId, latitude, longitude){
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function(res){
        if (res.geoObjects.get(0) != null){
            var obj = res.geoObjects.get(0);
            $("#address"+orderId).text(obj.getAddressLine());
        }
    });
}

$(document).ready(function () {

});