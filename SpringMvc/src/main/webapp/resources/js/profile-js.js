function ToOpenEditModal() {
    $('#formEditProfileModal').modal('show');
}

function ToCleanEditProfileForm() {
    $("#editProfileForm").trigger('reset');
}

function putValueToErrorPasswordLabel(newValue) {
    $(".errorOldPassword").text(newValue);
}

function addAddress() {
    $("#addressValid").text("");
    var inputAddress = $("#input-address").val();
    geocode(inputAddress);
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
            $("#addressValid").text(error);
        } else {
            //alert (geoAddress.getAddressLine());
            //alert(coords[0] + " ! " + coords[1]);
            $.ajax({
                url: 'addAddress',
                type: 'GET',
                data: ({
                    latitude: coords[0],
                    longitude: coords[1]
                }),
                dataType: "text",
                success: function (data) {
                    if (data == "success") {
                        $("#input-address").val("");
                        ymaps.geocode([coords[0], coords[1]]).then(function (res) {
                            if (res.geoObjects.get(0) != null) {
                                var obj = res.geoObjects.get(0);
                                var newHTML = "<li class=\"list-group-item new-address-list\"> <div class=\"col-xs-11 text-left\"> <h4>" + obj.getAddressLine() + "</h4> </div> <div class=\"col-xs-1 text-right\"> <span aria-hidden=\"true\" class=\"remove-address\" onclick=\"removeAddress('" + coords[0] + "','" + coords[1] + "',this);\">&times;</span> </div>  </li> <li class=\"forNewAddress\"></li>";
                                $(".forNewAddress").replaceWith(newHTML);
                                //alert(obj.getAddressLine());
                            }
                        });
                        /*var newHTML = "<li class=\"list-group-item new-address-list\"> <div class=\"col-xs-11 text-left\"> <h4>" + inputAddress + "</h4> </div> <div class=\"col-xs-1 text-right\"> <span aria-hidden=\"true\" class=\"remove-address\" address=\"" + inputAddress + "\" onclick=\"removeAddress('" + inputAddress + "',this);\">&times;</span> </div>  </li> <li class=\"forNewAddress\"></li>";*/
                    } else {
                        $("#addressValid").text("This address already exists.");
                    }
                },
                error: function () {
                    alert("error");
                }
            });

        }
    });
}

function removeAddress(latitude, longitude, thisElem) {
    $.ajax({
        url: 'removeAddress',
        type: 'GET',
        data: ({
            latitude: latitude,
            longitude: longitude
        }),
        success: function () {
            $(thisElem).parent().parent().remove();
        },
        error: function () {
            alert("error");
        }
    });
}

function clearEditPasswordInputs() {
    $("#passwordHash").val("");
    $("#confirmPassword").val("");
}

function getAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            var html = $("#user-addresses").html() + obj.getAddressLine() + "<br>";
            $("#user-addresses").html(html);
            var newHTML = "<li class=\"list-group-item\"> " +
                "<div class=\"col-xs-11 text-left\"> " +
                "   <h4>" + obj.getAddressLine() + "</h4> " +
                "</div> " +
                "<div class=\"col-xs-1 text-right\"> " +
                "   <span aria-hidden=\"true\" class=\"remove-address\" onclick=\"removeAddress('" + coords[0] + "','" + coords[1] + "',this);\">&times;</span> " +
                "</div>  </li> <li class=\"forNewAddress\">" +
                "</li>";
            $(".forNewAddress").replaceWith(newHTML);
        }
    });
}


$(document).ready(function () {

    ymaps.ready(initAddressList);

    function initAddressList() {
        var addressList = new ymaps.SuggestView('input-address');
    }

    clearEditPasswordInputs();

    $(".resetNewAddress").click(function () {
        $.ajax({
            url: 'resetNewAddress',
            type: 'GET',
            success: function () {
                $(".new-address-list").remove();
                $("#addressValid").text("");
                $("#input-address").val("");
            },
            error: function () {
                alert("error");
            }
        });
    });


    $(function () {
        $("#birth").datepicker({
            inline: true,
            showOtherMonths: true,
            dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
            dateFormat: 'yy-mm-dd',
            changeYear: true
        });

        $('#birth').inputmask({
                'mask': '9999-99-99',
                'oncomplete': function (e) {
                    var inputValue = e.target.value;
                    var dateArray = inputValue.split('-');
                    var now = new Date();
                    var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    var curDate = new Date(inputValue);
                    if (today < curDate || curDate == 'Invalid Date' || (dateArray[0] != curDate.getFullYear()) || (dateArray[1]-1 != curDate.getMonth()) || (dateArray[2] != curDate.getDate())){
                        $("#btn-save-user-data").prop('disabled', true);
                    } else{
                        $("#btn-save-user-data").prop('disabled', false);
                    }
                },
                'onincomplete': function (e) {
                    if (e.target.value == "") {
                        $("#btn-save-user-data").prop('disabled', false);
                    } else {
                        $("#btn-save-user-data").prop('disabled', true);
                    }
                }
            }
        );
    });

/*    function ValidateDate(date_fl){
        var str = date_fl;
        function TstDate(){
            var str2 = str.split("-");
            if(str2.length!=3){return false;}
//Границы разрешенного периода. Нельзя ввести дату до 1990-го года и позднее 2020-го.
            if((parseInt(str2[0], 10)>=2018)) {
                return false;
            }
            str2 = str2[0] +'-'+ str2[1]+'-'+ str2[2];
            var da = new Date(str2);
            alert(da);
            if(new Date(str2) == 'Invalid Date'){
                return false;
            }
            return str;
        }
        var S = TstDate()
        if(S)
        {
            alert("ok");
        }
        else
        {
            alert("no");
        }
    }*/
});