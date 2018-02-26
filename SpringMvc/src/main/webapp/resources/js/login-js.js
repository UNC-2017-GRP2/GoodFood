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
    $("#login-alert").css("display","inline-block");
}

$(document).ready(function () {

    $("#sign-up-reference").click(function () {
        $(".row-rotate").css("transform", "rotateY(180deg)");
    });

    $("#sign-in-reference").click(function () {
        $(".row-rotate").css("transform", "rotateY(0deg)");
    });
});