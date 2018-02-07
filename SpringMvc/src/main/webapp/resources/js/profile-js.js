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
            $("#addressValid").text(error);
        } else {
            //alert (geoAddress.getAddressLine());
            //alert(coords[0] + " ! " + coords[1]);

            /*$.ajax({
           url: 'addAddress',
           type: 'GET',
           data: ({
               inputAddress: inputAddress
           }),
           dataType: "text",
           success: function (data) {
               if(data == "success"){
                   $("#input-address").val("");
                   var newHTML = "<li class=\"list-group-item new-address-list\"> <div class=\"col-xs-11 text-left\"> <h4>" + inputAddress + "</h4> </div> <div class=\"col-xs-1 text-right\"> <span aria-hidden=\"true\" class=\"remove-address\" address=\"" + inputAddress + "\" onclick=\"removeAddress('" + inputAddress + "',this);\">&times;</span> </div>  </li> <li class=\"forNewAddress\"></li>";
                   $(".forNewAddress").replaceWith(newHTML);
               }else{
                   $("#addressValid").text("This address already exists.");
               }
           },
           error: function () {
               alert("error");
           }
       });*/


            /*ymaps.geocode(coords).then(function(res){
                if (res.geoObjects.get(0) != null){
                    var obj = res.geoObjects.get(0);
                    alert(obj.getAddressLine());
                }
            });*/

        }
    });

}

function removeAddress(removeAddress, thisElem) {
    $.ajax({
        url: 'removeAddress',
        type: 'GET',
        data: ({
            removeAddress: removeAddress
        }),
        success: function () {
            $(thisElem).parent().parent().remove();
        },
        error: function () {
            alert("error");
        }
    });
}




$(document).ready(function() {

    var map;

    /*ymaps.ready(
        function() {
        map = new ymaps.Map("map", {
            center: [51.6720400, 39.1843000 ],
            zoom: 13,
            controls: ['smallMapDefaultSet']
        });
    });*/
    ymaps.ready(initAddressList);

    function initAddressList() {
        var addressList = new ymaps.SuggestView('input-address');
    }


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

    $("#birth").val("");

    $( function() {
        $( "#birth" ).datepicker({
                inline: true,
                showOtherMonths: true,
                dayNamesMin: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']
            });
    } );
});