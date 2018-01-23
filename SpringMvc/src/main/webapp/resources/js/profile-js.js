function ToOpenEditModal() {
    $('#formEditProfileModal').modal('show');
}
function ToCleanEditProfileForm() {
    $("#editProfileForm").trigger('reset');
}
function putValueToErrorPasswordLabel(newValue) {
    $(".errorOldPassword").text(newValue);
}