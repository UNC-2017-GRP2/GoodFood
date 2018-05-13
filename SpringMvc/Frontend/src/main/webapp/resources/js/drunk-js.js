var destAddressFlag = false; addressFlag = false;

var style;
var form;

function addressSelection(address) {
    $("#input-address").val(address);
    geocode(address);
}

function destAddressSelection(address) {
    $("#input-dest-address").val(address);
    destGeocode(address);
}

function geocode(address) {
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
            addressFlag = true;
            setSuccessValid($("#input-address"), $("#address-valid"));
            $("#input-address-latitude").val(coords[0]);
            $("#input-address-longitude").val(coords[1]);
        }
    });
}

function destGeocode(address) {
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
            setErrorValidMessage($("#input-dest-address"), $("#address-valid"), error);

        } else {
            destAddressFlag = true;
            setSuccessValid($("#input-dest-address"), $("#address-valid"));
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

    ymaps.ready(initAddressList);

    function initAddressList() {
        var addressList = new ymaps.SuggestView('input-address');
        var destAddressList = new ymaps.SuggestView('input-dest-address');
    }

    $("#input-address").keyup(function () {
        var address = $("#input-address").val();
        if (address != "") {
            $("#my-address-list").css("display", "none");
        }
        geocode(address);
    });

    $("#input-address").focus(function () {
        var value = $("#input-address").val();
        if (value == "") {
            $("#my-address-list").css("display", "block");
        }
    });
    $("#input-address").blur(function () {
        setTimeout(function () {
            $("#my-address-list").css("display", "none");
        }, 200);
    });

    $("#input-address").keyup(function () {
        var address = $("#input-dest-address").val();
        if (address != "") {
            $("#my-address-list").css("display", "none");
        }
        destGeocode(address);
    });

    $("#input-dest-address").focus(function () {
        var value = $("#input-dest-address").val();
        if (value == "") {
            $("#my-address-list").css("display", "block");
        }
    });
    $("#input-dest-address").blur(function () {
        setTimeout(function () {
            $("#my-address-list").css("display", "none");
        }, 200);
    });

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