var fio = false, login = false, email = false, password = false, confirmPassword = false;
var mailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
var passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$/; //От 6 символов, наличие одной заглавной буквы, наличие одной строчной буквы и одной цифры

function ToOpenRegistrationModal() {
    $('#formRegistrationModal').modal('show');
    $(".row-rotate").css("transform", "rotateY(180deg)");
    $(".alert-registration").css("display", "inline-block");
}

function ToCleanRegistrationForm() {
    $(".alert-registration").css("display", "none");
    $("#registrationForm").trigger('reset');
}

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
                url: 'checkUsername',
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
                    alert("error checkUsername Registration");
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
                    url: 'checkEmail',
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
                        alert("error checkEmail Registration");
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
            setErrorValidMessage(this, $('#password-validation-message'), "Password must not be empty.");
        } else if (!passwordRegex.test(passwordInput)) {
            password = false;
            setErrorValidMessage(this, $('#password-validation-message'), "Password must contain from 6 characters, one capital letter,one lower case letter and one number");
        } else {
            password = true;
            setSuccessValid(this, $('#password-validation-message'));
        }
    });

    $('#confirmPassword').keyup(function () {
        $('#confirmPassword').css("box-shadow", "none");
        var confirmPasswordInput = $('#confirmPassword').val();
        if (confirmPasswordInput == "") {
            confirmPassword = false;
            setErrorValidMessage(this, $('#confirmPassword-validation-message'), "Confirm password must not be empty.");
        } else if (confirmPasswordInput != $('#passwordHash').val()) {
            confirmPassword = false;
            setErrorValidMessage(this, $('#confirmPassword-validation-message'), "Passwords don't match.");
        } else {
            confirmPassword = true;
            setSuccessValid(this, $('#confirmPassword-validation-message'));
        }
    });

});