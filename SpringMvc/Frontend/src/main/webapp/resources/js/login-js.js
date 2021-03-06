var fio = false, login = false, email = false, password = false, confirmPassword = false;
var mailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$/; //От 6 символов, наличие одной заглавной буквы, наличие одной строчной буквы и одной цифры

/*function ToOpenRegistrationModal() {
    $('#formRegistrationModal').modal('show');
    $(".row-rotate").css("transform", "rotateY(180deg)");
    $(".alert-registration").css("display", "inline-block");
}

function ToCleanRegistrationForm() {
    $(".alert-registration").css("display", "none");
    $("#registrationForm").trigger('reset');
}*/

function showErrorMessage() {
    $("#login-alert").css("display", "inline-block");
}

function checkAllRegistrationFields() {
    if (fio && login && email && password && confirmPassword) {
        $("#btn-signUp").prop('disabled', false);
    } else {
        $("#btn-signUp").prop('disabled', true);
    }
}

function setErrorValidMessage(input, validMessageDiv, message) {
    $(input).css("border", "0.5px solid #d9534f");
    $(validMessageDiv).text(message);
    $(validMessageDiv).css("display", "block");
    checkAllRegistrationFields();
}
function setSuccessValid(input, validMessageDiv) {
    $(input).css("border", "0.5px solid #5cb85c");
    $(validMessageDiv).css("display", "none");
    checkAllRegistrationFields();
}

$(document).ready(function () {

    $("#sign-up-reference").click(function () {
        $(".row-rotate").css("transform", "rotateY(180deg)");
    });

    $("#sign-in-reference").click(function () {
        $(".row-rotate").css("transform", "rotateY(0deg)");
    });

    /*Registration Validation*/
    $('#fio').keyup(function () {
        $('#fio').css("box-shadow", "none");
        if ($('#fio').val() == "") {
            fio = false;
            setErrorValidMessage(this, $('#fio-validation-message'), getErrorString('full_name_must_not_be_empty'));

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
            setErrorValidMessage(this, $('#login-validation-message'), getErrorString('login_must_not_be_empty'));

        } else {
            $.ajax({
                url: 'checkUsername',
                type: 'GET',
                data: ({
                    username: username
                }),
                dataType: "text",
                success: function (data) {
                    if (data == "true") {
                        login = false;
                        setErrorValidMessage($('#login'), $('#login-validation-message'), getErrorString('login_is_already_in_use'));
                    } else {
                        login = true;
                        setSuccessValid($('#login'), $('#login-validation-message'));
                    }
                },
                error: function () {
                    $.notify(getErrorString('data_error'), "error");
                }
            });
        }
    });

    $('#email').keyup(function () {
        $('#email').css("box-shadow", "none");
        var emailInput = $('#email').val();
        if (emailInput == "") {
            email = false;
            setErrorValidMessage(this, $('#email-validation-message'), getErrorString('email_must_not_be_empty'));

        } else {
            if (!mailRegex.test(emailInput)) {
                email = false;
                setErrorValidMessage(this, $('#email-validation-message'), getErrorString('email_is_not_valid'));
            } else {
                $.ajax({
                    url: 'checkEmail',
                    type: 'GET',
                    data: ({
                        email: emailInput
                    }),
                    dataType: "text",
                    success: function (data) {
                        if (data == "true") {
                            email = false;
                            setErrorValidMessage($('#email'), $('#email-validation-message'), getErrorString('email_is_already_in_use'));
                        } else {
                            email = true;
                            setSuccessValid($('#email'), $('#email-validation-message'));
                        }
                    },
                    error: function () {
                        $.notify(getErrorString('data_error'), "error");
                    }
                });
            }
        }
    });

    $('#passwordHash').keyup(function () {
        $('#passwordHash').css("box-shadow", "none");
        var passwordInput = $('#passwordHash').val();
        if (passwordInput == "") {
            password = false;
            setErrorValidMessage(this, $('#password-validation-message'), getErrorString('password_must_not_be_empty'));
        } else if (!passwordRegex.test(passwordInput)) {
            password = false;
            setErrorValidMessage(this, $('#password-validation-message'), getErrorString('password_must_contain'));
        } else {
            password = true;
            setSuccessValid(this, $('#password-validation-message'));
            if (passwordInput !== $('#confirmPassword').val()){
                confirmPassword = false;
                setErrorValidMessage($('#confirmPassword'), $('#confirmPassword-validation-message'), getErrorString('passwords_do_not_match'));
            }
        }
    });

    $('#confirmPassword').keyup(function () {
        $('#confirmPassword').css("box-shadow", "none");
        var confirmPasswordInput = $('#confirmPassword').val();
        if (confirmPasswordInput == "") {
            confirmPassword = false;
            setErrorValidMessage(this, $('#confirmPassword-validation-message'), getErrorString('password_confirmation_must_not_be_empty'));
        } else if (confirmPasswordInput != $('#passwordHash').val()) {
            confirmPassword = false;
            setErrorValidMessage(this, $('#confirmPassword-validation-message'), getErrorString('passwords_do_not_match'));
        } else {
            confirmPassword = true;
            setSuccessValid(this, $('#confirmPassword-validation-message'));
        }
    });

});