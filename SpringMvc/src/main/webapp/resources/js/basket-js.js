
function addressSelection(address) {
     $("#input-address").val(address);
    $(".to-order-btn").prop('disabled',false);
    $("#address-valid").text("");
    geocode(address);
}
function disabledInputAddress() {
    $("#input-address").prop('disabled',true);
}

function geocode(address) {
    ymaps.geocode(address).then(function (res) {
        var geoAddress;
        var  error;
        if (res.geoObjects.get(0) != null) {
            geoAddress = res.geoObjects.get(0);
            var  coords = geoAddress.geometry.getCoordinates();
            switch (geoAddress.properties.get('metaDataProperty.GeocoderMetaData.precision')) {
                case 'exact':
                    break;
                case 'number':
                case 'near':
                case 'range':
                    error = 'Inaccurate address, clarification required.';
                    break;
                case 'street':
                    error = 'Inaccurate address, clarification required.';
                    break;
                case 'other':
                default:
                    error = 'Inaccurate address, clarification required.';
            }
        } else {
            error = 'Address not found';
        }
        if (error) {
            $("#address-valid").text(error);
            $(".to-order-btn").prop('disabled',true);

        }else{
            $(".to-order-btn").prop('disabled',false);
            $("#address-valid").text("");
            $("#input-address-latitude").val(coords[0]);
            $("#input-address-longitude").val(coords[1]);
            alert($("#input-address-latitude").val() + " " + $("#input-address-longitude").val());
        }
    });
}

function getAddressByCoordinates(latitude, longitude){
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function(res){
        if (res.geoObjects.get(0) != null){
            var obj = res.geoObjects.get(0);
            $(".ul-my-addresses").append("<li class=\"list-group-item list-group-item-address\" onclick=\"addressSelection('" +obj.getAddressLine() +  "');\">" + obj.getAddressLine() + "</li>")
        }
    });
}


$(document).ready(function() {

    ymaps.ready(initAddressList);

    function initAddressList() {
        var addressList = new ymaps.SuggestView('input-address');
    }

    $('.minus').click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) - 1;
        var previousCount = parseInt($input.val());
        count = count < 1 ? 1 : count;
        $input.val(count);
        $input.change();
        var itemId = $(this).attr("item-id");

        var itemCost = $(this).attr("item-cost");
        var newValue = count*itemCost;
        var $final = $(this).parent().parent().parent().parent().parent().find('.final-item-cost-span');
        $final.text(newValue);

        var $totalCostElem = $(".total-order-cost");
        var previousItemsCost = previousCount*itemCost;
        var totalCost = parseInt($totalCostElem.text());
        totalCost = totalCost-previousItemsCost+newValue;
        $totalCostElem.text(totalCost);

        $.ajax({
            url : 'updateBasket',
            type: 'GET',
            /*contentType: 'application/json',
            mimeType: 'application/json',*/
            data : ({
                itemId: itemId,
                newQuantity: count
            }),
            success: function () {
                //alert(data);
            }
        });
        return false;
    });

    $('.plus').click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) + 1;
        var previousCount = parseInt($input.val());
        $input.val(count);
        $input.change();
        var itemId = $(this).attr("item-id");

        var itemCost = $(this).attr("item-cost");
        var newValue = count*itemCost;
        var $final = $(this).parent().parent().parent().parent().parent().find('.final-item-cost-span');
        $final.text(newValue);

        var $totalCostElem = $(".total-order-cost");
        var previousItemsCost = previousCount*itemCost;
        var totalCost = parseInt($totalCostElem.text());
        totalCost = totalCost-previousItemsCost+newValue;
        $totalCostElem.text(totalCost);

        $.ajax({
            url : 'updateBasket',
            type: 'GET',
            /*contentType: 'application/json',
            mimeType: 'application/json',*/
            data : ({
                itemId: itemId,
                newQuantity: count
            }),
            success: function () {
                //alert(data);
            }
        });


        /*$.getJSON("updateBasket", { itemId: itemId})
            .done(function (data) {
                alert(data);
            })
            .fail(function () {
                alert("Error GetUsersRoute");
            });*/
        return false;
    });

    $('.remove-item').click(function () {
        $(this).parent().parent().remove();
        var itemId = $(this).attr("item-id");
        $.ajax({
            url : 'removeItem',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            data : ({
                itemId: itemId
            }),
            success: function (data) {
                $(".total-order-cost").text(data);
                if (data == "0"){
                    $("#input-address").val("");
                    $("#input-address").prop('disabled',true);
                    $(".to-order-btn").prop('disabled',true);
                    $("#address-valid").text("");
                }
            }
        });

        $.ajax({
            url : 'isBasketEmpty',
            type: 'GET',
            contentType: 'application/json',
            mimeType: 'application/json',
            success: function (data) {
                /*alert(data);
                if (data == "true"){
                    alert("true");
                    $('.to-order').prop("disabled", false);
                }else{
                    alert("false");
                }*/
            }
        });
    });

    $("#input-address").keyup(function(){
        var address = $("#input-address").val();
        if (address != ""){
            $(".ul-my-addresses").css('visibility', 'hidden');
            $(".ul-my-addresses").css('height', '0');
        }
        geocode(address);
        /*var value = $("#input-address").val();
        if (value == ""){
            $(".ul-my-addresses").css('visibility', 'visible');
            $(".ul-my-addresses").css('height', 'auto');
            $("#address-valid").text("Address field must not be empty.");
            $(".to-order-btn").prop('disabled',true);

        }else{
            $(".ul-my-addresses").css('visibility', 'hidden');
            $(".ul-my-addresses").css('height', '0');
            //$("#address-valid").text("");
            geocode(value);
        }*/
    });

    $("#input-address").focus(function () {
        var value = $("#input-address").val();
        if (value == ""){
            $(".ul-my-addresses").css('visibility', 'visible');
            $(".ul-my-addresses").css('height', 'auto');
        }
    });
    $("#input-address").blur(function () {
        setTimeout(function () {
            $(".ul-my-addresses").css('visibility', 'hidden');
            $(".ul-my-addresses").css('height', '0');
        },200);
    });
});