var destAddressFlag = false, addressFlag = false;

var style;
var form;
var firstMap,
    secondMap,
    firstMarker,
    secondMarker;

function addressSelection(address) {
    $("#input-address").val(address);
    geocode(address, false);
}

function destAddressSelection(address) {
    $("#input-dest-address").val(address);
    destGeocode(address, false);
}

function geocode(address, isClick) {
    ymaps.geocode(address).then(function (res) {
        var geoAddress;
        var error;
        if (res.geoObjects.get(0) != null) {
            geoAddress = res.geoObjects.get(0);
            var coords = geoAddress.geometry.getCoordinates();
            switch (geoAddress.properties.get('metaDataProperty.GeocoderMetaData.precision')) {
                case 'exact':
                    break;
                case 'number':
                case 'near':
                case 'range':
                    error = getErrorString('clarify_address');
                    break;
                case 'street':
                    error = getErrorString('clarify_address');
                    break;
                case 'other':
                default:
                    error = getErrorString('clarify_address');
            }
        } else {
            error = getErrorString('address_is_not_found');
        }
        if (error) {
            addressFlag = false;
            setErrorValidMessage($("#input-address"), $("#address-valid"), error);

        } else {
            if (isClick){
                $("#input-address").val(geoAddress.getAddressLine());
            }
            //var position = geoAddress.geometry.getCoordinates();
            firstMarker.geometry.setCoordinates(coords);
            firstMap.geoObjects.add(firstMarker);
            firstMap.setCenter(coords, firstMap.getZoom(), { duration: 300 });
            addressFlag = true;
            setSuccessValid($("#input-address"), $("#address-valid"));
            $("#input-address-latitude").val(coords[0]);
            $("#input-address-longitude").val(coords[1]);
        }
    });
}

function destGeocode(address, isClick) {
    ymaps.geocode(address).then(function (res) {
        var geoAddress;
        var error;
        if (res.geoObjects.get(0) != null) {
            geoAddress = res.geoObjects.get(0);
            var coords = geoAddress.geometry.getCoordinates();
            switch (geoAddress.properties.get('metaDataProperty.GeocoderMetaData.precision')) {
                case 'exact':
                    break;
                case 'number':
                case 'near':
                case 'range':
                    error = getErrorString('clarify_address');
                    break;
                case 'street':
                    error = getErrorString('clarify_address');
                    break;
                case 'other':
                default:
                    error = getErrorString('clarify_address');
            }
        } else {
            error = getErrorString('address_is_not_found');
        }
        if (error) {
            destAddressFlag = false;
            setErrorValidMessage($("#input-dest-address"), $("#address-dest-valid"), error);

        } else {
            if (isClick){
                $("#input-dest-address").val(geoAddress.getAddressLine());
            }
            //var position = geoAddress.geometry.getCoordinates();
            secondMarker.geometry.setCoordinates(coords);
            secondMap.geoObjects.add(secondMarker);
            secondMap.setCenter(coords, secondMap.getZoom(), { duration: 300 });
            destAddressFlag = true;
            setSuccessValid($("#input-dest-address"), $("#address-dest-valid"));
            $("#input-address-dest-latitude").val(coords[0]);
            $("#input-address-dest-longitude").val(coords[1]);
        }
    });
}

function getAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            $(".ul-my-addresses").append("<li class=\"list-group-item list-group-item-address\" onclick=\"addressSelection('" + obj.getAddressLine() + "');\">" + obj.getAddressLine() + "</li>")
        }
    });
}

function getDestAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            $(".ul-my-addresses").append("<li class=\"list-group-item list-group-item-address\" onclick=\"destAddressSelection('" + obj.getAddressLine() + "');\">" + obj.getAddressLine() + "</li>")
        }
    });
}

function checkAllFieldsForCheckout() {
    if (addressFlag && destAddressFlag) {
        $(".to-order-btn").prop('disabled', false);
    } else {
        $(".to-order-btn").prop('disabled', true);
    }
}

function setErrorValidMessage(input, validMessageDiv, message) {
    $(input).css("border", "0.5px solid #d9534f");
    $(validMessageDiv).text(message);
    $(validMessageDiv).css("display", "block");
    checkAllFieldsForCheckout();
}

function setSuccessValid(input, validMessageDiv) {
    $(input).css("border", "0.5px solid #5cb85c");
    $(validMessageDiv).css("display", "none");
    checkAllFieldsForCheckout();
}

function setPhoneValue(phoneValue) {
    if (phoneValue != null && phoneValue != "") {
        phone = true;
        $('#input-phone').css("box-shadow", "none");
        setSuccessValid($('#input-phone'), $('#phone-validation-message'));
    }
}


$(document).ready(function () {

    ymaps.ready(init);
    var coords;
    function init() {
        var addressList = new ymaps.SuggestView('input-address');
        var destAddressList = new ymaps.SuggestView('input-dest-address');
        firstMap = new ymaps.Map("first-map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });

        firstMap.events.add('click', function (e) {
            coords = e.get('coords');
            geocode(coords, true);
        });
        firstMarker = new ymaps
            .Placemark([55.753564, 37.621085], { balloonContent: null },
                {
                    preset: 'islands#greenDotIconWithCaption',
                    draggable: true
                });

        firstMarker.events
            .add("dragend", function (event) {
                coords = self.firstMarker.geometry.getCoordinates();
                //alert(coords);
                geocode(coords, true);
            });


        secondMap = new ymaps.Map("second-map", {
            center: [51.6720400, 39.1843000],
            zoom: 12
        });

        secondMap.events.add('click', function (e) {
            coords = e.get('coords');
            destGeocode(coords, true);
        });

        secondMarker = new ymaps
            .Placemark([55.753564, 37.621085], { balloonContent: null },
                {
                    preset: 'islands#greenDotIconWithCaption',
                    draggable: true
                });
        secondMarker.events
            .add("dragend", function (event) {
                coords = self.secondMarker.geometry.getCoordinates();
                destGeocode(coords, true);
            });
    }

    $("#input-address").keyup(function () {
        var address = $("#input-address").val();
        if (address != "") {
            $("#my-address-list").css("display", "none");
        }
        geocode(address, false);
    });

/*    $("#input-address").focus(function () {
        var value = $("#input-address").val();
        if (value == "") {
            $("#my-address-list").css("display", "block");
        }
    });
    $("#input-address").blur(function () {
        setTimeout(function () {
            $("#my-address-list").css("display", "none");
        }, 200);
    });*/

    $("#input-address").keyup(function () {
        var address = $("#input-dest-address").val();
        if (address != "") {
            $("#my-address-list").css("display", "none");
        }
        destGeocode(address, false);
    });

/*    $("#input-dest-address").focus(function () {
        var value = $("#input-dest-address").val();
        if (value == "") {
            $("#my-address-list").css("display", "block");
        }
    });
    $("#input-dest-address").blur(function () {
        setTimeout(function () {
            $("#my-address-list").css("display", "none");
        }, 200);
    });*/

    $('#input-phone').inputmask({
        'mask': '+7 (999) 999-9999',
        'oncomplete': function (e) {
            phone = true;
            $('#input-phone').css("box-shadow", "none");
            setSuccessValid($('#input-phone'), $('#phone-validation-message'));
        },
        'onincomplete': function (e) {
            phone = false;
            $('#input-phone').css("box-shadow", "none");
            setErrorValidMessage($('#input-phone'), $('#phone-validation-message'), getErrorString('error_phone_is_not_valid'));
        }
    });
});