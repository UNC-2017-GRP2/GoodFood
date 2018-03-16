var fio = true, login = true, email = true, phone = true, birthday = true, password = false, confirmPassword = false;
var mailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$/; //От 6 символов, наличие одной заглавной буквы, наличие одной строчной буквы и одной цифры



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

/*----------------------------------------------------Get Address By Coordinates--------------------------------------------------*/

function getAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);

            var html = $("#user-data-list").html() + "<li><p><span class=\"glyphicon glyphicon-home one\"></span>" + obj.getAddressLine() + "</p></li>";
            $("#user-data-list").html(html);

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

/*function getAddressByCoordinates(latitude, longitude) {
    var coords = [latitude, longitude];
    ymaps.geocode(coords).then(function (res) {
        if (res.geoObjects.get(0) != null) {
            var obj = res.geoObjects.get(0);
            var html = $("#user-addresses").html() + obj.getAddressLine() + "<br>";
            $("#user-addresses").html(html);
            var
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
}*/

/*----------------------------------------------------For Edit profile Validation--------------------------------------------------*/

function checkAllEditProfileFields() {
    if (fio && login && email && phone && birthday) {
        $("#btn-save-user-data").prop('disabled', false);
    } else {
        $("#btn-save-user-data").prop('disabled', true);
    }
}

function setErrorValidMessage(input, validMessageDiv, message) {
    $(input).css("border", "0.5px solid #d9534f");
    $(validMessageDiv).text(message);
    $(validMessageDiv).css("display", "block");
    checkAllEditProfileFields();
}

function setSuccessValid(input, validMessageDiv) {
    $(input).css("border", "0.5px solid #5cb85c");
    $(validMessageDiv).css("display", "none");
    checkAllEditProfileFields();
}
/*----------------------------------------------------------------------------------------------------------------------------------*/

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


    /*----------------------------------------------------Edit profile Validation--------------------------------------------------*/
    $('#fio').keyup(function () {
        $('#fio').css("box-shadow", "none");
        if ($('#fio').val() == "") {
            fio = false;
            setErrorValidMessage(this, $('#fio-validation-message'), "Full name must not be empty.");

        } else {
            fio = true;
            setSuccessValid(this, $('#fio-validation-message'));
        }
    });

    $('#login').keyup(function () {
        $('#login').css("box-shadow", "none");
        var username = $('#login').val();
        if (username == "") {
            login = false;
            setErrorValidMessage(this, $('#login-validation-message'), "Login must not be empty.");

        } else {
            $.ajax({
                url: 'checkUsernameForUpdate',
                type: 'GET',
                data: ({
                    username: username
                }),
                dataType: "text",
                success: function (data) {
                    if (data == "true") {
                        login = false;
                        setErrorValidMessage($('#login'), $('#login-validation-message'), "This login is already in use.");
                    } else {
                        login = true;
                        setSuccessValid($('#login'), $('#login-validation-message'));
                    }
                },
                error: function () {
                    alert("error checkUsernameForUpdate");
                }
            });
        }
    });

    $('#email').keyup(function () {
        $('#email').css("box-shadow", "none");
        var emailInput = $('#email').val();
        if (emailInput == "") {
            email = false;
            setErrorValidMessage(this, $('#email-validation-message'), "E-mail must not be empty.");

        } else {
            if (!mailRegex.test(emailInput)) {
                email = false;
                setErrorValidMessage(this, $('#email-validation-message'), "E-mail is not valid.");
            } else {
                $.ajax({
                    url: 'checkEmailForUpdate',
                    type: 'GET',
                    data: ({
                        email: emailInput
                    }),
                    dataType: "text",
                    success: function (data) {
                        if (data == "true") {
                            email = false;
                            setErrorValidMessage($('#email'), $('#email-validation-message'), "This E-mail is already in use.");
                        } else {
                            email = true;
                            setSuccessValid($('#email'), $('#email-validation-message'));
                        }
                    },
                    error: function () {
                        alert("error checkEmailForUpdate");
                    }
                });
            }
        }
    });

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
                    //$("#btn-save-user-data").prop('disabled', true);
                    birthday = false;
                    setErrorValidMessage($('#birth'), $('#birth-validation-message'), "Birthday is not valid.");
                } else{
                    birthday = true;
                    setSuccessValid($('#birth'), $('#birth-validation-message'));
                    //$("#btn-save-user-data").prop('disabled', false);
                }
            },
            'onincomplete': function (e) {
                if (e.target.value == "") {
                    birthday = true;
                    setSuccessValid($('#birth'), $('#birth-validation-message'));
                    //$("#btn-save-user-data").prop('disabled', false);
                } else {
                    birthday = false;
                    setErrorValidMessage($('#birth'), $('#birth-validation-message'), "Birthday is not valid.");
                }
            }
        }
    );

    $('#phoneNumber1').inputmask({
        'mask': '+7 (999) 999-9999',
        'oncomplete': function (e) {
            phone = true;
            setSuccessValid($('#phoneNumber1'), $('#phone-validation-message'));
            /*$("#btn-save-user-data").prop('disabled', false);
            $("#phone-validation-message").text("");*/
        },
        'onincomplete': function (e) {
            if (e.target.value == "") {
                phone = true;
                setSuccessValid($('#phoneNumber1'), $('#phone-validation-message'));
                //$("#btn-save-user-data").prop('disabled', false);
            } else {
                phone = false;
                setErrorValidMessage($('#phoneNumber1'), $('#phone-validation-message'), "Phone is not valid.");
                /*$("#phone-validation-message").text("Phone is not valid.");
                $("#btn-save-user-data").prop('disabled', true);*/
            }
        }
    });

    $(function () {
        $('#profile-image1').on('click', function () {
            $('#profile-image-upload').click();
        });
    });
});