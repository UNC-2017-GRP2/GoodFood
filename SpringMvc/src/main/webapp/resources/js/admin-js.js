function dropdownButton(userId, buttonId) {
    // alert($('#' + buttonId).val());
    // alert(userId);
    $.ajax({
        url : 'changeRole',
        type: 'GET',
        /*contentType: 'application/json',
        mimeType: 'application/json',*/
        data : ({
            userId: userId,
            role: $('#' + buttonId).val()
        }),
        success: function (data) {
            $.notify("Role changed", "success");
        }
    });
}

function selectOption(elementId, value) {
    document.getElementById(elementId).value = value;
}
