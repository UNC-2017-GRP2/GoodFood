
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

    /*$(".panel-heading").click(function () {
        var $panelBody = $(this).next(".panel-body");
        if ($panelBody.css("display") == "none"){
            $panelBody.fadeIn(200);
            $(this).children().children(".row-down").css("transform","rotate(180deg)");
           // $panelBody.css("display", "block");
        }else{
            $panelBody.fadeOut(200);
            $(this).children().children(".row-down").css("transform","rotate(0deg)");
           // $panelBody.css("display", "none");
        }
    });*/

});