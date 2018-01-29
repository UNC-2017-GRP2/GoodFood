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
    if (inputAddress == "") {
        $("#addressValid").text("This field must not be empty.");
    } else {

        $.ajax({
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
        });
    }
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
    
});